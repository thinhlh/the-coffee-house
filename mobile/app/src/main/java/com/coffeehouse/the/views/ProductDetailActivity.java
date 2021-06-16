package com.coffeehouse.the.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.ActivityProductDetailBinding;
import com.coffeehouse.the.viewModels.ProductDetailViewModel;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
    }
}