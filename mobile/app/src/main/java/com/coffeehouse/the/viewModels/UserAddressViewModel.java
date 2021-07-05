package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.UserAddress;
import com.coffeehouse.the.services.repositories.AddressRepo;

import java.util.List;

public class UserAddressViewModel extends ViewModel {
    private final AddressRepo addressRepo;

    public UserAddressViewModel() {
        addressRepo = new AddressRepo();
    }

    public LiveData<List<UserAddress>> getUserAddress() {
        return addressRepo.getUserAddress();
    }
}
