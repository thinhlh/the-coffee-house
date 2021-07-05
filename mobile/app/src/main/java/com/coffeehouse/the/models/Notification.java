package com.coffeehouse.the.models;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Notification {
    private String id = "";
    private Date dateTime = Date.from(Instant.now());
    private String title = "";
    private String description = "";
    private String imageUrl = "";
    private List<String> targetCustomer = new ArrayList<>();

    public Notification() {
    }

    public Notification(Date dateTime, String title, String description) {
        this.dateTime = dateTime;
        this.title = title;
        this.description = description;
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
    }

    public String toGson() {
        return new Gson().toJson(this);
    }

    public static Notification fromGson(String gSon) {
        return new Gson().fromJson(gSon, Notification.class);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("dateTime", dateTime);
        map.put("description", description);
        map.put("imageUrl", imageUrl);
        map.put("title", title);
        map.put("targetCustomer", targetCustomer);
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
