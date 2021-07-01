package com.coffeehouse.the.adapter;

import com.coffeehouse.the.models.Store;
import com.coffeehouse.the.utils.helper.Searchable;
import com.coffeehouse.the.utils.helper.SwipeAbleRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AdminStoreAdapter extends StoreAdapter implements SwipeAbleRecyclerView<Store>, Searchable {

    private final List<Store> storesCopy = new ArrayList<>();

    @Override
    public void setItems(List<Store> items) {
        super.setItems(items);
        this.storesCopy.clear();
        this.storesCopy.addAll(items);
    }

    @Override
    public void remove(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public void filter(String query) {
        super.stores.clear();
        if (query.isEmpty()) {
            stores.addAll(storesCopy);
        } else {
            query = query.toLowerCase();
            String regex = ".*" + query + ".*";

            for (Store store : storesCopy) {
                if (Pattern.matches(regex, store.getName().toLowerCase()) || Pattern.matches(regex, store.getAddress().toLowerCase())) {
                    stores.add(store);
                }
            }
        }
        notifyDataSetChanged();
    }
}
