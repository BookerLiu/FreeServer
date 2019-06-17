package com.ff.postpone.pojo;

import java.util.Date;

public class UserInfo {
    private Integer id;

    private String cloudUser;

    private String cloudPass;

    private String blogUser;

    private String blogPass;

    private String blogUrl;

    private String blogCookie;

    private Date nextTime;

    private String cloudType;

    private String blogType;

    private String cookieType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCloudUser() {
        return cloudUser;
    }

    public void setCloudUser(String cloudUser) {
        this.cloudUser = cloudUser;
    }

    public String getCloudPass() {
        return cloudPass;
    }

    public void setCloudPass(String cloudPass) {
        this.cloudPass = cloudPass;
    }

    public String getBlogUser() {
        return blogUser;
    }

    public void setBlogUser(String blogUser) {
        this.blogUser = blogUser;
    }

    public String getBlogPass() {
        return blogPass;
    }

    public void setBlogPass(String blogPass) {
        this.blogPass = blogPass;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getBlogCookie() {
        return blogCookie;
    }

    public void setBlogCookie(String blogCookie) {
        this.blogCookie = blogCookie;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public String getCloudType() {
        return cloudType;
    }

    public void setCloudType(String cloudType) {
        this.cloudType = cloudType;
    }

    public String getBlogType() {
        return blogType;
    }

    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }

    public String getCookieType() {
        return cookieType;
    }

    public void setCookieType(String cookieType) {
        this.cookieType = cookieType;
    }
}