package com.coffeehouse.the.views.OthersViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.coffeehouse.the.R;
import com.coffeehouse.the.views.StoresFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class ChangeAddressBottomsheet extends BottomSheetDialogFragment implements View.OnClickListener{
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottomsheet_changeaddress_orderdetail,container,false);
        ImageButton imageButton=v.findViewById(R.id.close_changeaddress);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initview(view);
    }

    private void Initview(View view) {
        Button fixbutton =view.findViewById(R.id.button_change_useraddress);
        fixbutton.setOnClickListener(this);
        Button changestorefragmentbutton =view.findViewById(R.id.button_choose_storelocation);
        changestorefragmentbutton.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_change_useraddress:
                fixaddressuser();
                dismiss();
                break;
            case R.id.button_choose_storelocation:
                choosestore();
                dismiss();
                break;
        }
    }

    private void fixaddressuser() {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_fragment_container,new SavedAddressFragment()).addToBackStack(null).commit();

    }
    private void choosestore() {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_fragment_container,new StoresFragment()).addToBackStack(null).commit();
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_store_location);

    }
}
