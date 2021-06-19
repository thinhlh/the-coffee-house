package com.coffeehouse.the.models;

import com.coffeehouse.the.services.UserRepo;
import com.google.firebase.auth.FirebaseAuth;

import java.text.Format;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class Order {

    private String id = "";
    private Date orderTime = Date.from(Instant.now());
    private Cart cart = new Cart();
    private int total = 0;
    private String userId = "";

    public Order() {

    }

    public Order(Cart cart) {
        this.cart = cart;
        this.total = cart.getTotalCartValue();
        this.userId = FirebaseAuth.getInstance().getUid();
    }

    public Order(String id, Cart cart) {
        this.id = id;
        this.cart = cart;
        this.total = cart.getTotalCartValue();
        this.userId = FirebaseAuth.getInstance().getUid();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setItems(Cart cart) {
        this.cart = cart;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
