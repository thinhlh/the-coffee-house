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
import com.coffeehouse.the.adapter.ProductAdapter;
import com.coffeehouse.the.databinding.OrderFragmentBinding;
import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.models.UserAddress;
import com.coffeehouse.the.viewModels.OrderViewModel;

public class OrderFragment extends Fragment implements CategoryBottomSheet.SendCategoryPick, ProductDetailBottomSheet.UpdateCart, OrderDetailFragment.deleteCart, FavouriteProductListFragment.onFavouriteProductClick {

    private OrderViewModel orderViewModel;
    private ProductAdapter productsAdapter = new ProductAdapter();
    private OrderFragmentBinding orderFragmentBinding;
    private Cart cart = new Cart();
    private UserAddress userAddress = new UserAddress();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        orderFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.order_fragment, container, false);
        View v = orderFragmentBinding.getRoot();

        //BINDING
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        RecyclerView recyclerView = orderFragmentBinding.productsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        productsAdapter = new ProductAdapter();
        recyclerView.setAdapter(productsAdapter);

        getProducts(productsAdapter);
        //END BINDING

        productsAdapter.setClickListener(product -> {
            navigateToProductDetailBottomSheet(product);
        });

        //Inflate Menu Bottom Sheet
        orderFragmentBinding.menuSelectionCardView.setOnClickListener(view -> {
            CategoryBottomSheet categoryBottomSheet = new CategoryBottomSheet();
            categoryBottomSheet.setTargetFragment(OrderFragment.this, 2);
            categoryBottomSheet.show(getFragmentManager(), "Category");
        });

        //Inflate Favorite Product Fragment
        orderFragmentBinding.favoriteProductsIcon.setOnClickListener(view -> {
            FavouriteProductListFragment fragment = new FavouriteProductListFragment();
            fragment.setCart(cart);
            fragment.setTargetFragment(OrderFragment.this, 17);
            getFragmentManager().beginTransaction().replace(this.getId(), fragment).addToBackStack(null).commit();
        });

        //Inflate Order Detail Fragment
        orderFragmentBinding.orderView.setOnClickListener(view -> {
            OrderDetailFragment fragment = new OrderDetailFragment();
            fragment.setCartOrderView(cart);
            fragment.setAddress(userAddress);
            fragment.setTargetFragment(OrderFragment.this, 19);
            getFragmentManager().beginTransaction().replace(this.getId(), fragment).addToBackStack(null).commit();
        });

        //SEARCH PRODUCT REGION
        SearchView searchView = (SearchView) orderFragmentBinding.searchViewProduct;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productsAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                productsAdapter.filter(query);
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                orderFragmentBinding.txtMenu.setText("Thực đơn");
                return false;
            }
        });

        return v;
    }

    private void searchProduct(String s) {
        orderViewModel.getFilterProduct(s).observe(getViewLifecycleOwner(), productsAdapter::setItems);
    }

    private void getProducts(ProductAdapter productAdapter) {
        orderViewModel.getProducts().observe(getViewLifecycleOwner(), productAdapter::setItems);
    }

    public void navigateToProductDetailBottomSheet(Product product) {
        ProductDetailBottomSheet bottomSheet = new ProductDetailBottomSheet();
        bottomSheet.setTargetFragment(OrderFragment.this, 1);
        bottomSheet.setProductChosen(product);
        bottomSheet.show(getFragmentManager(), "Product Detail");
    }

    @Override
    public void onInputCategory(Category category) {
        orderViewModel.getProductsOfCategory(category.getId()).observe(getViewLifecycleOwner(), productsAdapter::setItems);
        orderFragmentBinding.txtMenu.setText(category.getTitle());
    }

    @Override
    public void onUpdateCart(CartItem cartItem) {
        cart.addItem(cartItem);
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public void onDeleteCart() {
        cart = new Cart();
    }

    @Override
    public void onProductClick(Product product) {
        navigateToProductDetailBottomSheet(product);
    }
}
