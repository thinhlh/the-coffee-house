package com.coffeehouse.the.views;

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
import com.coffeehouse.the.adapter.ProductsClickListener;
import com.coffeehouse.the.databinding.OrderFragmentBinding;
import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.OrderRepo;
import com.coffeehouse.the.services.UserRepo;
import com.coffeehouse.the.viewModels.OrderViewModel;

public class OrderFragment extends Fragment implements CategoryBottomSheet.SendCategoryPick, ProductDetailBottomSheet.UpdateCart {

    private OrderViewModel orderViewModel;
    private ProductAdapter productsAdapter = new ProductAdapter();
    private Cart cart = new Cart();
    private Order order;
    private OrderRepo orderRepo = new OrderRepo();
    private UserRepo userRepo = new UserRepo();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        OrderFragmentBinding orderFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.order_fragment, container, false);
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

        productsAdapter.setListener(new ProductsClickListener() {
            @Override
            public void onItemClick(Product product) {
                //Toast.makeText(getContext(), "PRODUCT " + product.getTitle() + " CHECKED ",Toast.LENGTH_SHORT).show();
//                ProductDetailBottomSheet bottomSheet = new ProductDetailBottomSheet();
//                bottomSheet.setTargetFragment(OrderFragment.this, 1);
//                bottomSheet.setProductChosen(product);
//                bottomSheet.show(getFragmentManager(), "Product Detail");
                navigateToProductDetailBottomSheet(product);
            }
        });

        //INFLATE MENU
        (v.findViewById(R.id.menu_selection_card_view)).setOnClickListener(view -> {
            CategoryBottomSheet categoryBottomSheet = new CategoryBottomSheet();
            categoryBottomSheet.setTargetFragment(OrderFragment.this, 2);
            categoryBottomSheet.show(getFragmentManager(), "Category");
        });

        (v.findViewById(R.id.favorite_products_icon)).setOnClickListener(view -> {
            Fragment fragment = new FavouriteProductListFragment();
            getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
        });
        //DONE

        v.findViewById(R.id.order_view).setOnClickListener(view -> {
            order = new Order(cart);
            orderRepo.addOrderData(order);
            userRepo.updateUserPoint(cart.getTotalCartValue() / 1000);
        });

        return v;
    }

    private void getProducts(ProductAdapter productAdapter) {
        orderViewModel.getProducts().observe(getViewLifecycleOwner(), productAdapter::setProductsList);
    }

    public void navigateToProductDetailBottomSheet(Product product) {
        ProductDetailBottomSheet bottomSheet = new ProductDetailBottomSheet();
        bottomSheet.setTargetFragment(OrderFragment.this, 1);
        bottomSheet.setProductChosen(product);
        bottomSheet.show(getFragmentManager(), "Product Detail");
    }

    @Override
    public void onInputCategory(Category category) {
        orderViewModel.getProductsOfCategory(category.getId()).observe(getViewLifecycleOwner(), productsAdapter::setProductsList);
    }

    @Override
    public void onUpdateCart(CartItem cartItem) {
        cart.addItem(cartItem);
    }
}
