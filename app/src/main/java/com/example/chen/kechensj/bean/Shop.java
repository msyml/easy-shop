package com.example.chen.kechensj.bean;

public class Shop {
    private int shop_id;
    private String shop_name;
    private String shop_address;
    private int boss_id;
    private String shop_date;


    public Shop() {
        super();
    }

    public Shop(int shop_id, String shop_name, String shop_address, int boss_id, String shop_date) {
        super();
        this.shop_id = shop_id;
        this.shop_name = shop_name;
        this.shop_address = shop_address;
        this.boss_id = boss_id;
        this.shop_date = shop_date;
    }


    @Override
    public String toString() {
        return "Shop [shop_id=" + shop_id + ", shop_name=" + shop_name + ", shop_address=" + shop_address + ", boss_id="
                + boss_id + ", shop_date=" + shop_date + "]";
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public int getBoss_id() {
        return boss_id;
    }

    public void setBoss_id(int boss_id) {
        this.boss_id = boss_id;
    }

    public String getShop_date() {
        return shop_date;
    }

    public void setShop_date(String shop_date) {
        this.shop_date = shop_date;
    }

}
