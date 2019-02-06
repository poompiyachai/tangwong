package com.example.krisorn.tangwong.Model;

public class Order {
    private String productID;
    private String productName;
    private String quanlity;
    private String price;
    private String livenow;

    public Order() {
    }

    public String getLivenow() {
        return livenow;
    }

    public void setLivenow(String livenow) {
        this.livenow = livenow;
    }

    public Order(String livenow) {
        this.livenow = livenow;
    }

    public Order(String livenow, String productName, String quanlity, String price) {

        this.productName = productName;
        this.quanlity = quanlity;
        this.price = price;
        this.livenow=livenow;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuanlity() {
        return quanlity;
    }

    public void setQuanlity(String quanlity) {
        this.quanlity = quanlity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
