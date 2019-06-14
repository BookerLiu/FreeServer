package com.ff.postpone.task;

import com.ff.postpone.mapper.UserInfoMapper;
import com.ff.postpone.pojo.UserInfo;
import com.ff.postpone.pojo.UserInfoExample;
import com.ff.postpone.task.util.HttpUtil;
import com.ff.postpone.task.util.ParamUtil;
import com.ff.postpone.task.util.Sina;
import com.ff.postpone.task.util.UrlUtil;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Scheduled(cron = "0 0/30 * * * ? ")
    public void postpone(){
        //查询所有云账号
        UserInfoExample example = new UserInfoExample();
        List<UserInfo> userInfos = infoMapper.selectByExample(example);

        List<String> cookieList = new ArrayList<>();
        cookieList.add(null);
        try{
            //根据不同服务器获取不同url进行操作
            for (UserInfo userInfo : userInfos) {
                String bz = userInfo.getCloudType();
                switch (bz){
                    case "0": //阿贝云
                        logic(userInfo,0,cookieList);
                        break;
                    case "1": //三丰云
                        logic(userInfo,1,cookieList);
                        break;
                }
            }
        }catch (Exception e){
            log.error("延期过程中出错!!!");
            e.printStackTrace();
        }
    }

    public void logic(UserInfo userInfo, int bz, List<String> cookieList) throws Exception{
        JSONObject json;
        String username = userInfo.getCloudUser();
        log.info(username + " 开始登录"+ ParamUtil.getYunName(bz)+"!!!");
        HttpClient yunClient = HttpUtil.getHttpClient();
        json = JSONObject.fromObject(HttpUtil.getPostRes(yunClient,  ParamUtil.getYunUrl(bz,0), ParamUtil.getYunLogin(username, userInfo.getCloudPass())));
        log.info("登录"+ ParamUtil.getYunName(bz)+"返回:"+json.toString());
        String loginMsg = json.getString("msg");
        if("登录成功".equals(loginMsg)){
            log.info(username + ParamUtil.getYunName(bz)+"登录成功,开始检查免费服务器状态!!!");
            json = JSONObject.fromObject(HttpUtil.getPostRes(yunClient, ParamUtil.getYunUrl(bz,1), ParamUtil.getFreeStatus()));
            log.info("检查服务器状态返回:"+json.toString());
            int status = CommCode.checkServerStatus(json, username, infoMapper, userInfo);
            log.info("服务器状态:"+status);
            json = JSONObject.fromObject(HttpUtil.getPostRes(yunClient, ParamUtil.getYunUrl(bz,1), ParamUtil.getCheckStatus()));
            log.info("检查审核状态返回:"+json);
            switch (status){
                case 1:  //已到审核期
                    CommCode.checkCheckStatus(json, userInfo, infoMapper, cookieList);
                    sentBlog(userInfo, yunClient, infoMapper, bz, cookieList);
                    break;
                case 2:  //未到审核期
                    CommCode.checkCheckStatus(json, userInfo, infoMapper, cookieList);
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
    public void sentBlog(UserInfo info, HttpClient yunClient, UserInfoMapper infoMapper, int bz, List<String> cookieList) throws Exception {
        log.info("开始发送延期博客...");

        Map<String,String> map;
        if("1".equals(info.getCookieType())){ //从接口获取cookie
            log.info("开始接口获取sinaCookie");
            if(cookieList.get(0)==null){
                log.info("开始第一次获取cookie....");
                cookieList.set(0, Sina.getSinaCookie(info));
            }
            map = ParamUtil.getPubHeader(cookieList.get(0));
        }else{ //从本地获取cookie
            map = ParamUtil.getPubHeader(info.getBlogCookie());
        }
        map.put("Referer","http://control.blog.sina.com.cn/admin/article/article_add.php");
        log.info("sina cookie:"+map.get("cookie"));
        HttpClient sinaClient = HttpUtil.getHttpClient();
        String postRes = HttpUtil.getPostRes(sinaClient, UrlUtil.SINA_SEND, ParamUtil.getSendBlog(bz), map);

        log.info("延期博客返回结果:"+postRes);
        JSONObject json = JSONObject.fromObject(postRes);
        String data = json.getString("data");
        String code = json.getString("code");
        if("B06001".equals(code) && data!=null && !"null".equals(data)){
            String sinaUrl = "http://blog.sina.com.cn/s/blog_" + data + ".html";
            log.info("更新"+info.getCloudUser()+" url为:"+sinaUrl);
            info.setBlogUrl(sinaUrl);
            infoMapper.updateByPrimaryKey(info);
            if(createPic(sinaUrl)){
                File file = new File("/usr/local/phantomjs-2.1.1/postponepic/postpone.png");
                Thread.sleep(20000);
                int i = 0;
                while(!file.exists()){
                    log.info("文件未创建成功,等待10秒...");
                    i++;
                    if(i==20){
                        log.info("文件创建失败:"+sinaUrl);
                        return;
                    }
                    Thread.sleep(10000);
                }
                sendAbei(info,yunClient,sinaUrl,file,cookieList,bz);

            }
        }else{
            log.info("发送博客失败:"+postRes);
        }
    }

    /**
     * 提交延期记录
     * @param yunClient
     * @param url
     * @param file
     */
    public void sendAbei(UserInfo info, HttpClient yunClient, String url, File file, List<String> cookieList, int bz) throws Exception {
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
        multipartEntityBuilder.addTextBody("url","url" ,contentType);
        String postRes = HttpUtil.getPostRes(yunClient, ParamUtil.getYunUrl(bz,1), multipartEntityBuilder.build(), ParamUtil.getPubHeader(""));

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
                Sina.deleteBlog(info,url,infoMapper,cookieList);
            }
        }catch (Exception e){
            log.info("提交延期记录失败,删除发布博客!!!");
            Sina.deleteBlog(info,url,infoMapper,cookieList);
        }
        file.delete();

    }

    /**
     * 使用 phantomjs 生成网页截图
     * @param url
     */
    public boolean createPic(String url){
        log.info("开始创建文件...");
        String BLANK = "  ";
        // phantomjs 文件路径
        String path = "/usr/local/phantomjs-2.1.1/bin/phantomjs" + BLANK;
        // js文件路径
        String jspath = "/usr/local/phantomjs-2.1.1/bin/postpone.js" + BLANK;
        // 截图生成路径
        String filepath = "/usr/local/phantomjs-2.1.1/postponepic/postpone.png";
        Runtime rt = Runtime.getRuntime();
        Process process = null;
        try {
            process = rt.exec(path + jspath + url + BLANK + filepath);
        } catch (IOException e) {
            // TODO 这里写异常处理的代码
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
