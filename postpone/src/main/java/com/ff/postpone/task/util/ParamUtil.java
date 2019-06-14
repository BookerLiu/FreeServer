package com.ff.postpone.task.util;

import com.ff.postpone.mapper.UserInfoMapper;
import com.ff.postpone.pojo.UserInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Demo-Liu
 * @Date 2019/3/19 10:50
 * @description
 */
public class ParamUtil {


    private static Logger log = Logger.getLogger(ParamUtil.class);


    /**
     * 获取云 url
     * @param bz 判断是哪个云服务器
     * @param logic 0 登录, 1 业务操作
     * @return
     */
    public static String getYunUrl(int bz,int logic){
        switch (bz){
            case 0:
                if(logic==0){
                    return UrlUtil.ABEI_LOGIN;
                }else{
                    return UrlUtil.ABEI_LOGIC;
                }
            case 1:
                if(logic==0){
                    return UrlUtil.SANFENG_LOGIN;
                }else{
                    return UrlUtil.SANFENG_LOGIC;
                }
        }
        return null;
    }

    /**
     * 获取云服务器名字
     * @param bz
     * @return
     */
    public static String getYunName(int bz){
        switch(bz){
            case 0:
                return "阿贝云";
            case 1:
                return "三丰云";
        }
        return null;
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
                new BasicNameValuePair("cmd","login"),
                new BasicNameValuePair("id_mobile",username),
                new BasicNameValuePair("password",password)
        };
        return yunLogin;
    }

    /**
     * 获取查询服务器状态参数
     * @return
     */
    public static NameValuePair[] getFreeStatus(){
        NameValuePair freeDelay[] = {
                new BasicNameValuePair("cmd","check_free_delay"),
                new BasicNameValuePair("ptype","vps")
        };
        return  freeDelay;
    }

    /**
     * 获取审核状态参数
     * @return
     */
    public static NameValuePair[] getCheckStatus(){
        NameValuePair status[] = {
                new BasicNameValuePair("cmd","free_delay_list"),
                new BasicNameValuePair("ptype","vps"),
                new BasicNameValuePair("count","20"),
                new BasicNameValuePair("page","1")
        };
        return status;
    }

    /**
     * 获取发送微博参数
     * @param bz
     * @return
     */
    public static NameValuePair[] getSendBlog(int bz){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(new Date());
        String[] strs = format.split(" ");
        NameValuePair blog[] = {
                new BasicNameValuePair("ptype",""),
                new BasicNameValuePair("teams",""),
                new BasicNameValuePair("worldcuptags",""),
                new BasicNameValuePair("album",""),
                new BasicNameValuePair("album_cite",""),
                new BasicNameValuePair("blog_id",""),
                new BasicNameValuePair("is_album","0"),
                new BasicNameValuePair("old365","0"),
                new BasicNameValuePair("stag",""),
                new BasicNameValuePair("sno",""),
                new BasicNameValuePair("book_worksid",""),
                new BasicNameValuePair("channel_id",""),
                new BasicNameValuePair("url",""),
                new BasicNameValuePair("channel",""),
                new BasicNameValuePair("newsid",""),
                new BasicNameValuePair("fromuid",""),
                new BasicNameValuePair("wid",""),
                new BasicNameValuePair("articletj",""),
                new BasicNameValuePair("vtoken","4fcebcbd1a24c119fd14998b8c9c635d"),
                new BasicNameValuePair("is_media","0"),
                new BasicNameValuePair("is_stock","0"),
                new BasicNameValuePair("is_tpl","0"),
                new BasicNameValuePair("assoc_article",""),
                new BasicNameValuePair("assoc_style","0"),
                new BasicNameValuePair("assoc_article_data",""),
                new BasicNameValuePair("article_BGM",""),
                new BasicNameValuePair("xRankStatus",""),
                new BasicNameValuePair("commentGlobalSwitch",""),
                new BasicNameValuePair("commenthideGlobalSwitch",""),
                new BasicNameValuePair("articleStatus_preview","1"),
                new BasicNameValuePair("source",""),
                new BasicNameValuePair("topic_id","0"),
                new BasicNameValuePair("topic_channel","0"),
                new BasicNameValuePair("topic_more",""),
                new BasicNameValuePair("utf8","1"),
                //new BasicNameValuePair("conlen","96"),
                new BasicNameValuePair("date_pub",strs[0]),
                new BasicNameValuePair("time",strs[1]),
                new BasicNameValuePair("new_time",""),
                new BasicNameValuePair("isTimed","0"),
                new BasicNameValuePair("immediatepub","0"),
                new BasicNameValuePair("blog_title", bz==0 ? UrlUtil.ABEI_TITLE + (int)(Math.random()*100000000) : UrlUtil.SANFENG_TITLE + (int)(Math.random()*100000000)),
                new BasicNameValuePair("blog_body", bz==0 ? UrlUtil.ABEI_BODY : UrlUtil.SANFENG_BODY),
                new BasicNameValuePair("blog_class","00"),
                new BasicNameValuePair("tag","it"),
                new BasicNameValuePair("x_cms_flag","2"),
                new BasicNameValuePair("x_quote_flag","1"),
                new BasicNameValuePair("sina_sort_id","117")
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
                new BasicNameValuePair("entry", "sso"),
                new BasicNameValuePair("gateway", "1"),
                new BasicNameValuePair("from", "null"),
                new BasicNameValuePair("savestate", "30"),
                new BasicNameValuePair("useticket", "0"),
                new BasicNameValuePair("pagerefer", ""),
                new BasicNameValuePair("vsnf", "1"),
                new BasicNameValuePair("su", Base64.getEncoder().encodeToString(username.getBytes())),
                new BasicNameValuePair("service", "sso"),
                new BasicNameValuePair("sp", password),
                new BasicNameValuePair("sr", "1024*768"),
                new BasicNameValuePair("encoding", "UTF-8"),
                new BasicNameValuePair("cdult", "3"),
                new BasicNameValuePair("domain", "sina.com.cn"),
                new BasicNameValuePair("prelt", "0"),
                new BasicNameValuePair("returntype", "TEXT")
        };
        return pairs;
    }

    /**
     * 获取CSDN发送参数
     * @param bz 0阿贝云, 1 三丰云
     * @return
     */
    public static List getSendCSDN(int bz){
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("titl","test2"));
        list.add(new BasicNameValuePair("typ","1"));
        list.add(new BasicNameValuePair("cont","<pre>test2</pre>"));
        list.add(new BasicNameValuePair("categories",""));
        list.add(new BasicNameValuePair("chnl","2"));
        list.add(new BasicNameValuePair("level","0"));
        list.add(new BasicNameValuePair("tag2","免费服务器"));
        list.add(new BasicNameValuePair("artid","0"));
        list.add(new BasicNameValuePair("private","false"));
        list.add(new BasicNameValuePair("stat","publish"));
        return list;
    }


    /**
     * CSDN登录总共3个请求  经测试,只有两个接口返回cookie
     * 获取CSDN登录参数1
     * @param userName
     * @return
     */
    public static NameValuePair[] getCSDNLogin1(String userName){
        NameValuePair pair[] = new NameValuePair[]{
                new BasicNameValuePair("type","1"),
                new BasicNameValuePair("userIdentification",userName),
        };
        return pair;
    }

    /**
     * CSDN登录总共3个请求  经测试,只有两个接口返回cookie
     * 获取CSDN登录参数2
     * @param userInfo
     * @return
     */
    public static JSONObject getCSDNLogin2(UserInfo userInfo){
        JSONObject json = new JSONObject();
        json.put("loginType","1");
        json.put("userIdentification",userInfo.getCsdnusername());
        json.put("pwdOrVerifyCode",userInfo.getCsdnpassword());
        json.put("webUmidToken","must not null-demo_liu");
        return json;
    }

    /**
     * 根据类型返回不同删除参数
     * @param articleId
     * @param type 1删除, 2从回收站删除
     * @return
     */
    public static List getCSDNDelete(String articleId, int type){
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("articleId",articleId));
        if(type!=1){
            list.add(new BasicNameValuePair("deep","true"));
        }
        return list;
    }
}
