package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.services.UserRepo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateUserInforViewModel extends ViewModel {
    private MutableLiveData<String> _birthday;
    private MutableLiveData<String> _name;
    private MutableLiveData<String> _phone;
    private MutableLiveData<String> _email;

    public LiveData<String> birthday;
    public LiveData<String> name;
    public LiveData<String> phone;
    public LiveData<String> email;

    private Date birthdayDate;

    public UpdateUserInforViewModel() {
        _name = new MutableLiveData<>(UserRepo.user.getName());
        _phone = new MutableLiveData<>(UserRepo.user.getPhoneNumber());
        _email = new MutableLiveData<>(UserRepo.user.getEmail());
        birthdayDate = UserRepo.user.getBirthday();
        DateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        _birthday = new MutableLiveData<>(simpleDateFormat.format(birthdayDate));

        birthday = _birthday;
        name = _name;
        phone = _phone;
        email = _email;
    }

    public void onDatePickerClick() {
    }

    public void onUpdateBtnClick() {
    }
}
