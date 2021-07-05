package com.coffeehouse.the.models;

import com.coffeehouse.the.services.repositories.UserRepo;

import java.io.Serializable;

public class UserAddress implements Serializable {
    private String id = "";
    private String userId = "";
    private String title = "";
    private String description = "";
    private String detail = "";
    private String gate = "";
    private String note = "";
    private String recipientName = UserRepo.user.getName();
    private String recipientPhone = UserRepo.user.getPhoneNumber();

    public UserAddress() {
    }

    public UserAddress(String userId, String title, String description, String detail, String gate, String note, String recipientName, String recipientPhone) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.detail = detail;
        this.gate = gate;
        this.note = note;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
}
