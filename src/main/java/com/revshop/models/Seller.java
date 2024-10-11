package com.revshop.models;

public class Seller {
    private int sellerId;
    private String businessName;
    private String email;
    private String password;
    private String businessDetails;

    public Seller() {}

    public Seller(String businessName, String email, String password, String businessDetails) {
        this.businessName = businessName;
        this.email = email;
        this.password = password;
        this.businessDetails = businessDetails;
    }

    public Seller(int sellerId, String businessName, String email, String password, String businessDetails) {
        this.sellerId = sellerId;
        this.businessName = businessName;
        this.email = email;
        this.password = password;
        this.businessDetails = businessDetails;
    }


    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(String businessDetails) {
        this.businessDetails = businessDetails;
    }

    @Override
    public String toString() {
        return "Seller [sellerId=" + sellerId + ", businessName=" + businessName + 
               ", email=" + email + ", businessDetails=" + businessDetails + "]";
    }
}
