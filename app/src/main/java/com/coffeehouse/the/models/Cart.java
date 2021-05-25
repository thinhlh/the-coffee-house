package com.coffeehouse.the.models;

import java.util.ArrayList;
import java.util.List;

//Cart class to be used at client side, can be convert to Order object for further information
public class Cart {

    // A Map of Products that user choose include ProductId and its quantity
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        int index = findCartItemByProductId(item.getProductId());
        if (index == -1) {
            items.add(item);
        } else {
            items.get(index).increaseQuantity(item.getQuantity());
        }
    }

    public void deleteItem(String productId) {
        int index = findCartItemByProductId(productId);
        items.remove(index);
    }

    public void clear() {
        items.clear();
    }

    private int findCartItemByProductId(String productId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProductId().equals(productId))
                return i;
        }
        return -1;
    }

    public int getTotalCartValue() {
        int total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getTotalCartItemValue();
        }
        return total;
    }

}