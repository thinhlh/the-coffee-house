package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Cart;

public class OrderDetailViewModel extends ViewModel {
    private Cart cart = new Cart();

    public OrderDetailViewModel() {
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public LiveData<Cart> getCartLiveData() {
        MutableLiveData<Cart> _cart = new MutableLiveData<>();
        _cart.setValue(cart);
        return _cart;
    }
}
