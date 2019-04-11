package com.ff.postpone.task;

import com.ff.postpone.mapper.UserInfoMapper;
import com.ff.postpone.pojo.UserInfo;
import com.ff.postpone.task.util.HttpUtil;
import com.ff.postpone.task.util.ParamUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Demo-Liu
 * @Date 2019/3/19 10:50
 * @description
 */
public class CommCode {


    private static Logger log = Logger.getLogger(CommCode.class);

    /**
     * 检查服务器状态
     * @return
     */
    public static int checkServerStatus(JSONObject json, String username, UserInfoMapper infoMapper, UserInfo userInfo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String msg = json.getString("msg");
        json = JSONObject.fromObject(msg);
        String status = null;
        try{
            status = json.getString("delay_enable");
        }catch (Exception e){
            status = json.getString("delay_state");
        }
        switch (status){
            case "1":
                log.info(username + "已到审核期!!!");
                return 1;
            case "0":
                String next_time = json.getString("next_time");
                log.info(username + "未到审核期,下次审核开始时间:"+next_time);
                Date nextTime = sdf.parse(next_time);
                userInfo.setNextTime(nextTime);
                infoMapper.updateByPrimaryKey(userInfo);
                return 2;
            case "待审核":
                log.info(username + "正在审核!!!");
                return 3;
        }
        log.info(username + "服务器返回其他状态:"+status);
        return 0;
    }

    /**
     * 检查审核状态
     * @param json
     * @param userInfo
     * @param infoMapper
     * @return
     */
    public static int checkCheckStatus(JSONObject json, UserInfo userInfo, UserInfoMapper infoMapper) throws IOException {
        String username = userInfo.getYunusername();
        json = JSONObject.fromObject(json.getString("msg"));
        JSONArray array = JSONArray.fromObject(json.getString("content"));
        json = JSONObject.fromObject(array.get(0));
        String state = json.getString("State");
        String url = json.getString("url");
        if("待审核".equals(state)){
            log.info(username + "审核中,无需审核!!!");
            return 1;
        }else if("审核通过".equals(state)){
            log.info(username + "审核通过!!!");
            if(userInfo.getSinaUrl().equals(url)){
                userInfo.setSinaUrl("success");
                infoMapper.updateByPrimaryKey(userInfo);
                deleteBlog(userInfo,url,infoMapper);
                log.info("修改url为success,删除延期博客!!!");
            }
            return 2;
        }else{
            log.info(username + "审核失败,审核结果:"+state);
            if(userInfo.getSinaUrl().equals(url)){
                userInfo.setSinaUrl("error");
                infoMapper.updateByPrimaryKey(userInfo);
                deleteBlog(userInfo,url,infoMapper);
                log.info("修改url为error,删除延期博客!!!");
            }
            return 3;
        }
    }

    /**
     * 删除延期博客
     * @param info
     * @param url
     * @param infoMapper
     */
    public static void deleteBlog(UserInfo info, String url, UserInfoMapper infoMapper) throws IOException {
        log.info("开始删除"+url);
        String[] strs = url.split("_");
        String[] params = strs[1].split(".html");
        NameValuePair delete[] = {
                new NameValuePair("blog_id[]",params[0]),
                new NameValuePair("uid","5118414786")
        };
        HttpClient sinaClient = HttpUtil.getHttpClient("UTF-8");
        String deleteUrl = "http://control.blog.sina.com.cn/admin/article/article_del_recycle.php?domain=1";

        String cookie = null;
        Map<String,String> map = null;
        if("1".equals(info.getStatus())){ //从接口获取cookie
            log.info("开始接口获取sinaCookie");
            cookie = getSinaCookie(info);
            map = CommCode.getPubHeader(cookie);
        }else{ //从本地获取cookie
            cookie = info.getSinaCookie();
            map = CommCode.getPubHeader(cookie);
        }
        map.put("Referer","http://blog.sina.com.cn/s/blog_"+params[0]+".html");
        String postRes = HttpUtil.getPostRes(sinaClient, deleteUrl, delete, map);
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
                    completelyDelete(sinaClient, params[0], cookie);
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
    public static void completelyDelete(HttpClient sinaClient, String param, String cookie) throws IOException {
        log.info("调用彻底删除接口...");
        String url = "http://control.blog.sina.com.cn/admin/article/article_recycle_del.php";
        GetMethod getMethod = new GetMethod(url);
        NameValuePair delete[] = {
                new NameValuePair("blog_id[]",param),
                new NameValuePair("uid","5118414786"),
                new NameValuePair("varname","requestId_40385686")
        };
        Map<String,String> map = getPubHeader(cookie);
        getMethod.setQueryString(delete);
        String getRes = HttpUtil.getGetRes(sinaClient, url, delete, map);
        log.info("彻底删除博文返回:"+ getRes);
    }



    /**
     * 获取新浪微博单点登录cookie
     * @param info
     * @return
     * @throws IOException
     */
    public static String getSinaCookie(UserInfo info) throws IOException {
        HttpClient httpClient = HttpUtil.getHttpClient("UTF-8");
        String url = "https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.15)&_=";
        NameValuePair[] pairs = CommCode.getSinaLogin(info.getSinausername(),info.getSinapassword());
        HttpUtil.getPostRes(httpClient,url,pairs,CommCode.getPubHeader(""));
        String cookieStr = "";
        Cookie[] cookies = httpClient.getState().getCookies();
        for (Cookie cookie : cookies) {
            cookieStr += cookie.toString()+";";
        }
        return cookieStr;
    }



    /**
     * 获取公共请求头Map
     * @param cookie
     * @return
     */
    public static Map<String,String> getPubHeader(String cookie){
        Map<String,String> map = new HashMap<>();
        map.put("Referer", "https://weibo.com/");
        map.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
        map.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("Accept", "text/html,application/xhtml+xm…plication/xml;q=0.9,*/*;q=0.8");
        map.put("Accept-Encoding", "deflate, br");
        map.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        map.put("cookie",cookie);
        return map;
    }

    /**
     * 获取阿贝云登录参数
     * @param username
     * @param password
     * @return
     */
    public static NameValuePair[] getYunLogin(String username, String password){
        NameValuePair yunLogin[] = {
                new NameValuePair("cmd","login"),
                new NameValuePair("id_mobile",username),
                new NameValuePair("password",password)
        };
        return yunLogin;
    }

    /**
     * 获取查询服务器状态参数
     * @return
     */
    public static NameValuePair[] getFreeStatus(){
        NameValuePair freeDelay[] = {
                new NameValuePair("cmd","check_free_delay"),
                new NameValuePair("ptype","vps")
        };
        return  freeDelay;
    }

    /**
     * 获取审核状态参数
     * @return
     */
    public static NameValuePair[] getCheckStatus(){
        NameValuePair status[] = {
                new NameValuePair("cmd","free_delay_list"),
                new NameValuePair("ptype","vps"),
                new NameValuePair("count","20"),
                new NameValuePair("page","1")
        };
        return status;
    }

    /**
     * 获取发送微博参数
     * @return
     */
    public static NameValuePair[] getSendBlog(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(new Date());
        String[] strs = format.split(" ");
        NameValuePair blog[] = {
                new NameValuePair("ptype",""),
                new NameValuePair("teams",""),
                new NameValuePair("worldcuptags",""),
                new NameValuePair("album",""),
                new NameValuePair("album_cite",""),
                new NameValuePair("blog_id",""),
                new NameValuePair("is_album","0"),
                new NameValuePair("old365","0"),
                new NameValuePair("stag",""),
                new NameValuePair("sno",""),
                new NameValuePair("book_worksid",""),
                new NameValuePair("channel_id",""),
                new NameValuePair("url",""),
                new NameValuePair("channel",""),
                new NameValuePair("newsid",""),
                new NameValuePair("fromuid",""),
                new NameValuePair("wid",""),
                new NameValuePair("articletj",""),
                new NameValuePair("vtoken","4fcebcbd1a24c119fd14998b8c9c635d"),
                new NameValuePair("is_media","0"),
                new NameValuePair("is_stock","0"),
                new NameValuePair("is_tpl","0"),
                new NameValuePair("assoc_article",""),
                new NameValuePair("assoc_style","0"),
                new NameValuePair("assoc_article_data",""),
                new NameValuePair("article_BGM",""),
                new NameValuePair("xRankStatus",""),
                new NameValuePair("commentGlobalSwitch",""),
                new NameValuePair("commenthideGlobalSwitch",""),
                new NameValuePair("articleStatus_preview","1"),
                new NameValuePair("source",""),
                new NameValuePair("topic_id","0"),
                new NameValuePair("topic_channel","0"),
                new NameValuePair("topic_more",""),
                new NameValuePair("utf8","1"),
                new NameValuePair("conlen","96"),
                new NameValuePair("date_pub",strs[0]),
                new NameValuePair("time",strs[1]),
                new NameValuePair("new_time",""),
                new NameValuePair("isTimed","0"),
                new NameValuePair("immediatepub","0"),
                new NameValuePair("blog_title", ParamUtil.TITLE + (int)(Math.random()*100000000)),
                new NameValuePair("blog_body",ParamUtil.BODY),
                new NameValuePair("blog_class","00"),
                new NameValuePair("tag","it"),
                new NameValuePair("x_cms_flag","2"),
                new NameValuePair("x_quote_flag","1"),
                new NameValuePair("sina_sort_id","117")
        };
        return blog;
    }


    /**
     * 获取新浪微博登录参数
     * @param username
     * @param password
     * @return
     */
    public static NameValuePair[] getSinaLogin(String username, String password){
        NameValuePair[] pairs = {
                new NameValuePair("entry", "sso"),
                new NameValuePair("gateway", "1"),
                new NameValuePair("from", "null"),
                new NameValuePair("savestate", "30"),
                new NameValuePair("useticket", "0"),
                new NameValuePair("pagerefer", ""),
                new NameValuePair("vsnf", "1"),
                new NameValuePair("su", Base64.getEncoder().encodeToString(username.getBytes())),
                new NameValuePair("service", "sso"),
                new NameValuePair("sp", password),
                new NameValuePair("sr", "1024*768"),
                new NameValuePair("encoding", "UTF-8"),
                new NameValuePair("cdult", "3"),
                new NameValuePair("domain", "sina.com.cn"),
                new NameValuePair("prelt", "0"),
                new NameValuePair("returntype", "TEXT")
        };
        return pairs;
    }
}
