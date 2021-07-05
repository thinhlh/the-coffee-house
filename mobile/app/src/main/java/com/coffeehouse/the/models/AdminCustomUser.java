package com.coffeehouse.the.models;

import android.graphics.Color;

import com.google.firebase.Timestamp;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdminCustomUser extends CustomUser {

    private String id = "";
    private Date lastSignedIn = Date.from(Instant.now());
    private Date dateCreated = Date.from(Instant.now());


    public String getFormattedLastSignedIn() {
        return new SimpleDateFormat("hh:mm EEE, MMM dd, yyyy").format(lastSignedIn);
    }

    public String getFormattedDateCreated() {
        return new SimpleDateFormat("hh:mm EEE, MMM dd, yyyy").format(dateCreated);
    }

    public String getFormattedBirthday() {
        return new SimpleDateFormat("dd-MM-yy").format(birthday);
    }

    public Date getLastSignedIn() {
        return lastSignedIn;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLastSignedIn(Date lastSignedIn) {
        this.lastSignedIn = lastSignedIn;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static AdminCustomUser fromMap(Map<String, Object> map) {
        AdminCustomUser user = new AdminCustomUser();
        user.email = (String) map.get("email");
        user.name = (String) map.get("name");
        if (map.get("point") instanceof Double) {
            // For fromGson purpose, which is typically used for passsign through intent
            user.point = ((Double) map.get("point")).intValue();
        } else {
            //Used for parsing a map from server
            user.point = Math.toIntExact((Long) map.get("point"));
        }
        user.phoneNumber = (String) map.getOrDefault("phoneNumber", "");

        if (map.get("birthday") instanceof String) {
            try {
                user.birthday = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a").parse((String) map.get("birthday"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else user.birthday = ((Timestamp) map.get("birthday")).toDate();
        switch ((String) map.get("membership")) {
            case "Silver":
                user.membership = Membership.Silver;
                break;
            case "Gold":
                user.membership = Membership.Gold;
                break;
            case "Diamond":
                user.membership = Membership.Diamond;
                break;
            default:
                user.membership = Membership.Bronze;
                break;
        }
        user.favoriteProducts = (List<String>) map.get("favoriteProducts");
        user.id = (String) map.get("id");

        if (map.containsKey("admin")) {
            user.admin = (boolean) map.get("admin");
        }

        if (map.containsKey("subscribeToNotifications")) {
            user.subscribeToNotifications = (boolean) map.get("subscribeToNotifications");
        }
        return user;
    }

    public int getIconColorBasedOnMembership() {
        int color = Color.BLACK;
        switch (this.membership) {
            case Bronze:
                color = Color.parseColor("#795C32");
                break;
            case Silver:
                color = Color.GRAY;
                break;
            case Gold:
                color = Color.parseColor("#FFBF00");
                break;
            case Diamond:
                color = Color.CYAN;
        }
        return color;
    }

    private Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("email", email);
        map.put("name", name);
        map.put("phoneNumber", phoneNumber);
        map.put("birthday", birthday);
        map.put("point", point);
        map.put("membership", membership);
        map.put("favoriteProducts", favoriteProducts);
        map.put("lastSignedIn", lastSignedIn);
        map.put("dateCreated", dateCreated);
        map.put("subscribeToNotifications", subscribeToNotifications);
        map.put("admin", admin);
        return map;
    }

    public String toGson() {
        return new Gson().toJson(toMap());
    }

    public static AdminCustomUser fromGson(String gson) {
        Map map = new Gson().fromJson(gson, Map.class);
        return AdminCustomUser.fromMap(map);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminCustomUser)) return false;
        AdminCustomUser that = (AdminCustomUser) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
