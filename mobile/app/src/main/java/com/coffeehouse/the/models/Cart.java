package com.coffeehouse.the.models;

import com.coffeehouse.the.utils.commons.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Cart class to be used at client side, can be convert to Order object for further information
public class Cart implements Serializable {

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

    public void setItems(List<CartItem> items) {
        this.items = items;
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
        int total = items.stream().mapToInt(CartItem::getTotalCartItemValue).sum();
        return total;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public String getCurrency() {
        return Constants.currencyFormatter.format(getTotalCartValue());
    }

    public String getAmountQuantity() {
        return "Giao tận nơi " + amountQuantity().toString() + " món";
    }

    public List<String> getAllProductsId() {
        List<String> result = new ArrayList<>();

        items.forEach(item -> {
            if (!result.contains(item.getProductId()))
                result.add(item.getProductId());
        });

        return result;
    }

    public String getTotalCurrency() {
        return Constants.currencyFormatter.format(getTotalCartValue());
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}