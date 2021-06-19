package com.coffeehouse.the.models;

import android.util.Size;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class CartItem {
    private String productId = "";
    private Integer itemPrice = 0;
    private Integer quantity = 0;
    private ProductSize size = ProductSize.Medium;
    private String note = "";
    private ProductTopping topping = ProductTopping.Off;
    private Integer totalCartItemValue;

    private Locale locale = new Locale("vi", "VN");
    private Format format = NumberFormat.getCurrencyInstance(locale);

    public CartItem() {
    }

    public CartItem(String productId, Integer itemPrice, Integer quantity, ProductSize size, ProductTopping topping, String note) {
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

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getQuantity() {
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

    public Integer getTotalCartItemValue() {
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
        return format.format(getTotalCartItemValue());
    }

    public void setTotalCartItemValue(Integer totalCartItemValue) {
        this.totalCartItemValue = totalCartItemValue;
    }

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return (productId.equals(cartItem.productId) &&
                size == cartItem.size && topping == cartItem.topping);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, size);
    }

}
