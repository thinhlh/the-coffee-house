package com.coffeehouse.the.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.ActivityProductDetailBinding;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.UserRepo;
import com.coffeehouse.the.viewModels.ProductDetailViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ProductDetailBottomSheet extends BottomSheetDialogFragment {

    private Product product = new Product();
    private ProductDetailViewModel productDetailViewModel;
    //private CartItem cartItem = new CartItem();

    public ProductDetailBottomSheet() {
    }

    //SEND CART DATA TO ORDER FRAGMENT
    public interface UpdateCart {
        void onUpdateCart(CartItem cartItem);
    }

    private UpdateCart listener;

    public void setListener(UpdateCart listener) {
        this.listener = listener;
    }
    //DONE SETUP


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //INFLATE
        ActivityProductDetailBinding activityProductDetailBinding = DataBindingUtil.inflate(inflater, R.layout.activity_product_detail, container, false);
        View v = activityProductDetailBinding.getRoot();

        //BINDING
        activityProductDetailBinding.setProductDetail(product);
        productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        activityProductDetailBinding.setLifecycleOwner(this);
        activityProductDetailBinding.setProductDetailViewModel(productDetailViewModel);
        //END BINDING


        //LOAD INFORMATION
        if (UserRepo.user.getFavoriteProducts().contains(product.getId())) {
            ((ToggleButton) v.findViewById(R.id.image_favorite)).setChecked(true);
        } else {
            ((ToggleButton) v.findViewById(R.id.image_favorite)).setChecked(false);
        }
        Picasso.get().load(product.getImageUrl()).into((ImageView) v.findViewById(R.id.detail_product_image));
        //DONE


        ((TextView) v.findViewById(R.id.sum_textview)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productDetailViewModel.count.getValue() > 0) {
                    //(productId, itemPrice, quantity, size, topping, note)
                    CartItem cartItem = new CartItem(product.getId(), productDetailViewModel.getAmountPerOrder(),
                            productDetailViewModel.count.getValue(), productDetailViewModel.getSize(), productDetailViewModel.getTopping(), "test note");

                    //cartItem.getItemPrice();
                    listener.onUpdateCart(cartItem);
                }
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setProductChosen(Product product) {
        this.product = product;
    }


    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateCart) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UpdateCart listener");
        }
    }

}
