package com.coffeehouse.the.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private String id = "";
    private String name = "";
    private String address = "";
    private LatLng coordinate = new LatLng(0, 0);
    private List<String> imageUrls = new ArrayList<>();

    public Store() {

    }

    public Store(String id, String name, String address, LatLng coordinate, List<String> imageUrls) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.coordinate = coordinate;
        this.imageUrls = imageUrls;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", coordinate=" + coordinate +
                ", imageUrls=" + imageUrls +
                '}';
    }

    public static Store fromQueryDocumentSnapshot(QueryDocumentSnapshot data) {
        GeoPoint geoPoint = data.getGeoPoint("coordinate");
        LatLng coordinate = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
        return new Store(data.getId(), data.getString("name"), data.getString("address"), coordinate, (List<String>) data.get("imageUrls"));
    }
}
