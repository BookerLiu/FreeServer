package com.ff.postpone.pojo;

import java.util.Date;

public class UserInfo {

    private Integer id;

    private String yunusername;

    private String yunpassword;

    private String sinausername;

    private String sinapassword;

    private Date nextTime;

    private String sinaUrl;

    private String status;

    private String bz;

    private String sinaCookie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYunusername() {
        return yunusername;
    }

    public void setYunusername(String yunusername) {
        this.yunusername = yunusername;
    }

    public String getYunpassword() {
        return yunpassword;
    }

    public void setYunpassword(String yunpassword) {
        this.yunpassword = yunpassword;
    }

    public String getSinausername() {
        return sinausername;
    }

    public void setSinausername(String sinausername) {
        this.sinausername = sinausername;
    }

    public String getSinapassword() {
        return sinapassword;
    }

    public void setSinapassword(String sinapassword) {
        this.sinapassword = sinapassword;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public String getSinaUrl() {
        return sinaUrl;
    }

    public void setSinaUrl(String sinaUrl) {
        this.sinaUrl = sinaUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSinaCookie() {
        return sinaCookie;
    }

    public void setSinaCookie(String sinaCookie) {
        this.sinaCookie = sinaCookie;
    }
}