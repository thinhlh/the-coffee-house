package com.coffeehouse.the.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.utils.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
                                            } else {
                                                for (QueryDocumentSnapshot cartItemDoc : cartValue) {
                                                    if (cartItemDoc != null) {
                                                        cartItems.add(CartItem.fromMap(cartItemDoc.getData()));
                                                    }
                                                }
                                            }
                                            cart.setItems(cartItems);
                                        });











                                Map<String, Object> map = orderDoc.getData();
                                Log.d("Order", map.toString());
                                Order order = orderDoc.toObject(Order.class);
                                order.setId(orderDoc.getId());
                                order.setCart(cart);
                                if (order.getUserId().equals(FirebaseAuth.getInstance().getUid())) {

                                    orderDoc.getReference().collection("cart").get();
//                                    .continueWithTask(task -> task.addOnSuccessListener(value1 -> {
//                                        for (QueryDocumentSnapshot doc1 : value1) {
//                                            if (doc1 != null) {
//                                                CartItem currentCartItem = doc1.toObject(CartItem.class);
//                                                order.getCart().getItems().add(currentCartItem);
//                                                order.setTotal(order.getCart().getTotalCartValue());
//                                            }
//                                        }
//                                        order.setTotal(order.getTotal() + shipPrice);
//                                    }))
//                                    .continueWith(task1 -> {
//                                        orders.add(order);
//                                        return null;
//                                    });


//                                        new OnSuccessListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                        for (QueryDocumentSnapshot doc1 : queryDocumentSnapshots) {
//                                            if (doc1 != null) {
//                                                CartItem currentCartItem = doc1.toObject(CartItem.class);
//                                                order.getCart().getItems().add(currentCartItem);
//                                                order.setTotal(order.getCart().getTotalCartValue());
//                                            }
//                                        }
//                                        order.setTotal(order.getTotal() + shipPrice);
//                                    }
//                                });
//                            }).continueWith(task1 -> {
//                                orders.add(order);
//                                return null;
//                            });

                                            //.get
//                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                                                @Override
//                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                                    for (QueryDocumentSnapshot doc1 : queryDocumentSnapshots) {
//                                                        if (doc1 != null) {
//                                                            CartItem currentCartItem = doc1.toObject(CartItem.class);
//                                                            order.getCart().getItems().add(currentCartItem);
//                                                            order.setTotal(order.getCart().getTotalCartValue());
//                                                        }
//                                                    }
//                                                    order.setTotal(order.getTotal() + Constants.DELIVERY_LEVIED);
//                                                }
//                                            }).continueWith(task -> {
//                                        orders.add(order);
//                                        return null;
//                                    });

//                                    .continueWithTask(task -> {
//                                orders.add(order);
//                                return null;
//                            });

//                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                @Override
//                                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
//                                    if (error != null) {
//                                        Log.w("Sub collection Repo", error);
//                                    } else {
//                                        for (QueryDocumentSnapshot doc1 : value) {
//                                            if (doc1 != null) {
//                                                CartItem currentCartItem = doc1.toObject(CartItem.class);
//                                                order.getCart().getItems().add(currentCartItem);
//                                                order.setTotal(order.getCart().getTotalCartValue());
//                                            }
//                                        }
//                                        order.setTotal(order.getTotal() + shipPrice);
//                                    }
//                                }
//                            });

                                    //orders.add(order);
                                }
                            }
                        }
                        this.orders.setValue(orders);
                    }
                });
    }

    public LiveData<List<Order>> getOrder() {
        return orders;
    }

}