package com.coffeehouse.the.models;

import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//Cart class to be used at client side, can be convert to Order object for further information
public class Cart {
    private Locale locale = new Locale("vi", "VN");
    private Format format = NumberFormat.getCurrencyInstance(locale);

    public Cart() {
    }

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
            if (items.get(i).equals(cartItem)) {
                return i;
            }
        return -1;
    }

    private Integer amountQuantity() {
        Integer _amountQuantity = 0;
        for (int i = 0; i < items.size(); i++)
            _amountQuantity += items.get(i).getQuantity();
        return _amountQuantity;
    }

    public int getTotalCartValue() {
        int total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getTotalCartItemValue();
        }
        return total;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public String getCurrency() {
        return format.format(getTotalCartValue());
    }

    public String getAmountQuantity() {
        return "Giao tận nơi " + amountQuantity().toString() + " món";
    }

    public String getTotalCurrency() {
        return format.format(getTotalCartValue());
    }
}