package com.coffeehouse.the.adapter;

import com.coffeehouse.the.models.Store;
import com.coffeehouse.the.utils.helper.SwipeAbleRecyclerView;

public class AdminStoreAdapter extends StoreAdapter implements SwipeAbleRecyclerView<Store> {
    @Override
    public void remove(int position) {
        notifyItemRemoved(position);
    }
}
