package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.StoreAdapter;
import com.coffeehouse.the.databinding.StoreLocationFragmentBinding;
import com.coffeehouse.the.viewModels.StoreViewModel;

public class StoresFragment extends Fragment {

    private StoreViewModel storeViewModel;
    private StoreAdapter storeAdapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        StoreLocationFragmentBinding storeLocationFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.store_location_fragment, container, false);

        //BINDING
        storeViewModel = new ViewModelProvider(this).get(StoreViewModel.class);
        RecyclerView recyclerView = storeLocationFragmentBinding.storeRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        storeAdapter = new StoreAdapter();
        recyclerView.setAdapter(storeAdapter);

        getStores(storeAdapter);
        //END BINDING

        storeAdapter.setClickListener(item -> {
            StoreDetailBottomSheet bottomSheet = new StoreDetailBottomSheet();
            bottomSheet.setStoreChosen(item);
            bottomSheet.show(getFragmentManager(), "Store Detail");
        });

        //Search
        SearchView searchView = storeLocationFragmentBinding.textSearch;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterStores(query, storeAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterStores(newText, storeAdapter);
                return true;
            }
        });

        return storeLocationFragmentBinding.getRoot();
    }

    private void getStores(StoreAdapter storeAdapter) {
        storeViewModel.getStores().observe(getViewLifecycleOwner(), storeAdapter::setStoresList);
    }

    private void filterStores(String s, StoreAdapter storeAdapter) {
        storeViewModel.filterStores(s).observe(getViewLifecycleOwner(), storeAdapter::setStoresList);
    }
}
