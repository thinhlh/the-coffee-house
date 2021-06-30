package com.coffeehouse.the.models;

import com.coffeehouse.the.utils.commons.Constants;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class CartItem implements Serializable {
    private String productId = "";
    private int itemPrice = 0;
    private int quantity = 0;
    private ProductSize size = ProductSize.Medium;
    private String note = "";
    private ProductTopping topping = ProductTopping.Off;
    private int totalCartItemValue = 0;

    public CartItem() {
    }

    public CartItem(String productId, int itemPrice, int quantity, ProductSize size, ProductTopping topping, String note) {
        this.productId = productId;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.size = size;
        this.topping = topping;
        this.note = note;
    }

    public ProductTopping getTopping() {
        return topping;
    }

    public void setTopping(ProductTopping topping) {
        this.topping = topping;
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

    public String options() {
        String _size = "Vừa";
        if (size == ProductSize.Large)
            _size = "Lớn";
        if (topping == ProductTopping.On)
            return _size + ", Có Topping";
        return _size;
    }

    public String totalCartItemPrice() {
        return Constants.currencyFormatter.format(getTotalCartItemValue());
    }

    public void setTotalCartItemValue(int totalCartItemValue) {
        this.totalCartItemValue = totalCartItemValue;
    }

    public String getToppingString() {
        return topping == ProductTopping.On ? "ADDED" : "NONE";
    }

    public String getSizeString() {
        return size == ProductSize.Large ? "Large" : "Medium";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return productId.equals(cartItem.productId) &&
                size == cartItem.size &&
                topping == cartItem.topping;
    }

    public static CartItem fromMap(Map<String, Object> map) {
        CartItem cartItem = new CartItem();
        if (map.get("itemPrice") != null)
            cartItem.itemPrice = Integer.parseInt(Objects.requireNonNull(map.get("itemPrice")).toString());
        if (map.get("note") != null)
            cartItem.setNote(String.valueOf(map.get("note")));
        if (map.get("productId") != null)
            cartItem.setProductId(String.valueOf(map.get("productId")));
        if (map.get("quantity") != null)
            cartItem.setQuantity(Integer.parseInt(Objects.requireNonNull(map.get("quantity")).toString()));
        if (map.get("size") != null)
            cartItem.size = Objects.equals(map.get("size"), "Medium") ? ProductSize.Medium : ProductSize.Large;
        if (map.get("topping") != null)
            cartItem.topping = Objects.equals(map.get("topping"), "Off") ? ProductTopping.Off : ProductTopping.On;
        if (map.get("totalCartItemValue") != null)
            cartItem.totalCartItemValue = Integer.parseInt(Objects.requireNonNull(map.get("totalCartItemValue")).toString());

        return cartItem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, size, topping);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "productId='" + productId + '\'' +
                ", itemPrice=" + itemPrice +
                ", quantity=" + quantity +
                ", size=" + size +
                ", note='" + note + '\'' +
                ", topping=" + topping +
                ", totalCartItemValue=" + totalCartItemValue +
                '}';
    }
}
