package com.coffeehouse.the.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Store implements Serializable {
    private String id = "";
    private String name = "";
    private String address = "";
    private LatLng coordinate = new LatLng(0, 0);
    private String imageUrl = "";

    public Store() {

    }

    public Store(String id, String name, String address, LatLng coordinate, String imageUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.coordinate = coordinate;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(LatLng coordinate) {
        this.coordinate = coordinate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", coordinate=" + coordinate +
                ", imageUrl=" + imageUrl +
                '}';
    }

    public static Store fromQueryDocumentSnapshot(QueryDocumentSnapshot data) {
        GeoPoint geoPoint = data.getGeoPoint("coordinate");
        LatLng coordinate = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
        return new Store(data.getId(), data.getString("name"), data.getString("address"), coordinate, (String) data.get("imageUrl"));
    }

    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("name", name);
            put("address", address);
            put("coordinate", new GeoPoint(coordinate.latitude, coordinate.longitude));
            put("imageUrl", imageUrl);
        }};
    }

    public String toGson() {
        return new Gson().toJson(this);
    }

    public static Store fromGson(String gson) {
        return new Gson().fromJson(gson, Store.class);
    }
}
