package com.ff.postpone.task.util;

import com.ff.postpone.mapper.UserInfoMapper;
import com.ff.postpone.pojo.UserInfo;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @Author LiuFei
 * @Date 2019/6/12 11:11
 * @description
 */
public class Sina {


    private static Logger log = Logger.getLogger(Sina.class);

    /**
     * 删除延期博客
     * @param info
     * @param url
     * @param infoMapper
     */
    public static void deleteBlog(UserInfo info, String url, UserInfoMapper infoMapper, List<String> cookieList) throws IOException, URISyntaxException {
        log.info("开始删除"+url);
        String[] strs = url.split("_");
        String[] params = strs[1].split(".html");
        NameValuePair delete[] = {
                new BasicNameValuePair("blog_id[]",params[0]),
                new BasicNameValuePair("uid","5118414786")
        };
        HttpClient sinaClient = HttpUtil.getHttpClient();

        Map<String,String> map;
        if("1".equals(info.getStatus())){ //从接口获取cookie
            log.info("开始接口获取sinaCookie");
            if(cookieList.get(0)==null){
                log.info("开始第一次获取cookie....");
                cookieList.set(0,getSinaCookie(info));
            }
            map = ParamUtil.getPubHeader(cookieList.get(0));
        }else{ //从本地获取cookie
            cookieList.set(0,info.getSinaCookie());
            map = ParamUtil.getPubHeader(cookieList.get(0));
        }
        map.put("Referer","http://blog.sina.com.cn/s/blog_"+params[0]+".html");
        String postRes = HttpUtil.getPostRes(sinaClient, UrlUtil.SINA_DELETE, delete, map);
        log.info("删除博客返回:"+postRes);
        String[] postRess;
        JSONObject json = null;
        if(postRes.length()>10){
            postRess = postRes.split("</script>");
            json = JSONObject.fromObject(postRess[1]);
        }
        try{
            if(json!=null){
                if("A00006".equals(json.getString("code"))){
                    log.info(url+"删除成功!!!");
                    infoMapper.updateByPrimaryKey(info);
                    completelyDelete(sinaClient, params[0], cookieList.get(0));
                }else{
                    log.info(url+"删除失败!!!");
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 从回收站删除博客
     * @param sinaClient
     * @param param
     * @param cookie
     * @throws IOException
     */
    public static void completelyDelete(HttpClient sinaClient, String param, String cookie) throws IOException, URISyntaxException {
        log.info("调用彻底删除接口...");
        NameValuePair delete[] = {
                new BasicNameValuePair("blog_id[]",param),
                new BasicNameValuePair("uid","5118414786"),
                new BasicNameValuePair("varname","requestId_40385686")
        };
        String getRes = HttpUtil.getGetRes(sinaClient, UrlUtil.SINA_DEEP, delete, ParamUtil.getPubHeader(cookie));
        log.info("彻底删除博文返回:"+ getRes);
    }



    /**
     * 获取新浪单点登录cookie
     * @param info
     * @return
     * @throws IOException
     */
    public static String getSinaCookie(UserInfo info) throws IOException, URISyntaxException {
        CookieStore cookieStore = new BasicCookieStore();
        HttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        NameValuePair[] pairs = ParamUtil.getSinaLogin(info.getSinausername(),info.getSinapassword());
        HttpUtil.getPostRes(httpClient,UrlUtil.SINA_LOGIN,pairs, ParamUtil.getPubHeader(""));
        String cookieStr = "";
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            cookieStr += cookie.getName()+"="+cookie.getValue()+";";
        }
        return cookieStr;
    }

}
