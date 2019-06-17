package com.ff.postpone.task;

import com.ff.postpone.pojo.UserInfo;
import com.ff.postpone.task.util.HttpUtil;
import com.ff.postpone.task.util.ParamUtil;
import com.ff.postpone.task.util.UrlUtil;
import net.sf.json.JSONObject;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author LiuFei
 * @Date 2019/6/12 11:12
 * @description
 */
public class CSDN {


    private static Logger log = Logger.getLogger(CSDN.class);

    /**
     * @Author LiuFei
     * @Date 2019/6/12 14:45
     * @description 获取csdn cookie
     * @Param [userInfo]
     * @Reutrn java.lang.String
    */
    public static String getCSDNCookie(UserInfo userInfo) throws IOException, URISyntaxException {
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        //登录接口1
        JSONObject jsonObject = JSONObject.fromObject(HttpUtil.getGetRes(httpClient, UrlUtil.CSDN_LOGIN1 , ParamUtil.getCSDNLogin1(userInfo.getBlogUser())));
        log.info("CSDN-login1返回:"+jsonObject.toString());
        //登录接口2
        if("success".equals(jsonObject.getString("message"))) {
            JSONObject json = JSONObject.fromObject(HttpUtil.getPostRes(httpClient, UrlUtil.CSDN_LOGIN2, new StringEntity(ParamUtil.getCSDNLogin2(userInfo).toString())));
            log.info("CSDN-login2返回:"+json.toString());
            String cookieStr = "";
            if ("success".equals(json.getString("message"))) {
                for (Cookie cookie : cookieStore.getCookies()) {
                    System.out.println(cookie.getName()+"="+cookie.getValue());
                    //这里为什么是这些cookie?  实践出真知!!!---demo_liu
                    String name = cookie.getName();
                    if(name.equals("uuid_tt_dd") || name.equals("dc_session_id") || name.equals("SESSION") ||
                       name.equals("UserName")   || name.equals("UserInfo")      || name.equals("UserToken")){
                        cookieStr += cookie.getName() + "=" + cookie.getValue() + ";";
                    }
                }
                cookieStr+="firstDie=1;";
                log.info("CSDN cookie:"+cookieStr);
                return cookieStr;
            } else {
                //未能成功请求
                return "99";
            }
        }else{
            return "99";
        }
    }


    /**
     * 发送CSDN博客,获取博客文章url
     * @param cookieStr
     * @param cloudType 0 阿贝云, 1 三丰云
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String sendCSDNBlog(String cookieStr, int cloudType) throws IOException {
        Map<String,String> map = new HashMap<>();
        map.put("cookie",cookieStr);
        HttpClient httpClient = HttpUtil.getHttpClient();
        JSONObject jsonObject = JSONObject.fromObject(HttpUtil.getPostRes(httpClient, UrlUtil.CSDN_SEND, new UrlEncodedFormEntity(ParamUtil.getSendCSDN(cloudType),"UTF-8"), map));
        log.info("发送CSDN博客返回:"+jsonObject.toString());
        if("1".equals(jsonObject.getString("result"))){
            JSONObject data = JSONObject.fromObject(jsonObject.getString("data"));
            return data.getString("url");
        }else{
            return "99";
        }
    }


    /**
     * 删除CSDN博客
     * @param cookieStr
     * @param url
     * @return
     * @throws IOException
     */
    public static boolean deleteCSDNBlog(String cookieStr, String url) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("cookie",cookieStr);
        String[] split = url.split("/");//content=删除成功
        String articleId = split[split.length - 1];
        HttpClient httpClient = HttpUtil.getHttpClient();
        String username = getUserNameByCookieStr(cookieStr);
        JSONObject jsonObject = JSONObject.fromObject(HttpUtil.getPostRes(httpClient, UrlUtil.getCSDNDelete(username), new UrlEncodedFormEntity(ParamUtil.getCSDNDelete(articleId, 1)), map));
        log.info("删除CSDN博客返回:"+jsonObject.toString());
        if("删除成功".equals(jsonObject.getString("content"))){
            return deepCSDNBlog(httpClient, cookieStr, articleId);
        }
        return false;
    }

    /**
     * 根据cookie字符串获取用户名
     * @param cookieStr
     * @return
     */
    private static String getUserNameByCookieStr(String cookieStr) throws Exception {
        if(cookieStr.indexOf("UserName") == -1)throw new Exception("cookieStr No Strings 'UserName'");
        String[] split = cookieStr.split(";");
        for (String s : split) {
            if(s.contains("UserName")){
                return s.split("=")[1];
            }
        }
        return null;
    }

    /**
     * 回收站删除CSDN博客
     * @param httpClient
     * @param cookieStr
     * @param articleId
     * @return
     * @throws IOException
     */
    private static boolean deepCSDNBlog(HttpClient httpClient, String cookieStr, String articleId) throws IOException {
        Map<String,String> map = new HashMap<>();
        map.put("cookie",cookieStr);
        JSONObject jsonObject = JSONObject.fromObject(HttpUtil.getPostRes(httpClient, UrlUtil.CSDN_DEEP, new UrlEncodedFormEntity(ParamUtil.getCSDNDelete(articleId, 2)), map));
        log.info("删除回收站CSDN博客返回:"+jsonObject.toString());
        return "删除成功".equals(jsonObject.getString("content"));
    }


    public static void main(String[] args) throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setBlogUser("15332919175");
        userInfo.setBlogPass("MM1457118807");
        String cookie = getCSDNCookie(userInfo);

//        String s = sendCSDNBlog(getCSDNCookie(userInfo),0);
//        System.out.println(s);
   //     deleteCSDNBlog(cookie,"https://blog.csdn.net/AdAd_aad/article/details/91982634");
    }
}
