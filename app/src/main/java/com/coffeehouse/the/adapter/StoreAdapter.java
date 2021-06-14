package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.StoreListItemBinding;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.models.Store;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StoreAdapter extends Adapter<StoreAdapter.StoreViewHolder> {
    private List<Store> stores;
    private StoreClickListener listener;

    public void setListener(StoreClickListener listener) {
        this.listener = listener;
    }

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
        Picasso.get().load(currentStore.getImageUrls().get(0)).into((ImageView) holder.itemView.findViewById(R.id.store_image));
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


    static class StoreViewHolder extends RecyclerView.ViewHolder {
        private final StoreListItemBinding storeListItemBinding;

        public StoreViewHolder(@NonNull StoreListItemBinding storeListItemBinding) {
            super(storeListItemBinding.getRoot());
            this.storeListItemBinding = storeListItemBinding;
        }

        public void bindOnClick(Store store, StoreClickListener clickListener) {
            storeListItemBinding.setStore(store);
            storeListItemBinding.executePendingBindings();
            itemView.setOnClickListener(view -> {
                if (clickListener != null)
                    clickListener.onStoreClick(store);
            });
        }
    }
}