package com.coffeehouse.the.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.StoreLocationDetailBinding;
import com.coffeehouse.the.models.Store;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class StoreDetailBottomSheet extends BottomSheetDialogFragment {
    private final String contactPhoneNumber = "18006939";
    private Store store = new Store();

    public StoreDetailBottomSheet() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //INFLATE
        StoreLocationDetailBinding storeLocationDetailBinding = DataBindingUtil.inflate(inflater, R.layout.store_location_detail, container, false);
        View v = storeLocationDetailBinding.getRoot();

        //BINDING
        storeLocationDetailBinding.setStoreDetail(store);
        storeLocationDetailBinding.setLifecycleOwner(this);
        //END BINDING

        storeLocationDetailBinding.storeDetailButton.setOnClickListener(v1 -> {
            navigateToOrder();
            dismiss();
        });
        storeLocationDetailBinding.imgStoreDetailGgMap.setOnClickListener(v1 -> navigateToGoogleMap());
        storeLocationDetailBinding.storePhoneContact.setOnClickListener(listener -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contactPhoneNumber));
            startActivity(intent);
        });
        if (!store.getImageUrl().isEmpty())
            Picasso.get().load(store.getImageUrl()).into(storeLocationDetailBinding.image);

        return v;
    }

    private void navigateToOrder() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_fragment_container, new OrderFragment()).addToBackStack(null).commit();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_order);
    }

    private void navigateToGoogleMap() {
        try {
            // Create a Uri from an intent string. Use the result to create an Intent
            Uri gmmIntentUri = Uri.parse("https://www.google.co.in/maps/dir//" + store.getAddress());

            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mapIntent);

        } catch (ActivityNotFoundException e) {
            // When google map is not installed
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent ggPlayIntent = new Intent(Intent.ACTION_VIEW, uri);
            ggPlayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ggPlayIntent);
        }
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setStoreChosen(Store store) {
        this.store = store;
    }
}
