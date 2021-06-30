package com.coffeehouse.the.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.ActivityProductDetailBinding;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.coffeehouse.the.viewModels.ProductDetailViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    private ActivityProductDetailBinding activityProductDetailBinding;
    private Product product = new Product();
    private ProductDetailViewModel productDetailViewModel;
    private Locale locale = new Locale("vi", "VN");
    NumberFormat format = NumberFormat.getCurrencyInstance(locale);

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
        activityProductDetailBinding = DataBindingUtil.inflate(inflater, R.layout.activity_product_detail, container, false);
        View v = activityProductDetailBinding.getRoot();

        //BINDING
        activityProductDetailBinding.setProductDetail(product);
        activityProductDetailBinding.itemPrice.setText(format.format(product.getPrice()));
        productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        productDetailViewModel.setAmountPerOrder(product.getPrice());
        activityProductDetailBinding.setLifecycleOwner(this);
        activityProductDetailBinding.setProductDetailViewModel(productDetailViewModel);
        //END BINDING


        //LOAD INFORMATION
        if (UserRepo.user.getFavoriteProducts().contains(product.getId())) {
            ((ToggleButton) v.findViewById(R.id.image_favorite)).setChecked(true);
        } else {
            ((ToggleButton) v.findViewById(R.id.image_favorite)).setChecked(false);
        }
        if (!product.getImageUrl().isEmpty())
            Picasso.get().load(product.getImageUrl()).into((ImageView) v.findViewById(R.id.detail_product_image));
        //DONE


        (v.findViewById(R.id.select_item_button)).setOnClickListener(this::onClick);
        (v.findViewById(R.id.sum_textview)).setOnClickListener(this::onClick);
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
    public void onClick(View v) {
        if (productDetailViewModel.count.getValue() > 0) {
            CartItem cartItem = new CartItem(product.getId(), productDetailViewModel.getAmountPerOrder(),
                    productDetailViewModel.count.getValue(), productDetailViewModel.getSize(), productDetailViewModel.getTopping(), activityProductDetailBinding.otherOption.getText().toString());
            listener.onUpdateCart(cartItem);
        }
        dismiss();
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
