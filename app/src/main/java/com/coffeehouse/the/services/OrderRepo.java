package com.coffeehouse.the.services;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class OrderRepo extends Fetching {
    private final MutableLiveData<List<Order>> data = new MutableLiveData<>();

    public OrderRepo() {
    }

    public void addOrderData(Order order) {
        //ORDER HASH MAP
        Map<String, Object> dataOrder = new HashMap<>();
        dataOrder.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        dataOrder.put("orderTime", order.getOrderTime());

        db.collection("orders").add(dataOrder).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                String id = task.getResult().getId();
                AtomicReference<Integer> i = new AtomicReference<>(1);
                order.getCart().getItems().forEach(cartItem -> {
                    db.collection("orders").document(id).collection("cart").document().set(cartItem);
                });
            }
        });
    }
}
