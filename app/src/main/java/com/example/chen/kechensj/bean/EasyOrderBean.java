package com.example.chen.kechensj.bean;

public class EasyOrderBean {

    private String id;
    private int count;


    @Override
    public String toString() {
        return "Data [id=" + id + ", count=" + count + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
