package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Product;

public class ProductDetailViewModel extends ViewModel {

    //private Product product = new Product();

    public ProductDetailViewModel() {
        countM = new MutableLiveData<>(0);
        sumM = new MutableLiveData<>("Thêm vào giỏ - 0 đ");
        count = countM;
        sum = sumM;
    }

    private MutableLiveData<Integer> countM;
    private MutableLiveData<String> sumM;

    public LiveData<Integer> count;
    public LiveData<String> sum;

    public void Add(Product product) {
        countM.setValue(countM.getValue() + 1);
        Integer amount = countM.getValue() * product.getPrice();
        sumM.setValue("Thêm vào giỏ - " + amount.toString() + " đ");
    }

    public void Minus(Product product) {
        if (countM.getValue() > 1) {
            countM.setValue(countM.getValue() - 1);
            Integer amount = countM.getValue() * product.getPrice();
            sumM.setValue("Thêm vào giỏ - " + amount.toString() + " đ");
        }
    }
}
