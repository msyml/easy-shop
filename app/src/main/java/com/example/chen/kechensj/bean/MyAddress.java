package com.example.chen.kechensj.bean;

public class MyAddress {
    private int aid;
    private String address;
    private String name;
    private String mobile;
    private String check;
    private int uid;

    @Override
    public String toString() {
        return "MyAddress{" +
                "aid=" + aid +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", check='" + check + '\'' +
                ", uid=" + uid +
                '}';
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public MyAddress() {
    }

    public MyAddress(int aid, String address, String name, String mobile, String check, int uid) {
        this.aid = aid;
        this.address = address;
        this.name = name;
        this.mobile = mobile;
        this.check = check;
        this.uid = uid;
    }
}
