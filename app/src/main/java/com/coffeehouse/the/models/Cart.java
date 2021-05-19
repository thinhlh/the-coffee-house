package com.coffeehouse.the.models;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    // A Map of Products that user choose include ProductId and its quantity
    private Map<String,Integer> items=new HashMap<>();

    public void addItem(String productId,int quantity){
        if(items.containsKey(productId)){
            items.put(productId,items.get(productId)+quantity);
        }
        else {
            items.putIfAbsent(productId,quantity);
        }
    }

    public void deleteItem(String productId){
        items.remove(productId);
    }

    public void clear(String productId){
        items.clear();
    }

}