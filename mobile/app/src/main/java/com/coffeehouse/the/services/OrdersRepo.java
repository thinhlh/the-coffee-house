package com.coffeehouse.the.services;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Order;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersRepo implements Fetching {
    private final MutableLiveData<List<Order>> data = new MutableLiveData<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final int shipPrice = 30000;

    public OrdersRepo() {
        setUpRealTimeListener();
    }

    public void addOrderData(Order order) {
        //ORDER HASH MAP
        Map<String, Object> dataOrder = new HashMap<>();
        dataOrder.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        dataOrder.put("orderTime", order.getOrderTime());

        db.collection("orders").add(dataOrder).addOnCompleteListener(task -> {
            String id = task.getResult().getId();
            order.getCart().getItems().forEach(cartItem -> {
                db.collection("orders").document(id).collection("cart").document().set(cartItem);
            });
        });
    }

    @Override
    public void setUpRealTimeListener() {
        db.collection("orders").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Orders Repo", error);
            } else {
                List<Order> orders = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        Order order = doc.toObject(Order.class);
                        order.setId(doc.getId());
                        if (order.getUserId().equals(FirebaseAuth.getInstance().getUid())) {
                            doc.getReference().collection("cart").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                                    if (error != null) {
                                        Log.w("Sub collection Repo", error);
                                    } else {
                                        for (QueryDocumentSnapshot doc1 : value) {
                                            if (doc1 != null) {
                                                CartItem currentCartItem = doc1.toObject(CartItem.class);
                                                order.getCart().getItems().add(currentCartItem);
                                                order.setTotal(order.getCart().getTotalCartValue());
                                            }
                                        }
                                        order.setTotal(order.getTotal() + shipPrice);
                                    }
                                }
                            });
                            orders.add(order);
                        }
                    }
                }
                data.setValue(orders);
            }
        });
    }

    public LiveData<List<Order>> getOrder() {
        return data;
    }

}
