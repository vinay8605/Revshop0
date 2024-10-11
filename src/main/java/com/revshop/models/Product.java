package com.revshop.models;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int seller_id;
	private String image_url;

    public Product() {}

    public Product(String name, String description, double price, String image_url, int seller_id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
        this.seller_id = seller_id;
    }

    public Product(int id, String name, String description, double price, String image_url, int seller_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
        this.seller_id = seller_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public int getSellerId() {
        return seller_id;
    }

    public void setSellerId(int seller_id) {
        this.seller_id = seller_id;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description +
               ", price=" + price + ", image_url=" + image_url + ", seller_id=" + seller_id + "]";
    }
}
