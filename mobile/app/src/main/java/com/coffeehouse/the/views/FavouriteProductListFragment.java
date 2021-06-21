package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.ProductAdapter;
import com.coffeehouse.the.adapter.RecyclerViewClickListener;
import com.coffeehouse.the.databinding.FavouriteFragmentBinding;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.viewModels.FavouriteProductViewModel;

import org.jetbrains.annotations.NotNull;

public class FavouriteProductListFragment extends Fragment implements View.OnClickListener {

    private FavouriteProductViewModel favouriteProductViewModel = new FavouriteProductViewModel();
    private ProductAdapter productsAdapter = new ProductAdapter();

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initview(view);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //INFLATE
        FavouriteFragmentBinding favouriteFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.favourite_fragment, container, false);
        View v = favouriteFragmentBinding.getRoot();

        //BINDING
        favouriteProductViewModel = new ViewModelProvider(this).get(FavouriteProductViewModel.class);
        RecyclerView recyclerView = favouriteFragmentBinding.productsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        productsAdapter = new ProductAdapter();
        recyclerView.setAdapter(productsAdapter);

        getFavProducts(productsAdapter);
        //END BINDING

        productsAdapter.setClickListener(item -> onFavProductClick(item));

        return v;
    }

    private void onFavProductClick(Product product) {
        OrderFragment fragment = new OrderFragment();
        getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
        fragment.navigateToProductDetailBottomSheet(product);
    }

    private void getFavProducts(ProductAdapter productsAdapter) {
        favouriteProductViewModel.getFavProducts().observe(getViewLifecycleOwner(), productsAdapter::setItems);
    }
    private void Initview(View view){
        ImageView imageView=view.findViewById(R.id.close_favorite_list_fragment);
        imageView.setOnClickListener(this);
    }

    private void Closelistfavoritefragment() {
        FragmentManager fragmentManager=requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_fragment_container,new OrderFragment()).addToBackStack(null).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_favorite_list_fragment:
                Closelistfavoritefragment();
        }
    }
}
