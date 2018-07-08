package com.example.chen.kechensj.bean;

public class RecycleItem {
    private String id;
    private String image;
    private String name;
    private String price;

    private String number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RecycleItem(String id, String image, String name, String price, String number) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;

        this.number = number;
    }


    public RecycleItem() {
    }

    public RecycleItem(String image, String name, String price, String number) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "RecycleItem{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
