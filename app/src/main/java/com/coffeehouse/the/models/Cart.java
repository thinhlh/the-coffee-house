package com.coffeehouse.the.models;

import java.util.ArrayList;
import java.util.List;

//Cart class to be used at client side, can be convert to Order object for further information
public class Cart {

    // A Map of Products that user choose include ProductId and its quantity
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem cartItem) {
        int index = findCartItemIndex(cartItem);
        if (index == -1) {
            items.add(cartItem);
        } else {
            items.get(index).increaseQuantity(cartItem.getQuantity());
        }
    }

    public void deleteItem(CartItem cartItem) {
        int index = findCartItemIndex(cartItem);
        items.remove(index);
    }

    public void clear() {
        items.clear();
    }

    private int findCartItemIndex(CartItem cartItem) {
        for (int i = 0; i < items.size(); i++)
            if(items.get(i).equals(cartItem)){
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