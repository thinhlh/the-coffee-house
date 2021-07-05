package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.StoreListItemBinding;
import com.coffeehouse.the.models.Store;
import com.coffeehouse.the.utils.helper.ClickableRecyclerView;
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StoreAdapter extends Adapter<StoreAdapter.StoreViewHolder> implements ClickableRecyclerView<Store> {
    protected List<Store> stores = new ArrayList<>();
    private RecyclerViewClickListener<Store> listener;

    @NonNull
    @NotNull
    @Override
    public StoreAdapter.StoreViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        StoreListItemBinding storeListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.store_list_item, parent, false);
        return new StoreViewHolder(storeListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StoreAdapter.StoreViewHolder holder, int position) {
        Store currentStore = stores.get(position);
        holder.storeListItemBinding.setStore(currentStore);
        if (!currentStore.getImageUrl().isEmpty())
            Picasso.get().load(currentStore.getImageUrl()).into(holder.storeListItemBinding.storeImage);
        holder.bindOnClick(currentStore, listener);
    }

    public void setStoresList(List<Store> stores) {
        this.stores = stores;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return stores != null ? stores.size() : 0;
    }

    @Override
    public void setItems(List<Store> items) {
        this.stores = items;
        notifyDataSetChanged();
    }

    @Override
    public List<Store> getItems() {
        return this.stores;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<Store> listener) {
        this.listener = listener;
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {
        private final StoreListItemBinding storeListItemBinding;

        public StoreViewHolder(@NonNull StoreListItemBinding storeListItemBinding) {
            super(storeListItemBinding.getRoot());
            this.storeListItemBinding = storeListItemBinding;
        }

        public void bindOnClick(Store store, RecyclerViewClickListener<Store> clickListener) {
            storeListItemBinding.setStore(store);
            storeListItemBinding.executePendingBindings();
            itemView.setOnClickListener(view -> {
                if (clickListener != null)
                    clickListener.onClick(store);
            });
        }
    }
}
