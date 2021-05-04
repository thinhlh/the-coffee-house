package com.coffeehouse.the.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.airbnb.lottie.L;
import com.coffeehouse.the.BR;
import com.coffeehouse.the.models.Membership;
import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.services.FetchNotifications;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Flushable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationViewModel extends ViewModel {
    private MutableLiveData<String> id;
    private MutableLiveData<Date> dateTime;
    private MutableLiveData<String> description;
    private MutableLiveData<String> imageUrl;
    private MutableLiveData<List<String>> targetCustomer;
    private MutableLiveData<String> title;

    private MutableLiveData<List<Notification>> listMutableLiveData;
    private List<Notification> notificationViewModelList;

    public List<Notification> getNotificationViewModelList() {
        return notificationViewModelList;
    }

    public void setNotificationViewModelList(List<Notification> notificationViewModelList) {
        this.notificationViewModelList = notificationViewModelList;
    }

    public MutableLiveData<List<Notification>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void setListMutableLiveData(MutableLiveData<List<Notification>> listMutableLiveData) {
        this.listMutableLiveData = listMutableLiveData;
    }

    public NotificationViewModel() {
    }

    public NotificationViewModel(MutableLiveData<String> id, MutableLiveData<Date> dateTime, MutableLiveData<String> description, MutableLiveData<String> imageUrl, MutableLiveData<List<String>> targetCustomer, MutableLiveData<String> title) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.imageUrl = imageUrl;
        this.targetCustomer = targetCustomer;
        this.title = title;
    }

    public MutableLiveData<String> getId() {
        return id;
    }

    public void setId(MutableLiveData<String> id) {
        this.id = id;
    }

    public MutableLiveData<Date> getDateTime() {
        return dateTime;
    }

    public void setDateTime(MutableLiveData<Date> dateTime) {
        this.dateTime = dateTime;
    }

    public MutableLiveData<String> getDescription() {
        return description;
    }

    public void setDescription(MutableLiveData<String> description) {
        this.description = description;
    }

    public MutableLiveData<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MutableLiveData<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MutableLiveData<List<String>> getTargetCustomer() {
        return targetCustomer;
    }

    public void setTargetCustomer(MutableLiveData<List<String>> targetCustomer) {
        this.targetCustomer = targetCustomer;
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public void setTitle(MutableLiveData<String> title) {
        this.title = title;
    }

    public LiveData<List<Notification>> getNotificationList() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }
//        notificationViewModelList = new ArrayList<NotificationViewModel>();
//        fetchNotification(notificationViewModelList);
//        listMutableLiveData.setValue(notificationViewModelList);
        return listMutableLiveData;
    }


    public void fetchNotification(List<Notification> notificationViewModelList) {
        FetchNotifications fetchNotifications = new FetchNotifications();
        fetchNotifications.fetchNotifications()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("tag", "Get data success");
                        List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                            Log.d("Check", "Notification:" + documentSnapshot.getData().toString());
                            Notification notification = documentSnapshot.toObject(Notification.class);
                            notification.setId(documentSnapshot.getId());
                            notificationViewModelList.add(notification);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("tag", "Get data Fail");
                    }
                });


    }
}
