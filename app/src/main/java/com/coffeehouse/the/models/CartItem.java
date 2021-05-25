package com.coffeehouse.the.models;

import java.util.Objects;

public class CartItem {
    private String productId = "";
    private int itemPrice = 0;
    private int quantity = 0;
    private ProductSize size = ProductSize.Medium;
    private String note = "";

    public CartItem() {
    }

    public CartItem(String productId, int itemPrice, int quantity, ProductSize size, String note) {
        this.productId = productId;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.size = size;
        this.note = note;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductSize getSize() {
        return size;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void increaseQuantity(int increaseValue) {
        this.quantity += increaseValue;
    }

    public int getTotalCartItemValue() {
        return itemPrice * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return productId.equals(cartItem.productId) &&
                size == cartItem.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, size);
    }
}
