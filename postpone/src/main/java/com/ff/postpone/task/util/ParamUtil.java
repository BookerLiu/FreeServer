package com.ff.postpone.task.util;

import com.ff.postpone.pojo.UserInfo;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Demo-Liu
 * @Date 2019/3/19 10:50
 * @description
 */
public class ParamUtil {


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
     * 获取云登录参数
     * @param username
     * @param password
     * @return
     */
    public static List<NameValuePair> getYunLogin(String username, String password){
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("cmd","login"));
        list.add(new BasicNameValuePair("id_mobile",username));
        list.add(new BasicNameValuePair("password",password));
        return list;
    }

    /**
     * 获取查询服务器状态参数
     * @return
     */
    public static List<NameValuePair> getFreeStatus(){
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("cmd","check_free_delay"));
        list.add(new BasicNameValuePair("ptype","vps"));
        return  list;
    }

    /**
     * 获取审核状态参数
     * @return
     */
    public static List<NameValuePair> getCheckStatus(){
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("cmd","free_delay_list"));
        list.add(new BasicNameValuePair("ptype","vps"));
        list.add(new BasicNameValuePair("count","20"));
        list.add(new BasicNameValuePair("page","1"));
        return list;
    }

    /**
     * 获取发送微博参数
     * @param cloudType
     * @return
     */
    public static List<NameValuePair> getSendBlog(int cloudType){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(new Date());
        String[] strs = format.split(" ");
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("ptype",""));
        list.add(new BasicNameValuePair("teams",""));
        list.add(new BasicNameValuePair("worldcuptags",""));
        list.add(new BasicNameValuePair("album",""));
        list.add(new BasicNameValuePair("album_cite",""));
        list.add(new BasicNameValuePair("blog_id",""));
        list.add(new BasicNameValuePair("is_album","0"));
        list.add(new BasicNameValuePair("old365","0"));
        list.add(new BasicNameValuePair("stag",""));
        list.add(new BasicNameValuePair("sno",""));
        list.add(new BasicNameValuePair("book_worksid",""));
        list.add(new BasicNameValuePair("channel_id",""));
        list.add(new BasicNameValuePair("url",""));
        list.add(new BasicNameValuePair("channel",""));
        list.add(new BasicNameValuePair("newsid",""));
        list.add(new BasicNameValuePair("fromuid",""));
        list.add(new BasicNameValuePair("wid",""));
        list.add(new BasicNameValuePair("articletj",""));
        list.add(new BasicNameValuePair("vtoken","4fcebcbd1a24c119fd14998b8c9c635d"));
        list.add(new BasicNameValuePair("is_media","0"));
        list.add(new BasicNameValuePair("is_stock","0"));
        list.add(new BasicNameValuePair("is_tpl","0"));
        list.add(new BasicNameValuePair("assoc_article",""));
        list.add(new BasicNameValuePair("assoc_style","0"));
        list.add(new BasicNameValuePair("assoc_article_data",""));
        list.add(new BasicNameValuePair("article_BGM",""));
        list.add(new BasicNameValuePair("xRankStatus",""));
        list.add(new BasicNameValuePair("commentGlobalSwitch",""));
        list.add(new BasicNameValuePair("commenthideGlobalSwitch",""));
        list.add(new BasicNameValuePair("articleStatus_preview","1"));
        list.add(new BasicNameValuePair("source",""));
        list.add(new BasicNameValuePair("topic_id","0"));
        list.add(new BasicNameValuePair("topic_channel","0"));
        list.add(new BasicNameValuePair("topic_more",""));
        list.add(new BasicNameValuePair("utf8","1"));
        //list.add(//new BasicNameValuePair("conlen","96"));
        list.add(new BasicNameValuePair("date_pub",strs[0]));
        list.add(new BasicNameValuePair("time",strs[1]));
        list.add(new BasicNameValuePair("new_time",""));
        list.add(new BasicNameValuePair("isTimed","0"));
        list.add(new BasicNameValuePair("immediatepub","0"));
        list.add(new BasicNameValuePair("blog_title", cloudType==0 ? UrlUtil.ABEI_TITLE + (int)(Math.random()*100000000) : UrlUtil.SANFENG_TITLE + (int)(Math.random()*10000000)));
        list.add(new BasicNameValuePair("blog_body", cloudType==0 ? UrlUtil.ABEI_BODY : UrlUtil.SANFENG_BODY));
        list.add(new BasicNameValuePair("blog_class","00"));
        list.add(new BasicNameValuePair("tag","it"));
        list.add(new BasicNameValuePair("x_cms_flag","2"));
        list.add(new BasicNameValuePair("x_quote_flag","1"));
        list.add(new BasicNameValuePair("sina_sort_id","117"));
        return list;
    }


    /**
     * 获取新浪微博登录参数
     * @param username
     * @param password
     * @return
     */
    public static List<NameValuePair> getSinaLogin(String username, String password){
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("entry", "sso"));
        list.add(new BasicNameValuePair("gateway", "1"));
        list.add(new BasicNameValuePair("from", "null"));
        list.add(new BasicNameValuePair("savestate", "30"));
        list.add(new BasicNameValuePair("useticket", "0"));
        list.add(new BasicNameValuePair("pagerefer", ""));
        list.add(new BasicNameValuePair("vsnf", "1"));
        list.add(new BasicNameValuePair("su", Base64.getEncoder().encodeToString(username.getBytes())));
        list.add(new BasicNameValuePair("service", "sso"));
        list.add(new BasicNameValuePair("sp", password));
        list.add(new BasicNameValuePair("sr", "1024*768"));
        list.add(new BasicNameValuePair("encoding", "UTF-8"));
        list.add(new BasicNameValuePair("cdult", "3"));
        list.add(new BasicNameValuePair("domain", "sina.com.cn"));
        list.add(new BasicNameValuePair("prelt", "0"));
        list.add(new BasicNameValuePair("returntype", "TEXT"));
        return list;
    }

    /**
     * 获取CSDN发送参数
     * @param cloudType 0阿贝云, 1 三丰云
     * @return
     */
    public static List getSendCSDN(int cloudType){
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("titl",UrlUtil.ABEI_TITLE));
        list.add(new BasicNameValuePair("typ","1"));
        list.add(new BasicNameValuePair("cont",cloudType==0 ? UrlUtil.ABEI_BODY : UrlUtil.SANFENG_BODY));
        list.add(new BasicNameValuePair("categories",""));
        list.add(new BasicNameValuePair("chnl","31"));
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
        json.put("userIdentification",userInfo.getBlogUser());
        json.put("pwdOrVerifyCode",userInfo.getBlogPass());
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
