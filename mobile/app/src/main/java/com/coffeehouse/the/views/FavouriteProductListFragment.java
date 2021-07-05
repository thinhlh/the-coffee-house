package com.coffeehouse.the.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.ProductAdapter;
import com.coffeehouse.the.databinding.FavouriteFragmentBinding;
import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.viewModels.FavouriteProductViewModel;

import org.jetbrains.annotations.NotNull;

public class FavouriteProductListFragment extends Fragment {

    private FavouriteProductViewModel favouriteProductViewModel = new FavouriteProductViewModel();
    private ProductAdapter productsAdapter = new ProductAdapter();
    private Cart cart = new Cart();

    public interface onFavouriteProductClick {
        void onProductClick(Product product);
    }

    private onFavouriteProductClick listener;

    public void setListener(onFavouriteProductClick listener) {
        this.listener = listener;
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

        favouriteFragmentBinding.closeFavoriteListFragment.setOnClickListener(listener -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        productsAdapter.setClickListener(this::onFavProductClick);

        return v;
    }

    private void onFavProductClick(Product product) {
        listener.onProductClick(product);
        assert getFragmentManager() != null;
        getFragmentManager().popBackStack();
    }

    private void getFavProducts(ProductAdapter productsAdapter) {
        favouriteProductViewModel.getFavProducts().observe(getViewLifecycleOwner(), productsAdapter::setItems);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (onFavouriteProductClick) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onFavoriteProduct listener");
        }
    }
}
