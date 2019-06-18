package com.ff.postpone.task.util;

/**
 * @Author Demo-Liu
 * @Date 2019/3/15 23:33
 * @description
 */
public class UrlUtil {

    private static String cloudPubBody = "\n" +
            "<p>1.首先安装操作系统</p>\n" +
            "\n" +
            "<p><img alt=\"\" class=\"has\" src=\"https://img-blog.csdnimg.cn/20190618145028512.png\" /></p>\n" +
            "\n" +
            "<p>2.看一下基本信息</p>\n" +
            "\n" +
            "<p><img alt=\"\" class=\"has\" src=\"https://img-blog.csdnimg.cn/20190618145103314.png\" /></p>\n" +
            "\n" +
            "<p>3.并且支持创建快照</p>\n" +
            "\n" +
            "<p><img alt=\"\" class=\"has\" src=\"https://img-blog.csdnimg.cn/20190618145121816.png\" /></p>";

    //阿贝云
    public static String ABEI_TITLE = "MyServer";
    public static String ABEI_BODY = "<p>最近的阿贝云 好像挺火的,下面来看一下&nbsp;&nbsp;<a href=\"https://www.abeiyun.com/\">https://www.abeiyun.com</a></p>\n" + cloudPubBody;

    //三丰云
    public static String SANFENG_TITLE = "MyServer";
    public static String SANFENG_BODY = "<p>最近的三丰云 好像挺火的,下面来看一下&nbsp;&nbsp;<a href=\"https://www.sanfengyun.com/\">https://www.abeiyun.com</a></p>\n" + cloudPubBody;


    //阿贝云
    public static String ABEI_LOGIN="https://api.abeiyun.com/www/login.php";
    public static String ABEI_LOGIC="https://api.abeiyun.com/www/renew.php";

    //三丰云
    public static String SANFENG_LOGIN="https://api.sanfengyun.com/www/login.php";
    public static String SANFENG_LOGIC="https://api.sanfengyun.com/www/renew.php";

    //新浪登录接口
    public static String SINA_LOGIN="https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.15)&_=";
    //新浪发送博客接口
    public static String SINA_SEND="http://control.blog.sina.com.cn/admin/article/article_post.php";
    //新浪删除接口
    public static String SINA_DELETE="http://control.blog.sina.com.cn/admin/article/article_del_recycle.php?domain=1";
    //新浪回收站删除接口
    public static String SINA_DEEP="http://control.blog.sina.com.cn/admin/article/article_recycle_del.php";

    //CSDN登录接口1
    public static String CSDN_LOGIN1="https://passport.csdn.net/v1/register/checkCaptchaSwitch";
    //CSDN登录接口2
    public static String CSDN_LOGIN2="https://passport.csdn.net/v1/register/pc/login/doLogin";
    //CSDN发送博客接口
    public static String CSDN_SEND="https://mp.csdn.net/postedit/saveArticle?isPub=1";
    //CSDN删除接口
    public static String getCSDNDelete(String username){
        return "https://blog.csdn.net/"+username+"/phoenix/article/delete";
    }
    //CSDN回收站删除接口
    public static String CSDN_DEEP="https://blog.csdn.net//pheapi/article/delete";




}
