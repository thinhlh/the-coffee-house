package com.coffeehouse.the.models;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomUser {
    protected String email = "";
    protected String name = "";
    protected int point = 0;
    protected String phoneNumber = "";
    protected Date birthday = Date.from(Instant.now());
    protected Membership membership = Membership.Bronze;
    protected List<String> favoriteProducts = new ArrayList<>();
    protected boolean subscribeToNotifications = true;
    protected boolean admin = false;


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

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void fromJson(Map<String, Object> json) {
        this.email = (String) json.get("email");
        this.name = (String) json.get("name");
        this.point = Integer.parseInt((String) json.get("point"));
        this.phoneNumber = (String) json.getOrDefault("phoneNumber", "");
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
        this.admin = (boolean) json.get("isAdmin");
    }

    public String birthdayString() {
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        return df.format(birthday);
    }

    public void setSubscribeToNotifications(boolean subscribeToNotifications) {
        this.subscribeToNotifications = subscribeToNotifications;
    }

    public boolean getSubscribeToNotifications() {
        return subscribeToNotifications;
    }

    public String membershipString() {
        String _membership;
        switch (membership) {
            case Gold:
                _membership = "Vàng";
                break;
            case Silver:
                _membership = "Bạc";
                break;
            case Diamond:
                _membership = "Kim Cương";
                break;
            default:
                _membership = "Đồng";
        }
        return _membership;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomUser)) return false;
        CustomUser that = (CustomUser) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name);
    }
}
