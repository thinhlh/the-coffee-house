package com.coffeehouse.the.models;

import android.util.Log;

import com.coffeehouse.the.services.UserRepo;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Order {

    private String id = "";
    private Date orderTime = Date.from(Instant.now());
    private Cart cart = new Cart();
    private String userId = "";
    private String orderMethod = "";
    private String orderAddress = "";
    private String recipientName = "";
    private String recipientPhone = "";
    private boolean delivered = false;

    public Order() {

    }

    public Order(Cart cart, String orderMethod, boolean delivered, String orderAddress, String recipientName, String recipientPhone) {
        this.cart = cart;
        this.userId = FirebaseAuth.getInstance().getUid();
        this.orderMethod = orderMethod;
        this.delivered = delivered;
        this.orderAddress = orderAddress;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
    }

    public Order(String id, Cart cart) {
        this.id = id;
        this.cart = cart;
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
        return cart.getTotalCartValue();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderMethod() {
        return orderMethod;
    }

    public void setOrderMethod(String orderMethod) {
        this.orderMethod = orderMethod;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean getDelivered() {
        return delivered;
    }

    public String getFormattedOrderTime() {
        return new SimpleDateFormat("HH:mm EEEE, MMM dd").format(orderTime);
    }

    public List<String> getAllProductsId(){
        return cart.getAllProductsId();
    }

    private static Order fromMap(Map<String, Object> map) {
        Order order = new Order();

        order.setId((String) map.get("id"));
        order.setUserId((String) map.get("userId"));

        if (map.get("orderTime") instanceof String) {
            //This is used for fromGson method
            try {
                order.orderTime = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a").parse((String) map.get("orderTime"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else order.orderTime = ((Timestamp) map.get("orderTime")).toDate();
        
        order.orderMethod = String.valueOf(map.get("orderMethod"));
        order.orderAddress = (String) map.get("orderAddress");
        order.recipientName = (String) map.get("recipientName");
        order.recipientPhone = (String) map.get("recipientPhone");


        if (map.containsKey("delivered")) {
            order.setDelivered((Boolean) map.get("delivered"));
        }

        return order;
    }

    /*
     * THIS MAP IS NOT INCLUDED CART
     * */
    private Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("orderTime", orderTime);
        map.put("userId", userId);
        map.put("orderMethod", orderMethod);
        map.put("orderAddress", orderAddress);
        map.put("recipientName", recipientName);
        map.put("recipientPhone", recipientPhone);
        map.put("delivered", delivered);

        return map;
    }

    public String toGson() {
        return new Gson().toJson(toMap());
    }

    public static Order fromGson(String gson) {
        Map<String, Object> map = new Gson().fromJson(gson, HashMap.class);
        return fromMap(map);
    }


    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", orderTime=" + orderTime +
                ", cart=" + cart +
                ", userId='" + userId + '\'' +
                ", orderMethod='" + orderMethod + '\'' +
                ", orderAddress='" + orderAddress + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", recipientPhone='" + recipientPhone + '\'' +
                '}';
    }
}
