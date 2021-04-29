package com.coffeehouse.the.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class Fetching {

    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
}
