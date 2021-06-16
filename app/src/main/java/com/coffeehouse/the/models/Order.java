package com.coffeehouse.the.models;

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

    public Order() {

    }

    public Order(Cart cart) {
        this.cart = cart;
        this.total = cart.getTotalCartValue();
    }

    public Order(String id, Cart cart) {
        this.id = id;
        this.cart = cart;
        this.total = cart.getTotalCartValue();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
