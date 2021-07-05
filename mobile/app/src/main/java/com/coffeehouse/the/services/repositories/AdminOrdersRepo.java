package com.coffeehouse.the.services.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.utils.helper.Fetching;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminOrdersRepo implements Fetching {
    private final MutableLiveData<List<Order>> orders = new MutableLiveData<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdminOrdersRepo() {
        setUpRealTimeListener();
    }

    @Override
    public void setUpRealTimeListener() {
        db.collection("orders")
                .orderBy("orderTime", Query.Direction.DESCENDING)
                .addSnapshotListener((orderValue, orderError) -> {
                            if (orderError != null) {
                                Log.w("Orders Repository", orderError);
                            } else {
                                List<Order> orders = new ArrayList<>();
                                for (QueryDocumentSnapshot orderDoc : orderValue) {
                                    if (orderDoc != null) {
                                        Cart cart = new Cart();
                                        orderDoc.getReference().collection("cart")
                                                .addSnapshotListener((cartValue, cartError) -> {
                                                    List<CartItem> cartItems = new ArrayList<>();
                                                    if (cartError != null) {
                                                        //TODO Handling error
                                                        Log.e("Cart Error", cartError.getMessage());
                                                    } else {
                                                        for (QueryDocumentSnapshot cartItemDoc : cartValue) {
                                                            if (cartItemDoc != null) {
                                                                cartItems.add(CartItem.fromMap(cartItemDoc.getData()));
                                                            }
                                                        }
                                                        cart.setItems(cartItems);
                                                    }

                                                    Order order = orderDoc.toObject(Order.class);
                                                    order.setId(orderDoc.getId());
                                                    order.setCart(cart);

                                                    // Co the log o day bat ky gt nao de xem ket qua
                                                    orders.add(order);

                                                    this.orders.setValue(orders);
                                                });
                                    }
                                }
                            }
                        }
                );
    }

    public LiveData<List<Order>> getOrder() {
        return orders;
    }

    public Task<Void> delivery(String orderId) {
        return db.collection("orders").document(orderId).update("delivered", true);
    }
}
