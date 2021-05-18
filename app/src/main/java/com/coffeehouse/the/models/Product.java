package com.coffeehouse.the.models;

import java.util.Map;

public class Product {
    private String id;
    private String title;
    private String description;
    private String categoryId;
    private String imageUrl;
    private int price;

    public Product() {
        this.title = "";
        this.description = "";
        this.categoryId = "";
        this.imageUrl = "";
        this.price = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void fromJson(Map<String, Object> json) {
        this.title = (String) json.get("title");
        this.description = (String) json.get("description");
        this.categoryId = (String) json.get("categoryId");
        this.imageUrl = (String) json.get("imageUrl");
        this.price = Integer.parseInt((String) json.get("price"));
    }
}
