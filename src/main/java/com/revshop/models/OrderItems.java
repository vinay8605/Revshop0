package com.revshop.models;

import java.sql.Timestamp;

public class OrderItems {

    private int orderItemId; 
    private int orderId;      
    private int sellerId;     
    private int productId;    
    private int quantity;      
    private double priceAtTime; 
    private double discountedPriceAtTime; 
    private String imageUrl;   
    private String productName; 
    private Timestamp addedDate; 

    
    public OrderItems() {
        this.orderItemId = generateRandomId(); 
        this.addedDate = new Timestamp(System.currentTimeMillis()); 
    }

    
    public OrderItems(int orderId, int productId, int sellerId, int quantity, double priceAtTime, double discountedPriceAtTime, String imageUrl, String productName) {
        this.orderItemId = generateRandomId(); 
        this.orderId = orderId;
        this.productId = productId;
        this.sellerId = sellerId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
        this.discountedPriceAtTime = discountedPriceAtTime;
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.addedDate = new Timestamp(System.currentTimeMillis()); 
    }


    public OrderItems(int orderId, int productId, int sellerId, int quantity, double priceAtTime, double discountedPriceAtTime) {
        this(orderId, productId, sellerId, quantity, priceAtTime, discountedPriceAtTime, null, null); 
    }

    
    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(double priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    public double getDiscountedPriceAtTime() {
        return discountedPriceAtTime;
    }

    public void setDiscountedPriceAtTime(double discountedPriceAtTime) {
        this.discountedPriceAtTime = discountedPriceAtTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    private int generateRandomId() {
        return (int) (Math.random() * Integer.MAX_VALUE); 
    }

    
    @Override
    public String toString() {
        return "OrderItems{" +
                "orderItemId=" + orderItemId +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", sellerId=" + sellerId +
                ", quantity=" + quantity +
                ", priceAtTime=" + priceAtTime +
                ", discountedPriceAtTime=" + discountedPriceAtTime +
                ", imageUrl='" + imageUrl + '\'' +
                ", productName='" + productName + '\'' +
                ", addedDate=" + addedDate +
                '}';
    }
}
