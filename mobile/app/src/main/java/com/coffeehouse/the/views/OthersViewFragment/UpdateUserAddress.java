package com.coffeehouse.the.views.OthersViewFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.FixSavedaddressBinding;
import com.coffeehouse.the.models.UserAddress;
import com.coffeehouse.the.services.repositories.AddressRepo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class UpdateUserAddress extends AppCompatActivity implements View.OnClickListener {
    private FixSavedaddressBinding fixSavedaddressBinding;
    private EditText txtTitle, txtDescription, txtDetail, txtGate, txtNote, txtRecipientName, txtRecipientPhone;
    private final AddressRepo addressRepo = new AddressRepo();
    private UserAddress userAddress = new UserAddress();
    private boolean flag = true;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle
                                    savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        super.onCreate(savedInstanceState);
        fixSavedaddressBinding = DataBindingUtil.setContentView(this, R.layout.fix_savedaddress);

        Intent intent = getIntent();
        userAddress = (UserAddress) intent.getSerializableExtra("UserAddress");
        flag = intent.getBooleanExtra("Flag", true);

        init();
        setVariable();

        fixSavedaddressBinding.deleteAddress.setOnClickListener(this::onClick);
        fixSavedaddressBinding.updateAddress.setOnClickListener(this::onClick);
        fixSavedaddressBinding.backButton.setOnClickListener(this::onClick);


    }


    private void setVariable() {
        txtTitle.setText(userAddress.getTitle());
        txtDescription.setText(userAddress.getDescription());
        txtDetail.setText(userAddress.getDetail());
        txtGate.setText(userAddress.getGate());
        txtNote.setText(userAddress.getNote());
        txtRecipientName.setText(userAddress.getRecipientName());
        txtRecipientPhone.setText(userAddress.getRecipientPhone());
    }

    private void init() {
        txtTitle = fixSavedaddressBinding.addnewadressEdittext;
        txtDescription = fixSavedaddressBinding.textAddressDescription;
        txtDetail = fixSavedaddressBinding.textAddressDetail;
        txtGate = fixSavedaddressBinding.textAddressGate;
        txtNote = fixSavedaddressBinding.textAddressNote;
        txtRecipientName = fixSavedaddressBinding.textName;
        txtRecipientPhone = fixSavedaddressBinding.textPhone;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_address:
                if (userAddress.getId() == "") {
                    Toast.makeText(getApplicationContext(), "Address haven't created", Toast.LENGTH_SHORT).show();
                } else {
                    addressRepo.deleteUserAddress(userAddress.getId());
                    Toast.makeText(getApplicationContext(), "Delete Address success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.update_address:
                if (validation()) {
                    UserAddress currentAddress = new UserAddress(FirebaseAuth.getInstance().getUid(), txtTitle.getText().toString(), txtDescription.getText().toString(), txtDetail.getText().toString(), txtGate.getText().toString(), txtNote.getText().toString(), txtRecipientName.getText().toString(), txtRecipientPhone.getText().toString());
                    if (flag) {
                        addressRepo.addUserAddress(currentAddress);
                        Toast.makeText(getApplicationContext(), "Add Address success", Toast.LENGTH_SHORT).show();
                    } else {
                        addressRepo.updateAddress(userAddress.getId(), currentAddress);
                        Toast.makeText(getApplicationContext(), "Update Address success", Toast.LENGTH_SHORT).show();
                    }

                    finish();
                }
                break;
            case R.id.back_button:
                finish();
                break;
        }
    }

    private boolean validation() {
        return (titleValidation() && desValidation() && nameValidation() && phoneValidation());
    }

    private boolean titleValidation() {
        String title = txtTitle.getText().toString();
        if (title.isEmpty()) {
            txtTitle.setError("Title Required");
            txtTitle.requestFocus();
            return false;
        }
        return true;
    }

    private boolean desValidation() {
        String des = txtDescription.getText().toString();
        if (des.isEmpty()) {
            txtDescription.setError("Destination Required");
            txtDescription.requestFocus();
            return false;
        }
        return true;
    }

    private boolean nameValidation() {
        String name = txtRecipientName.getText().toString();
        if (name.isEmpty()) {
            txtRecipientName.setError("Name Required");
            txtRecipientName.requestFocus();
            return false;
        }
        return true;
    }

    private boolean phoneValidation() {
        String phone = txtRecipientPhone.getText().toString();
        if (phone.isEmpty()) {
            txtRecipientPhone.setError("Phone Required");
            txtRecipientPhone.requestFocus();
            return false;
        }
        return true;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
