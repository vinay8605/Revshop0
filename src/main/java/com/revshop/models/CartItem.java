package com.revshop.models;

public class CartItem {
    private int id;
    private int cartId;
    private int productId;
    private int quantity;
    private double productPrice;
    private double totalPrice;
    private String productName;
    
    
    public CartItem(int productId, int quantity, double productPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    
    

    
    public CartItem() {}
    public CartItem(int productId, int quantity, double productPrice, String productName) {
        this.productId = productId;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.productName = productName;
        this.totalPrice = quantity * productPrice; 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.quantity * this.productPrice; 
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
        this.totalPrice = this.quantity * this.productPrice; 
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
