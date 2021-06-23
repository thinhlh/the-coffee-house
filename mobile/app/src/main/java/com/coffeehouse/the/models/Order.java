package com.coffeehouse.the.models;

import com.coffeehouse.the.services.UserRepo;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.text.Format;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Order {

    private String id = "";
    private Date orderTime = Date.from(Instant.now());
    private Cart cart = new Cart();
    private String userId = "";
    private String orderMethod = "";
    private boolean delivered;
    private String orderAddress = "";
    private String recipientName = "";
    private String recipientPhone = "";

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
        return cart.getItems().stream().mapToInt(CartItem::getTotalCartItemValue).sum();
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

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public static Order fromMap(Map<String, Object> map) {
        Order order = new Order();

        order.setId((String) map.get("id"));
        order.setUserId((String) map.get("userId"));
        order.setOrderTime(((Timestamp) map.get("birthday")).toDate());

        List<Object> cartItemList = (List<Object>) map.get("cart");
        Cart cart = new Cart();

        return order;
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
