package com.coffeehouse.the.models;

import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.viewmodel.NotificationViewModel;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Notification {
    private String id;
    private Date dateTime;
    private String description;
    private String imageUrl;
    private List<String> targetCustomerString = new ArrayList<>();
    private List<String> targetCustomer;
    private String title;

    public Notification() {
        this.id = "";
        this.dateTime = Date.from(Instant.EPOCH);
        this.description = "";
        this.imageUrl = "";
        this.title = "";
        this.targetCustomer = new ArrayList<>();
    }


    public List<String> getTargetCustomer() {
        return targetCustomer;
    }

    public void setTargetCustomer(List<String> targetCustomer) {
        this.targetCustomer = targetCustomer;
    }

    public Notification setNoti(Notification noti) {
        return noti;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void fromJson(Map<String, Object> json) {
        this.title = (String) json.get("title");
        this.dateTime = Date.from(((Timestamp) json.get("dateTime")).toInstant());
        this.description = (String) json.get("description");
        this.imageUrl = (String) json.get("imageUrl");
        this.targetCustomer = (List<String>) json.get("targetCustomer");
//        this.targetCustomerString = (List<String>) json.get("targetCustomer");
//        for (int i = 0; i < targetCustomerString.size(); i++) {
//            switch (targetCustomerString.get(i)) {
//                case "Membership.Silver":
//                    this.targetCustomer.add(Membership.Silver);
//                    break;
//                case "Membership.Gold":
//                    this.targetCustomer.add(Membership.Gold);
//                    break;
//                case "Membership.Diamond":
//                    this.targetCustomer.add(Membership.Diamond);
//                    break;
//                case "Membership.Bronze":
//                    this.targetCustomer.add(Membership.Bronze);
//                    break;
//            }
//        }
    }
}
