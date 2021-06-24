package com.coffeehouse.the.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersRepo implements Fetching {
    private final MutableLiveData<List<Order>> orders = new MutableLiveData<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public OrdersRepo() {
        setUpRealTimeListener();
    }

    public void addOrderData(Order order) {
        //ORDER HASH MAP
        Map<String, Object> dataOrder = new HashMap<>();
        dataOrder.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        dataOrder.put("orderTime", order.getOrderTime());
        dataOrder.put("orderMethod", order.getOrderMethod());
        dataOrder.put("delivered", order.getDelivered());
        dataOrder.put("orderAddress", order.getOrderAddress());
        dataOrder.put("recipientName", order.getRecipientName());
        dataOrder.put("recipientPhone", order.getRecipientPhone());

        db.collection("orders").add(dataOrder).addOnCompleteListener(task -> {
            String id = task.getResult().getId();
            order.getCart().getItems().forEach(cartItem -> {
                db.collection("orders").document(id).collection("cart").document().set(cartItem);
            });
        });
    }

    @Override
    public void setUpRealTimeListener() {
        db.collection("orders")
                .orderBy("orderTime", Query.Direction.DESCENDING).addSnapshotListener((orderValue, orderError) -> {
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

                                            if (order.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                                                orders.add(order);
                                            this.orders.setValue(orders);
                                            Log.d("FINAL", orders.toString());
                                            Log.d("Orders", String.valueOf(orders.size()));
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

}