package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.services.UserRepo;

public class AdminOthersViewModel extends ViewModel {
    private final UserRepo repo=new UserRepo();

    public void signOut(){
        repo.signOut();
    }
}
