package com.coffeehouse.the.views;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.PromotionAdapter;
import com.coffeehouse.the.databinding.FragmentScreen1MembershipBinding;
import com.coffeehouse.the.databinding.MembershipFragmentBinding;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.coffeehouse.the.viewModels.PromotionViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;

public class screen1_membership extends Fragment {
    private FragmentScreen1MembershipBinding binding;
    private final PromotionAdapter promotionAdapter = new PromotionAdapter();
    private PromotionViewModel promotionViewModel;

    public screen1_membership() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_screen1__membership, container, false);
        binding.setUser(UserRepo.user);
//        binding.cardviewChangepromotion.setOnClickListener(this::onClick);

        //Binding
        promotionViewModel = new ViewModelProvider(this).get(PromotionViewModel.class);
        RecyclerView recyclerView = binding.promotionRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(promotionAdapter);
        getPromotions(promotionAdapter);
        //End binding

        promotionAdapter.setClickListener(item -> {
            PromotionDetailBottomSheet bottomSheet = new PromotionDetailBottomSheet();
            bottomSheet.setPromotion(item);
            bottomSheet.show(getFragmentManager(), "Promotion Detail");
        });

        createUserBarCode();
        binding.userId.setText(FirebaseAuth.getInstance().getUid());

        return binding.getRoot();
    }

    private void createUserBarCode() {
        try {
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            Writer codeWriter;
            codeWriter = new Code128Writer();
            BitMatrix byteMatrix = codeWriter.encode(FirebaseAuth.getInstance().getUid(), BarcodeFormat.CODE_128, 400, 220, hintMap);
            int width = byteMatrix.getWidth();
            int height = byteMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, byteMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
            binding.userBarcode.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.d("BARCODE", "Create bar code failed cause by" + e);
        }
    }


    private void getPromotions(PromotionAdapter promotionAdapter) {
        promotionViewModel.getPromotions().observe(getViewLifecycleOwner(), promotionAdapter::setItems);
    }

}
