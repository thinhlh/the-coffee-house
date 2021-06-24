package com.coffeehouse.the.viewModels.admin;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.AdminCustomUser;
import com.coffeehouse.the.services.repositories.UsersRepo;

import java.util.List;

public class AdminUsersViewModel extends ViewModel {

    private Context context;
    private UsersRepo repo;

    public AdminUsersViewModel() {

    }

    public void setContext(Context context) {
        this.context = context;
        repo=new UsersRepo(context);
    }

    public LiveData<List<AdminCustomUser>> getUsers() {
        return repo.getUsers();
    }

    public void deleteUser(String uid){
        repo.deleteUser(uid);
    }

}
