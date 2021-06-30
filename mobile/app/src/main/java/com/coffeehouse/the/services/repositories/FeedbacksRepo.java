package com.coffeehouse.the.services.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Feedback;
import com.coffeehouse.the.utils.helper.Fetching;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FeedbacksRepo implements Fetching {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<Feedback>> data = new MutableLiveData<>();

    public FeedbacksRepo() {

    }

    @Override
    public void setUpRealTimeListener() {
        db.collection("feedbacks").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Feedbacks Repo", error);
            } else {
                List<Feedback> list = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        Feedback feedback = new Feedback();
                        feedback = doc.toObject(Feedback.class);
                        list.add(feedback);
                    }
                }
                data.setValue(list);
            }
        });
    }

    public LiveData<List<Feedback>> getFeedbacks() {
        return data;
    }

    public Task<DocumentReference> addFeedbackData(Feedback feedback) {
        return db.collection("feedbacks").add(feedback);
    }
}
