package com.coffeehouse.the.adapter;

public interface Searchable {

    //Always remember to copy items before calling this method inside adapter constructor or adapter.getItems()
    void filter(String query);
}
