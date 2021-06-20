package com.coffeehouse.the.viewModels.admin;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.services.UserRepo;
import com.google.android.gms.tasks.Task;

public class AdminOthersViewModel extends ViewModel {
    private final UserRepo repo=new UserRepo();

    public Task<Void> signOut(Context context){
        return repo.signOut(context);
    }
}
