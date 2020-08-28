package com.ff.postpone.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Demo-Liu
 * @create 2020-08-05 13:43
 * @description 配置文件参数
 */
@Component
@ConfigurationProperties
public class Profile implements CommandLineRunner{

    private Profile(){}

    /**
     * 云服务器配置
     */
    public static List<Map<String, String>> cloudServers;

    /**
     * 持久化信息
     */
    public static Map<String, Map<String, String>> userInfos;

    //邮箱 配置
    public static String MAIL_SERVER_HOST;
    public static Integer MAIL_SERVER_PORT;
    public static String MAIL_USERNAME;
    public static String MAIL_PASSWORD;
    public static String MAIL_RECEIVE_USER;

    //博客 配置
    public static String BLOG_URI;
    public static String BLOG_LOCAL_PATH;
    public static String BLOG_USERNAME;
    public static String BLOG_URL;

    //博客初始化配置
    public static Integer BLOG_INIT_WAIT_COUNT;
    public static Integer BLOG_INIT_WAIT_TIME;

    //phantomJs 配置
    public static String PJ_EXEC;
    public static String PJ_PIC_PATH;

    //资源文件提取路径
    public static String RESOURCE_TEMP_FILEPATH;

    @Override
    public void run(String... args) throws Exception {
        //根据 BLOG_URI 生成 BLOG_URL
        BLOG_URL = "https://" +  BLOG_URI.substring(BLOG_URI.lastIndexOf("/")+1);
        //.git
        BLOG_URL = BLOG_URL.substring(0,BLOG_URL.length()-4);
        if(userInfos == null) userInfos = new HashMap<>();
    }

    public void setCloudServers(List<Map<String, String>> cloudServers) {
        Profile.cloudServers = cloudServers;
    }


    public void setUserInfos(Map<String, Map<String, String>> userInfos) {
        Profile.userInfos = userInfos;
    }

    @Value("${mailInfo.serverHost}")
    public void setMailServerHost(String mailServerHost) {
        MAIL_SERVER_HOST = mailServerHost;
    }

    @Value("${mailInfo.serverPort}")
    public void setMailServerPort(Integer mailServerPort) {
        MAIL_SERVER_PORT = mailServerPort;
    }

    @Value("${mailInfo.password}")
    public void setMailPassword(String mailPassword) {
        MAIL_PASSWORD = mailPassword;
    }

    @Value("${mailInfo.username}")
    public void setMailUsername(String mailUsername) {
        MAIL_USERNAME = mailUsername;
    }

    @Value("${mailInfo.receiveUser}")
    public void setMailReceiveUser(String mailReceiveUser) {
        MAIL_RECEIVE_USER = mailReceiveUser;
    }

    @Value("${blogInfo.uri}")
    public void setBlogUri(String blogUri) {
        BLOG_URI = blogUri;
    }

    @Value("${blogInfo.localPath}")
    public void setBlogLocalPath(String blogLocalPath) {
        BLOG_LOCAL_PATH = blogLocalPath;
    }

    @Value("${blogInfo.username}")
    public void setBlogUsername(String blogUsername) {
        BLOG_USERNAME = blogUsername;
    }

    @Value("${phantomJs.phantomJsExec}")
    public void setPjExec(String pjExec) {
        PJ_EXEC = pjExec;
    }

    @Value("${phantomJs.picPath}")
    public void setPjPicPath(String pjPicPath) {
        PJ_PIC_PATH = pjPicPath;
    }

    @Value("${blogInitWaitCount}")
    public void setBlogInitWaitCount(Integer blogInitWaitCount) {
        BLOG_INIT_WAIT_COUNT = blogInitWaitCount;
    }
    @Value("${blogInitWaitTime}")
    public void setBlogInitWaitTime(Integer blogInitWaitTime) {
        BLOG_INIT_WAIT_TIME = blogInitWaitTime;
    }
    @Value("${resourceTempFilePath}")
    public void setResourceTempFilepath(String resourceTempFilepath) {
        RESOURCE_TEMP_FILEPATH = resourceTempFilepath;
    }
}
