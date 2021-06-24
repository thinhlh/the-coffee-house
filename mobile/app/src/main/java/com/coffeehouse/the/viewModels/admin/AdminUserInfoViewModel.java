package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.services.repositories.AdminUserRepo;
import com.google.android.gms.tasks.Task;

public class AdminUserInfoViewModel extends ViewModel {

    private final AdminUserRepo repo = new AdminUserRepo();

    public Task<Void> promoteToAdmin(String uid) {
        return repo.promoteToAdmin(uid);
    }

    public Task<Void> demoteToMember(String uid) {
        return repo.demoteToMember(uid);
    }
}
