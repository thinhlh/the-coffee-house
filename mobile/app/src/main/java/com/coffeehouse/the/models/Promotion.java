package com.coffeehouse.the.models;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Promotion {
    private String id = "";
    private String code = "";
    private String title = "";
    private String description = "";
    private String imageUrl = "";
    private String value = "";
    private List<String> targetCustomer = new ArrayList<>();
    private Date expiryDate = Date.from(Instant.now());

    public Promotion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getTargetCustomer() {
        return targetCustomer;
    }

    public void setTargetCustomer(List<String> targetCustomer) {
        this.targetCustomer = targetCustomer;
    }

    public String getFormattedExpiryDate() {
        return new SimpleDateFormat("dd-MM-yy").format(this.expiryDate);
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getValueToInt() {
        int count = 0;
        int number = 0;
        while (value.charAt(count) != '%') {
            number *= 10;
            number += value.charAt(count++) - '0';
        }
        return number;
    }
}