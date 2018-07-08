package com.example.chen.kechensj.bean;

public class UserCollect {
    private int cid;
    private int user_id;
    private int commodity_id;
    private String date;


    public UserCollect() {
        super();
    }

    public UserCollect(int cid, int user_id, int commodity_id, String date) {
        super();
        this.cid = cid;
        this.user_id = user_id;
        this.commodity_id = commodity_id;
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserCollect [cid=" + cid + ", user_id=" + user_id + ", commodity_id=" + commodity_id + ", date=" + date
                + "]";
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
