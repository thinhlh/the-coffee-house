package com.coffeehouse.the.viewModels.admin;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coffeehouse.the.models.AdminCustomUser;
import com.coffeehouse.the.services.remote.UserInfoApi;
import com.coffeehouse.the.services.repositories.AdminUserRepo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserInfo;

import org.json.JSONObject;

public class AdminUserInfoViewModel extends ViewModel {

    private final AdminUserRepo repo=new AdminUserRepo();
    private UserInfoApi userInfoApi;

    private Context context;

    public void setContext(Context context) {
        this.context = context;
        userInfoApi=new UserInfoApi(context);
    }

    public Task<Void> promoteToAdmin(String uid) {
        return repo.promoteToAdmin(uid);
    }

    public Task<Void> demoteToMember(String uid) {
        return repo.demoteToMember(uid);
    }

    public void getUserInfo(String id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
         userInfoApi.getUserInfo(id,listener,errorListener);
    }
}
