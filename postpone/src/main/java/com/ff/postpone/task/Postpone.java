package com.ff.postpone.task;

import com.ff.postpone.mapper.UserInfoMapper;
import com.ff.postpone.pojo.UserInfo;
import com.ff.postpone.pojo.UserInfoExample;
import com.ff.postpone.task.util.HttpUtil;
import com.ff.postpone.task.util.MailUtil;
import com.ff.postpone.task.util.ParamUtil;
import com.ff.postpone.task.util.PropertiesUtil;
import net.sf.json.JSONObject;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @Author Demo-Liu
 * @Date 2019/3/20 14:29
 * @description
 */
@Component
public class Postpone {


    @Autowired
    private UserInfoMapper infoMapper;

    private Logger log = Logger.getLogger(Postpone.class);

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void postpone(){
        //查询所有云账号
        UserInfoExample example = new UserInfoExample();
        List<UserInfo> userInfos = infoMapper.selectByExample(example);

        Map<String,String> cookieMap = new HashMap<>();
        try{
            //根据不同服务器获取不同url进行操作
            for (UserInfo userInfo : userInfos) {
                String bz = userInfo.getCloudType();
                switch (bz){
                    case "0": //阿贝云
                        logic(userInfo,0,cookieMap);
                        break;
                    case "1": //三丰云
                        logic(userInfo,1,cookieMap);
                        break;
                }
            }
        }catch (Exception e){
            log.error("延期过程中出错!!!");
            e.printStackTrace();
        }
    }

    public void logic(UserInfo userInfo, int cloudType, Map<String,String> cookieMap) throws Exception{
        JSONObject json;
        String username = userInfo.getCloudUser();
        log.info(username + " 开始登录"+ ParamUtil.getYunName(cloudType)+"!!!");
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient yunClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        String ss = HttpUtil.getPostRes(yunClient,  ParamUtil.getYunUrl(cloudType,0), new UrlEncodedFormEntity(ParamUtil.getYunLogin(username, userInfo.getCloudPass())));
        json = JSONObject.fromObject(ss);
        log.info("登录"+ ParamUtil.getYunName(cloudType)+"返回:"+json.toString());
        String loginMsg = json.getString("msg");
        if("登录成功".equals(loginMsg)){
            log.info(username + ParamUtil.getYunName(cloudType)+"登录成功,开始检查免费服务器状态!!!");
            json = JSONObject.fromObject(HttpUtil.getPostRes(yunClient, ParamUtil.getYunUrl(cloudType,1), new UrlEncodedFormEntity(ParamUtil.getFreeStatus())));
            log.info("检查服务器状态返回:"+json.toString());
            int status = CloudLogic.checkServerStatus(json, username, infoMapper, userInfo);
            log.info("服务器状态:"+status);
            json = JSONObject.fromObject(HttpUtil.getPostRes(yunClient, ParamUtil.getYunUrl(cloudType,1), new UrlEncodedFormEntity(ParamUtil.getCheckStatus())));
            log.info("检查审核状态返回:"+json);
            switch (status){
                case 1:  //已到审核期
                    CloudLogic.checkCheckStatus(json, userInfo, infoMapper, cookieMap);
                    sentBlog(userInfo, yunClient, infoMapper, cloudType, cookieMap);
                    break;
                case 2:  //未到审核期
                    CloudLogic.checkCheckStatus(json, userInfo, infoMapper, cookieMap);
                    break;
            }

        }else{
            log.info("登录失败 "+loginMsg);
        }
    }

    /**
     * 发博客并生成截图
     * @param info
     * @param yunClient
     * @param infoMapper
     */
    public void sentBlog(UserInfo info, HttpClient yunClient, UserInfoMapper infoMapper, int cloudType, Map<String,String> cookieMap) throws Exception {
        log.info("开始发送延期博客...");
        String postRes;
        String cookie;
        //cookie获取方式(0 使用本地cookie, 1 使用接口获取cookie)
        String cookieType = info.getCookieType();
        //使用博客类型 (0 新浪博客, 1 CSDN博客)
        if("0".equals(info.getBlogType())){
            String userKey = info.getBlogUser()+"SINA";
            if("0".equals(cookieType)){
                cookie = info.getBlogCookie();
                log.info("本地获取SINA cookie:"+cookie);
                cookieMap.put(userKey,cookie);
            }else{ //从本地获取cookie
                log.info("开始接口获取Sina cookie");
                if(cookieMap.get(userKey) == null){
                    log.info(userKey+"开始第一次获取cookie....");
                    cookieMap.put(userKey,Sina.getSinaCookie(info));
                }
            }
            JSONObject json = JSONObject.fromObject(Sina.sendSinaBlog(cookieMap.get(userKey), cloudType));
            log.info("延期博客返回结果:"+json.toString());
            String data = json.getString("data");
            String code = json.getString("code");
            if("B06001".equals(code) && data!=null && !"null".equals(data)){
                String sinaUrl = "http://blog.sina.com.cn/s/blog_" + data + ".html";
                log.info("更新"+info.getCloudUser()+" url为:"+sinaUrl);
                info.setBlogUrl(sinaUrl);
                infoMapper.updateByPrimaryKey(info);
                if(createPic(sinaUrl,info.getBlogType())){
                    File file = new File(new PropertiesUtil().getProperty("PHANTOM_SINA_FILE"));
                    sendAbei(info,yunClient,sinaUrl,file,cookieMap,cloudType);
                }else{
                    log.info("图片生成失败,删除新浪博客!!!");
                    Sina.deleteBlog(info,sinaUrl,cookieMap);
                }
            }else{
                log.info("发送新浪博客失败");
                MailUtil.sendMaid("发送新浪博客失败",json.toString());
            }
        }else{
            String userKey = info.getBlogUser()+"CSDN";
            if("0".equals(cookieType)){
                cookie = info.getBlogCookie();
                log.info("本地获取CSDN cookie:"+cookie);
                cookieMap.put(userKey,cookie);
            }else{
                log.info("接口获取CSDN cookie...");
                if(cookieMap.get(userKey) == null){
                    log.info(userKey+"开始第一次获取cookie....");
                    cookieMap.put(userKey, CSDN.getCSDNCookie(info));
                }
            }
            String res = CSDN.sendCSDNBlog(cookieMap.get(userKey), cloudType);
            JSONObject jsonObject = JSONObject.fromObject(res);
            if("1".equals(jsonObject.getString("result"))){
                String url = jsonObject.getString("url");
                log.info("更新"+info.getCloudUser()+" url为:"+url);
                info.setBlogUrl(url);
                infoMapper.updateByPrimaryKey(info);
                if(createPic(url,info.getBlogType())){
                    File file = new File(new PropertiesUtil().getProperty("PHANTOM_CSDN_FILE"));
                    sendAbei(info,yunClient,url,file,cookieMap,cloudType);
                }else{
                    log.info("图片生成失败,删除CSDN博客!!!");
                    CSDN.deleteCSDNBlog(cookieMap.get(userKey),url);
                }
            }else{
                log.info("发送CSDN博客失败");
                MailUtil.sendMaid("发送CSDN博客失败",jsonObject.toString());
            }
        }
    }

    /**
     * 提交延期记录
     * @param yunClient
     * @param url
     * @param file
     */
    public void sendAbei(UserInfo info, HttpClient yunClient, String url, File file, Map<String,String> cookieMap, int bz) throws Exception {
        log.info("开始提交延期记录!!!");
//        HttpPost httpPost = new HttpPost(CommCode.getYunUrl(bz,1));
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

        //三丰云和阿贝云文件提交方式不一样
        if(bz==0) {
            multipartEntityBuilder.addBinaryBody("yanqi_img",file);
        }else{
            multipartEntityBuilder.addBinaryBody("yanqi_img",file, ContentType.IMAGE_PNG,"postpone.png");
        }
        multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));
        ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));
        multipartEntityBuilder.addTextBody("cmd","free_delay_add" ,contentType);
        multipartEntityBuilder.addTextBody("ptype","vps" ,contentType);
        multipartEntityBuilder.addTextBody("url",url ,contentType);
        String postRes = HttpUtil.getPostRes(yunClient, ParamUtil.getYunUrl(bz,1), multipartEntityBuilder.build());

        //三丰云和阿贝云文件提交方式不一样
//        if(bz==0){
//            //阿贝云
//            filePart = new FilePart("yanqi_img",file);
//        }else{
//            //三丰云
//            filePart = new FilePart("yanqi_img","postpone.png",file,"image/png","UTF-8");
//        }
//        filePart.setCharSet("utf-8");
//        Part[] parts = {
//                new StringPart("cmd","free_delay_add" , "utf-8"),
//                new StringPart("ptype","vps" , "utf-8"),
//                new StringPart("url",url , "utf-8"),
//                filePart};
//        MultipartRequestEntity entity = new MultipartRequestEntity(parts, httpPost.getParams());
//        yunClient.executeMethod(httpPost);
//        String postRes = httpPost.getResponseBodyAsString();
        log.info("提交延期记录返回结果:"+postRes);
        try{
            JSONObject json = JSONObject.fromObject(postRes);
            if("提交成功".equals(json.getString("msg"))){
                log.info("提交延期记录成功!!!");
            }else{
                log.info("提交延期记录失败,删除发布博客!!!");
                Sina.deleteBlog(info,url,cookieMap);
            }
        }catch (Exception e){
            log.info("提交延期记录失败,删除发布博客!!!");
            Sina.deleteBlog(info,url,cookieMap);
        }
        file.delete();

    }

    /**
     * 使用 phantomjs 生成网页截图
     * @param url
     * @param blogType
     * @return
     */
    public boolean createPic(String url, String blogType){
        log.info("开始创建文件...");
        PropertiesUtil prpt = new PropertiesUtil();
        String BLANK = "  ";
        // phantomjs 文件路径
        String path = prpt.getProperty("PHANTOM_PTAH") + BLANK;
        // js文件路径
        String jspath;
        // 截图生成路径
        String filepath;
        if("1".equals(blogType)){
            jspath = prpt.getProperty("PHANTOM_CSDN_JS") + BLANK;
            filepath = prpt.getProperty("PHANTOM_CSDN_FILE");
        }else{
            jspath = prpt.getProperty("PHANTOM_SINA_JS") + BLANK;
            filepath = prpt.getProperty("PHANTOM_SINA_FILE");
        }
        File file = new File(filepath);
        //执行一次删除防止上次任务残留
        file.delete();
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(path + jspath + url + BLANK + filepath);
            Thread.sleep(20000);
            int i = 0;
            while(!file.exists()){
                log.info("文件未创建成功,等待10秒...");
                i++;
                if(i==20){
                    log.info("文件创建失败:" + url);
                    return false;
                }
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        log.info("文件创建成功!!!");
        return true;
    }


}
