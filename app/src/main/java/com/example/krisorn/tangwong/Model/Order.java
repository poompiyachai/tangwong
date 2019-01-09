package com.example.krisorn.tangwong.Model;

public class Order {
    private String productID;
    private String productName;
    private String quanlity;
    private String price;

    public Order() {
    }

    public Order(String productID, String productName, String quanlity, String price) {
        this.productID = productID;
        this.productName = productName;
        this.quanlity = quanlity;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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
