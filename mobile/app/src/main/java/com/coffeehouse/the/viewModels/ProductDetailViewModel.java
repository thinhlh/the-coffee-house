package com.coffeehouse.the.viewModels;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.models.ProductSize;
import com.coffeehouse.the.models.ProductTopping;
import com.coffeehouse.the.services.repositories.CategoriesRepo;
import com.coffeehouse.the.services.repositories.UserRepo;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailViewModel extends ViewModel {

    private MutableLiveData<Integer> countM;
    private MutableLiveData<String> sumM;
    private MutableLiveData<Boolean> toppingCheckM;

    public LiveData<Integer> count;
    public LiveData<String> sum;
    public LiveData<Boolean> toppingCheck;

    private Integer amount;
    private Integer amountPerOrder;
    private final Integer toppingPrice = 10000;
    private Integer sizePrice = 0;
    private final Integer upSize = 5000;
    private CategoriesRepo categoriesRepo;
    private Locale locale = new Locale("vi", "VN");
    NumberFormat format = NumberFormat.getCurrencyInstance(locale);

    public ProductDetailViewModel() {
        countM = new MutableLiveData<>(1);
        toppingCheckM = new MutableLiveData<>(false);
        sumM = new MutableLiveData<>("Thêm vào giỏ");
        amount = amountPerOrder;
        count = countM;
        sum = sumM;
        toppingCheck = toppingCheckM;
        categoriesRepo = new CategoriesRepo();
    }

    public void Add(Product product) {
        countM.setValue(countM.getValue() + 1);
        updateBill(product);
    }

    public void Minus(Product product) {
        if (countM.getValue() > 1) {
            countM.setValue(countM.getValue() - 1);
            updateBill(product);
        }
    }

    public void toppingOnCheck(Product product) {
        if (countM.getValue() == 0 || checkProduct(product) == false) {
            toppingCheckM.setValue(false);
        } else {
            toppingCheckM.setValue(!toppingCheckM.getValue());
            updateBill(product);
        }
    }

    private void updateBill(Product product) {
        if (toppingCheckM.getValue()) {
            amountPerOrder = sizePrice + toppingPrice + product.getPrice();
        } else {
            amountPerOrder = sizePrice + product.getPrice();
        }
        amount = countM.getValue() * amountPerOrder;
        sumM.setValue("Thêm vào giỏ - " + format.format(amount));
    }

    private boolean checkProduct(Product product) {
        for (Category category1 : categoriesRepo.getCategories().getValue()) {
            if (product.getCategoryId().equals(category1.getId())) {
                if (category1.getTitle().equals("Bộ Sưu Tập Quà Tặng"))
                    return false;
                continue;
            }
        }
        return true;
    }

    public void sizeSelect(View v, RadioGroup radioGroup, Product product) {
        int radioID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = v.findViewById(radioID);
        if (radioButton.getText().equals("Vừa")) {
            sizePrice = 0;
            size = ProductSize.Medium;
            updateBill(product);
        } else if (radioButton.getText().equals("Lớn")) {
            if (checkProduct(product)) {
                sizePrice = upSize;
                size = ProductSize.Large;
                updateBill(product);
            }
        }
    }

    public void onFavToggleButton(Product product) {
        UserRepo user = new UserRepo();
        user.toggleFavorite(product.getId()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

            }
        });
    }

    //GETTER FOR CART INFORMATION
    private ProductSize size = ProductSize.Medium;

    public ProductTopping getTopping() {
        if (toppingCheckM.getValue()) {
            return ProductTopping.On;
        }
        return ProductTopping.Off;
    }

    public ProductSize getSize() {
        return size;
    }

    public Integer getAmountPerOrder() {
        return amountPerOrder;
    }

    public void setAmountPerOrder(Integer amountPerOrder) {
        this.amountPerOrder = amountPerOrder;
    }
}
