package com.coffeehouse.the.models;

import com.coffeehouse.the.utils.Constants;

import java.util.Map;
import java.util.Objects;

public class CartItem {
    private String productId = "";
    private int itemPrice = 0;
    private Integer quantity = 0;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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
        cartItem.itemPrice = Integer.parseInt(map.get("itemPrice").toString());
        cartItem.setNote(String.valueOf(map.get("note")));
        cartItem.setProductId(String.valueOf(map.get("productId")));
        cartItem.setQuantity(Integer.parseInt(map.get("quantity").toString()));
        cartItem.size = map.get("size").equals("Medium") ? ProductSize.Medium : ProductSize.Large;
        cartItem.topping = map.get("topping").equals("Off") ? ProductTopping.Off : ProductTopping.On;
        cartItem.totalCartItemValue = Integer.parseInt(map.get("totalCartItemValue").toString());

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
