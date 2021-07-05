package com.coffeehouse.the.services.repositories;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coffeehouse.the.models.AdminCustomUser;
import com.coffeehouse.the.utils.helper.Fetching;
import com.coffeehouse.the.utils.commons.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * This class is used for fetching users for admin screen*/
public class UsersRepo implements Fetching {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final MutableLiveData<List<AdminCustomUser>> users = new MutableLiveData<>();

    private RequestQueue requestQueue;

    private Context context;

    public UsersRepo(Context context) {
        this.context = context;
        setUpRealTimeListener();
    }

    public LiveData<List<AdminCustomUser>> getUsers() {
        return users;
    }

    @Override
    public void setUpRealTimeListener() {

        //TODO get user info from the remote server
        db.collection("users").whereNotEqualTo(FieldPath.documentId(), FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Users Repository", error);
            } else {
                List<AdminCustomUser> currentUsers = new ArrayList<>();

                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        Map<String, Object> map = doc.getData();
                        map.put("id", doc.getId());
                        AdminCustomUser user = AdminCustomUser.fromMap(map);
                        getUserInfo(user);
                        currentUsers.add(user);
                    }
                }
                users.setValue(currentUsers);
            }
        });
    }

    private void reloadUsersInfo() {
        if (users.getValue().isEmpty()) {
            return;
        }
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }

        users.getValue().forEach(this::fetchUserSignInData);
    }

    private void getUserInfo(AdminCustomUser user) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        fetchUserSignInData(user);
    }

    private void fetchUserSignInData(AdminCustomUser user) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Constants.SERVER_ENDPOINT + "user-info/" + user.getId(),
                null,
                response -> {
                    try {
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.RFC_1123_DATE_TIME;
                        LocalDateTime localDateTime = LocalDateTime.parse((String) response.getJSONObject("metadata").get("lastSignInTime"), dateTimeFormatter);
                        Date lastSignIn = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                        user.setLastSignedIn(lastSignIn);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            //TODO handling error
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return Constants.BASE_HEADERS;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void deleteUser(String uid) {
        StringRequest deleteRequest = new StringRequest(
                Request.Method.DELETE,
                Constants.SERVER_ENDPOINT + "delete-user/" + uid,
                response -> {
                    //TODO NOTIFY COMPLETED
                }, error -> {
            //TODO NOTIFY ERROR OCCURRED
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return Constants.BASE_HEADERS;
            }
        };
        requestQueue.add(deleteRequest);
    }
}
