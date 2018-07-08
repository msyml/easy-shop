package com.example.chen.kechensj.bean;

public class Orderfrom {
    private int uid;
    private int cid;
    private int oid;
    private String address;
    private String start_time;
    private String state;
    private String over_time;


    public Orderfrom() {
        super();
    }

    public Orderfrom(int uid, int cid, int oid, String address, String start_time, String state, String over_time) {
        super();
        this.uid = uid;
        this.cid = cid;
        this.oid = oid;
        this.address = address;
        this.start_time = start_time;
        this.state = state;
        this.over_time = over_time;
    }


    @Override
    public String toString() {
        return "Orderfrom [uid=" + uid + ", cid=" + cid + ", oid=" + oid + ", address=" + address + ", start_time="
                + start_time + ", state=" + state + ", over_time=" + over_time + "]";
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOver_time() {
        return over_time;
    }

    public void setOver_time(String over_time) {
        this.over_time = over_time;
    }
}
