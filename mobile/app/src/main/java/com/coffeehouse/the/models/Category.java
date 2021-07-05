package com.coffeehouse.the.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Category implements Serializable {

    private String id = "";
    private String title = "";
    private String imageUrl = "";

    public Category() {
    }

    public Category(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id);
    }

    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("title", title);
            put("imageUrl", imageUrl);
        }};
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
