package com.ff.postpone.task;


import com.ff.postpone.common.*;
import com.ff.postpone.constant.*;
import com.ff.postpone.util.*;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author Demo-Liu
 * @Date 2019/3/20 14:29
 * @description 执行定时任务
 */
@DisallowConcurrentExecution
public class Postpone extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(Postpone.class);

    private static final Integer waitTime = 1000 * 60 * Profile.BLOG_INIT_WAIT_TIME;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        postpone();
    }

    public void postpone(){
        //获取所有云账号配置
        List<Map<String, String>> cloudServers = Profile.cloudServers;

        //邮箱配置
        MailUtil mailUtil = MailUtil.MailUtilBuilder.getBuilder()
                .setHost(Profile.MAIL_SERVER_HOST)
                .setPort(Profile.MAIL_SERVER_PORT)
                .setPassword(Profile.MAIL_PASSWORD)
                .setUsername(Profile.MAIL_USERNAME)
                .setReceiveUser(Profile.MAIL_RECEIVE_USER)
                .build();


        String username = null;
        String type;
        CloudInfo cloudInfo;
        String cloudName = null;
        HttpClient httpClient;
        String ukLog = null;
        String uKey = null;
        //循环检查免费服务器状态
        next: for (Map<String, String> serverInfo : cloudServers) {
            try{
                httpClient = HttpUtil.getHttpClient(new BasicCookieStore());
                username = serverInfo.get(Constans.CLOUD_USERNAME);
                type = serverInfo.get(Constans.CLOUD_TYPE);
                cloudInfo = CloudInfo.getCloudInfo(type);
                cloudName = cloudInfo.getCloudName();
                ukLog = CommonCode.getUKLog(username, cloudName);
                uKey = CommonCode.getUserKey(username, type);

                //调用登录接口查看状态
                String status = loginAndCheck(httpClient, mailUtil, serverInfo, cloudInfo);
                if(status != null && "1".equals(status)){
                    mailUtil.sendMail(ukLog+"已到延期时间",ukLog+"开始执行延期程序...");
                    //发送博客
                    log.info("{}开始发送延期博客", ukLog);
                    String blogUrl = BlogGit.sendCustomBlogByType(type);
                    log.info("{}发送延期博客url:{}", ukLog,blogUrl);
                    //检查博客是否被初始化
                    //先等20秒 如果还没有初始化成功 开始循环等待
                    Thread.sleep(waitTime);
                    int waitCount = 0;
                    while (!CommonCode.isInitBlog(blogUrl)){
                        log.info("{}延期博客未初始化,等待{}分钟", ukLog, Profile.BLOG_INIT_WAIT_TIME);
                        waitCount ++;
                        if(waitCount > Profile.BLOG_INIT_WAIT_COUNT){
                            BlogGit.deleteBlog(blogUrl);
                            log.info("{}博客初始化失败:{}", ukLog,blogUrl);
                            mailUtil.sendMail(ukLog+"博客初始化失败",blogUrl);
                            continue next;
                        }
                        Thread.sleep(waitTime);
                    }

                    //持久化至文件
                    Map<String, String> userInfo = Profile.userInfos.get(uKey);
                    if(userInfo == null) userInfo = new HashMap<>();
                    userInfo.put("blogUrl", blogUrl);
                    CommonCode.userInfosPermanent(uKey, userInfo);

                    //生成截图
                    log.info("{}开始生成截图", ukLog);
                    boolean picCreated = createPic(blogUrl);
                    //如果创建成功 开始发送延期请求
                    if(picCreated){
                        log.info("{}截图生成成功,开始提交", ukLog);
                        postBlogInfo(httpClient, mailUtil, blogUrl, serverInfo, cloudInfo);
                    }else{
                        log.info("{}网页截图生成失败:{}", ukLog,blogUrl);
                        BlogGit.deleteBlog(blogUrl);
                        mailUtil.sendMail(cloudName+"账号:"+username+",网页截图生成失败", "blog Url: "+ blogUrl);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                log.error("{},延期过程出错!!!", ukLog);
                mailUtil.sendMail(cloudName + "账号: "+username+",延期过程出错",e.getMessage());
            }
        }

    }

        /**
         * 登录和检查状态接口
         */
    public String loginAndCheck(HttpClient httpClient, MailUtil mailUtil, Map<String,String> serverInfo, CloudInfo cloudInfo) throws Exception {
        //用户名密码
        String username = serverInfo.get(Constans.CLOUD_USERNAME);
        String password = serverInfo.get(Constans.CLOUD_PASSWORD);
        //服务器类型名称
        String cloudName = cloudInfo.getCloudName();
        String type = cloudInfo.getType();


        //持久化信息
        Map<String, String> userInfo = Profile.userInfos.get(CommonCode.getUserKey(username, type));
        //首次启动为null
        if(userInfo == null) userInfo = new HashMap<>();
        String nextTime = userInfo.get(CloudDataKey.NEXT_TIME);

        String ukLog = CommonCode.getUKLog(username, cloudName);

        String status = null;
        //1. 检查是否到期
        if(StringUtil.isEmpty(nextTime) || CommonCode.isExpire(nextTime)){
            log.info("{}开始登录...", ukLog);

            //2. 调用登录接口
            JSONObject loginJson = JSONObject.fromObject(
                    HttpUtil.getPostRes(httpClient,
                            cloudInfo.getLoginUri(),
                            CloudPostParams.getYunLogin(username, password))
            );
            log.info("{}登录接口返回:{}", ukLog, loginJson.toString());

            if(CloudDataKey.LOGIN_SUCCESS.equals(loginJson.getString(CloudDataKey.LOGIN_STATUS))){
                //3. 登录成功 调用状态接口
                log.info("{}登录成功,开始检查免费服务器状态!!!", ukLog);
                JSONObject statusJson = JSONObject.fromObject(
                        HttpUtil.getPostRes(httpClient,
                                cloudInfo.getBusUri(),
                                CloudPostParams.getFreeStatus()));
                log.info("{}检查服务器状态返回:{}", ukLog, statusJson.toString());
                statusJson = statusJson.getJSONObject(CloudDataKey.STATUS_DATA);
                status = statusJson.containsKey(CloudDataKey.DELAY_STATUS1) ? statusJson.getString(CloudDataKey.DELAY_STATUS1) : statusJson.getString(CloudDataKey.DELAY_STATUS2);


                //审核状态接口
                JSONObject checkJson = JSONObject.fromObject(
                        HttpUtil.getPostRes(httpClient,
                                cloudInfo.getBusUri(),
                                CloudPostParams.getCheckStatus()));
                log.info("{}延期记录接口返回:{}", ukLog, checkJson.toString());

                switch (status) {
                    case "1": //已到审核期
                        CommonCode.checkCheckStatus(checkJson, ukLog, userInfo.get("blogUrl"), mailUtil);
                        log.info("{},已到审核期!!!", ukLog);
                        break;
                    case "0": //未到审核期
                        CommonCode.checkCheckStatus(checkJson, ukLog, userInfo.get("blogUrl"), mailUtil);
                        String next_time = statusJson.getString(CloudDataKey.NEXT_TIME);
//                        if(!next_time.equals(StringUtil.isEmpty(nextTime) ? "" : next_time)){
//                            mailUtil.sendMail(cloudName + "账号: "+username+"下次执行时间"+next_time, statusJson.toString());
//                        }
                        //持久化到文件
                        userInfo.put(CloudDataKey.NEXT_TIME, next_time);
                        CommonCode.userInfosPermanent(CommonCode.getUserKey(username,type), userInfo);
                        log.info("{}未到审核期,下次审核开始时间:{}", ukLog, next_time);
                        break;
                    default :
                        log.info("{}正在审核!!!", ukLog);
                }
            }else{
                log.info("{}登录失败!!!",ukLog);
                mailUtil.sendMail(cloudName + "账号: "+username+", 登录失败!!!",loginJson.toString());
            }
        }else{
            log.info("{}未到期", ukLog);
        }
        return status;
    }


    /**
     * 生成网页截图
     * @param blogUrl 博客url
     * @return
     * @throws Exception
     */
    public boolean createPic(String blogUrl) throws Exception {
        log.info("开始创建文件...");
        StringBuilder sb = new StringBuilder();
        String BLANK = "  ";

        sb.append(Profile.PJ_EXEC).append(BLANK)
                .append(ResourceAbPath.PIC_JS_ABPATH).append(BLANK)
                .append(blogUrl).append(BLANK)
                .append(Profile.PJ_PIC_PATH);


        //执行一次删除防止上次任务残留
        File file = FileUtil.deleteFile(Profile.PJ_PIC_PATH);

        log.info("开始执行截图命令:{}", sb.toString());
        try {
            log.info("执行截图命令返回:{}", CmdUtil.execCmd(sb.toString(), true));
            Thread.sleep(20000);
            int i = 0;
            while(!file.exists()){
                log.info("文件未创建成功,等待10秒...");
                i++;
                if(i==20){
                    log.info("文件创建失败: {}", blogUrl);
                    return false;
                }
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            log.info("文件创建失败: {}", blogUrl);
            e.printStackTrace();
            return false;
        }
        log.info("文件创建成功!!!");
        return true;
    }


    /**
     * 提交延期博客信息
     * @param httpClient httpclient
     * @param mailUtil 邮件工具类
     * @param blogUrl 博客url
     * @param serverInfo 服务器信息
     * @param cloudInfo 云服务器参数
     * @throws IOException
     * @throws GitAPIException
     */
    public void postBlogInfo(HttpClient httpClient, MailUtil mailUtil, String blogUrl, Map<String,String> serverInfo, CloudInfo cloudInfo) throws IOException, GitAPIException {
        String username = serverInfo.get(Constans.CLOUD_USERNAME);
        String cloudName = cloudInfo.getCloudName();

        String ukLog = CommonCode.getUKLog(username, cloudName);

        File file = new File(Profile.PJ_PIC_PATH);

        String postRes = HttpUtil.getPostRes(httpClient, cloudInfo.getBusUri(), CloudPostParams.getBlogInfo(cloudInfo,file,blogUrl).build());
        JSONObject json = JSONObject.fromObject(postRes);
        log.info("{}提交延期记录返回结果: {}", ukLog, json.toString());

        if(CloudDataKey.BLOG_SUCCESS.equals(json.getString(CloudDataKey.BLOG_STATUS))){
            log.info("{}提交延期记录成功!!!", ukLog);
        }else{
            log.info("{}提交延期记录失败,删除发布博客!!!", ukLog);
            mailUtil.sendMail(cloudName+"账号:"+username+"发送延期博客失败", json.toString());
            BlogGit.deleteBlog(blogUrl);
        }
        file.delete();
    }



}
