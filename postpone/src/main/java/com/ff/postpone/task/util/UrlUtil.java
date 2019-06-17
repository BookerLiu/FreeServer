package com.ff.postpone.task.util;

/**
 * @Author Demo-Liu
 * @Date 2019/3/15 23:33
 * @description
 */
public class UrlUtil {


    //阿贝云
    public static String ABEI_TITLE = "谈一谈近期使用阿贝云服务器的感受";
    public static String ABEI_BODY = "总的来说,阿贝云的免费服务器,拥有5M带宽,这在免费当中真的是良心满满了,此外,阿贝云还有2G2H5M、15元/月 4G4H5M、25元/月 优惠云服务器 可以购买,很实惠的,真的是很棒!!!";
    //三丰云
    public static String SANFENG_TITLE = "谈一谈近期使用三丰云服务器的感受";
    public static String SANFENG_BODY = "总的来说,三丰云的免费服务器,拥有5M带宽,这在免费当中真的是良心满满了,此外,三丰云还有2G2H5M、15元/月 4G4H5M、25元/月 优惠云服务器 可以购买,很实惠的,真的是很棒!!!地址:https://www.sanfengyun.com";


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
