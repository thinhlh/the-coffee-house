package com.coffeehouse.the.views;

import android.content.Intent;
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
import com.coffeehouse.the.databinding.OrderFragmentBinding;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.viewModels.OrderViewModel;

public class OrderFragment extends Fragment  {

    private OrderViewModel orderViewModel;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        OrderFragmentBinding orderFragmentBinding= DataBindingUtil.inflate(inflater,R.layout.order_fragment,container,false);
        View v=orderFragmentBinding.getRoot();

        //BINDING
        orderViewModel= new ViewModelProvider(this).get(OrderViewModel.class);
        RecyclerView recyclerView= orderFragmentBinding.productsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        ProductAdapter productsAdapter=new ProductAdapter();
        recyclerView.setAdapter(productsAdapter);



        getProducts(productsAdapter);


        return v;
    }

    private void getProducts(ProductAdapter productAdapter){
        orderViewModel.getProducts().observe(getViewLifecycleOwner(),productAdapter::setProductsList);
    }
}
