package com.ff.postpone.task;

import com.ff.postpone.mapper.UserInfoMapper;
import com.ff.postpone.pojo.UserInfo;
import com.ff.postpone.pojo.UserInfoExample;
import com.ff.postpone.task.util.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
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
        List<UserInfo> userInfos = infoMapper.selectByExampleWithBLOBs(example);

        try{
            //根据不同服务器获取不同url进行操作
            for (UserInfo userInfo : userInfos) {
                String bz = userInfo.getBz();
                switch (bz){
                    case "0": //阿贝云
                        logic(userInfo,0);
                        break;
                    case "1": //三丰云
                        logic(userInfo,1);
                        break;
                }
            }
        }catch (Exception e){
            log.error("延期过程中出错!!!");
            e.printStackTrace();
        }
    }

    public void logic(UserInfo userInfo, int bz) throws Exception{
        JSONObject json;
        String username = userInfo.getYunusername();
        log.info(username + " 开始登录"+CommCode.getYunName(bz)+"!!!");
        HttpClient yunClient = HttpUtil.getHttpClient("UTF-8");
        json = JSONObject.fromObject(HttpUtil.getPostRes(yunClient,  CommCode.getYunUrl(bz,0), CommCode.getYunLogin(username, userInfo.getYunpassword())));
        log.info("登录"+CommCode.getYunName(bz)+"返回:"+json.toString());
        String loginMsg = json.getString("msg");
        if("登录成功".equals(loginMsg)){
            log.info(username + CommCode.getYunName(bz)+"登录成功,开始检查免费服务器状态!!!");
            json = JSONObject.fromObject(HttpUtil.getPostRes(yunClient, CommCode.getYunUrl(bz,1), CommCode.getFreeStatus()));
            log.info("检查服务器状态返回:"+json.toString());
            int status = CommCode.checkServerStatus(json, username, infoMapper, userInfo);
            log.info("服务器状态:"+status);
            json = JSONObject.fromObject(HttpUtil.getPostRes(yunClient, CommCode.getYunUrl(bz,1), CommCode.getCheckStatus()));
            log.info("检查审核状态返回:"+json);
            switch (status){
                case 1:  //已到审核期
                    CommCode.checkCheckStatus(json, userInfo, infoMapper);
                    sentBlog(userInfo, yunClient, infoMapper, bz);
                    break;
                case 2:  //未到审核期
                    CommCode.checkCheckStatus(json, userInfo, infoMapper);
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
     * @throws Exception
     */
    public void sentBlog(UserInfo info, HttpClient yunClient, UserInfoMapper infoMapper, int bz) throws Exception {
        log.info("开始发送延期博客...");
        String url = "http://control.blog.sina.com.cn/admin/article/article_post.php";

        Map<String,String> map;
        if("1".equals(info.getStatus())){ //从接口获取cookie
            log.info("开始接口获取sinaCookie");
            String cookie = CommCode.getSinaCookie(info);
            map = CommCode.getPubHeader(cookie);
        }else{ //从本地获取cookie
            map = CommCode.getPubHeader(info.getSinaCookie());
        }
        map.put("Referer","http://control.blog.sina.com.cn/admin/article/article_add.php");
        log.info("sina cookie:"+map.get("cookie"));
        HttpClient sinaClient = HttpUtil.getHttpClient("UTF-8");
        String postRes = HttpUtil.getPostRes(sinaClient, url, CommCode.getSendBlog(bz), map);

        log.info("延期博客返回结果:"+postRes);
        JSONObject json = JSONObject.fromObject(postRes);
        String data = json.getString("data");
        if(data!=null && !"null".equals(data)){
            String sinaUrl = "http://blog.sina.com.cn/s/blog_" + data + ".html";
            log.info("更新"+info.getYunusername()+" url为:"+sinaUrl);
            info.setSinaUrl(sinaUrl);
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
                sendAbei(info,yunClient,sinaUrl,file);

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
     * @throws Exception
     */
    public void sendAbei(UserInfo info, HttpClient yunClient, String url, File file) throws Exception {
        log.info("开始提交延期记录!!!");
        PostMethod postMethod = new PostMethod("https://api.abeiyun.com/www/renew.php");
        FilePart filePart = new FilePart("yanqi_img",file);
        filePart.setCharSet("utf-8");
        Part[] parts = {
                new StringPart("cmd","free_delay_add" , "utf-8"),
                new StringPart("ptype","vps" , "utf-8"),
                new StringPart("url",url , "utf-8"),
                filePart};
        MultipartRequestEntity entity = new MultipartRequestEntity(parts, postMethod.getParams());
        postMethod.setRequestEntity(entity);
        yunClient.executeMethod(postMethod);
        String postRes = postMethod.getResponseBodyAsString();
        log.info("提交延期记录返回结果:"+postRes);
        if(postRes.startsWith("{")){
            log.info("提交延期记录成功!!!");
        }else{
            log.info("提交延期记录失败,删除发布博客!!!");
            CommCode.deleteBlog(info,url,infoMapper);
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
