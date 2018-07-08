package com.example.chen.kechensj.bean;

import java.util.List;

public class OrderBean {
    private int shopId;
    private String shopName;
    private List<CommodityBean> commodityBeanList;

    public OrderBean() {
    }

    public OrderBean(int shopId, String shopName, List<CommodityBean> commodityBeanList) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.commodityBeanList = commodityBeanList;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", commodityBeanList=" + commodityBeanList +
                '}';
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<CommodityBean> getCommodityBeanList() {
        return commodityBeanList;
    }

    public void setCommodityBeanList(List<CommodityBean> commodityBeanList) {
        this.commodityBeanList = commodityBeanList;
    }

    public static class CommodityBean {
        private int id;
        private int shopId;
        private String shopName;
        private int productId;
        private String productName;
        private String price;
        private String defaultPic;
        private int count;

        public CommodityBean() {
        }

        public CommodityBean(int id, int shopId, String shopName, int productId, String productName, String price, String defaultPic, int count) {
            this.id = id;
            this.shopId = shopId;
            this.shopName = shopName;
            this.productId = productId;
            this.productName = productName;
            this.price = price;
            this.defaultPic = defaultPic;
            this.count = count;
        }

        @Override
        public String toString() {
            return "CommodityBean{" +
                    "id=" + id +
                    ", shopId=" + shopId +
                    ", shopName='" + shopName + '\'' +
                    ", productId=" + productId +
                    ", productName='" + productName + '\'' +
                    ", price='" + price + '\'' +
                    ", defaultPic='" + defaultPic + '\'' +
                    ", count=" + count +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDefaultPic() {
            return defaultPic;
        }

        public void setDefaultPic(String defaultPic) {
            this.defaultPic = defaultPic;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
