package com.coffeehouse.the.models;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CustomUser {
    private String email = "";
    private String name = "";
    private int point = 0;
    private String phoneNumber = "";
    private Date birthday = Date.from(Instant.now());
    private Membership membership = Membership.Bronze;
    private List<String> favoriteProducts = new ArrayList<>();


    public CustomUser() {
    }

    public CustomUser(String email, String name, String phoneNumber, Date birthday) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public List<String> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(List<String> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    public void fromJson(Map<String, Object> json) {
        this.email = (String) json.get("email");
        this.name = (String) json.get("name");
        this.point = Integer.parseInt((String) json.get("point"));
        this.phoneNumber = (String) json.getOrDefault("phone", "");
        this.birthday = Date.from(((Timestamp) json.get("birthday")).toInstant());
        switch ((String) json.get("membership")) {
            case "Membership.Silver":
                this.membership = Membership.Silver;
                break;
            case "Membership.Gold":
                this.membership = Membership.Gold;
                break;
            case "Membership.Diamond":
                this.membership = Membership.Diamond;
                break;
            default:
                this.membership = Membership.Bronze;
                break;
        }
        this.favoriteProducts = (List<String>) json.get("favoriteProducts");
    }
}
