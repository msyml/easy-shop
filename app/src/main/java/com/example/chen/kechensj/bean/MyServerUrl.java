package com.example.chen.kechensj.bean;

import com.example.chen.kechensj.application.MyApplication;

public class MyServerUrl {
    private MyApplication myApplication = new MyApplication();
    private String ipAddress = myApplication.getIpAddress();
    private String head = "http://" + ipAddress + "/Kcsj/";
    private String LoginUrl = head + "LoginServlet";

    public String getLoginUrl() {
        return LoginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        LoginUrl = loginUrl;
    }
}
