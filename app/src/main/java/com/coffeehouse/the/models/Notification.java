package com.coffeehouse.the.models;

import com.google.firebase.firestore.DocumentSnapshot;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Notification {
    private String id;
    private Date dateTime;
    private String description;
    private String imageUrl;
    private String title;

    Notification() {
        this.id = "";
        this.dateTime = Date.from(Instant.EPOCH);
        this.description = "";
        this.imageUrl = "";
        this.title = "";
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


}
