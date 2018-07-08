package com.example.chen.kechensj.bean;

import java.util.List;

public class Commodity {

    private List<Date> date;

    public List<Date> getDate() {
        return date;
    }

    public void setDate(List<Date> date) {
        this.date = date;
    }

    public class Date {

        private int pid;
        private String pname;
        private double price;
        private String pimage;
        private String pdescripe;
        private int sale_number;
        private int shop_id;
        private int category_id;
        private String date;
        private double inventory;

        public Date() {
        }

        public Date(int pid, String pname, double price, String pimage, String pdescripe, int sale_number, int shop_id, int category_id, String date, double inventory) {
            this.pid = pid;
            this.pname = pname;
            this.price = price;
            this.pimage = pimage;
            this.pdescripe = pdescripe;
            this.sale_number = sale_number;
            this.shop_id = shop_id;
            this.category_id = category_id;
            this.date = date;
            this.inventory = inventory;
        }

        @Override
        public String toString() {
            return "Commodity [pid=" + pid + ", pname=" + pname + ", price=" + price + ", pimage=" + pimage
                    + ", pdescripe=" + pdescripe + ", sale_number=" + sale_number + ", shop_id=" + shop_id
                    + ", category_id=" + category_id + ", date=" + date + ", inventory=" + inventory + "]";
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getPimage() {
            return pimage;
        }

        public void setPimage(String pimage) {
            this.pimage = pimage;
        }

        public String getPdescripe() {
            return pdescripe;
        }

        public void setPdescripel(String pdescripe) {
            this.pdescripe = pdescripe;
        }

        public int getSale_number() {
            return sale_number;
        }

        public void setSale_number(int sale_number) {
            this.sale_number = sale_number;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getInventory() {
            return inventory;
        }

        public void setInventory(double inventory) {
            this.inventory = inventory;
        }

    }
}


