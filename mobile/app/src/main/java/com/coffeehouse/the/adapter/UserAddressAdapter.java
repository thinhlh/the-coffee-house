package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.SavedAddressListItemBinding;
import com.coffeehouse.the.models.UserAddress;
import com.coffeehouse.the.utils.helper.ClickableRecyclerView;
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserAddressAdapter extends Adapter<UserAddressAdapter.UserAddressViewHolder> implements ClickableRecyclerView<UserAddress> {
    private List<UserAddress> list = new ArrayList<>();
    private RecyclerViewClickListener<UserAddress> listener;

    @NonNull
    @NotNull
    @Override
    public UserAddressAdapter.UserAddressViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SavedAddressListItemBinding savedAddressListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.saved_address_list_item, parent, false);
        return new UserAddressViewHolder(savedAddressListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserAddressAdapter.UserAddressViewHolder holder, int position) {
        UserAddress currentUserAddress = list.get(position);
        holder.savedAddressListItemBinding.setUserAddress(currentUserAddress);
        holder.bindOnClick(currentUserAddress, listener);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public void setItems(List<UserAddress> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    @Override
    public List<UserAddress> getItems() {
        return this.list;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<UserAddress> listener) {
        this.listener = listener;
    }

    static class UserAddressViewHolder extends RecyclerView.ViewHolder {
        private final SavedAddressListItemBinding savedAddressListItemBinding;

        public UserAddressViewHolder(@NonNull @NotNull SavedAddressListItemBinding savedAddressListItemBinding) {
            super(savedAddressListItemBinding.getRoot());
            this.savedAddressListItemBinding = savedAddressListItemBinding;
        }

        public void bindOnClick(UserAddress userAddress, RecyclerViewClickListener<UserAddress> clickListener) {
            savedAddressListItemBinding.setUserAddress(userAddress);
            savedAddressListItemBinding.executePendingBindings();
            itemView.setOnClickListener(view -> {
                if (clickListener != null)
                    clickListener.onClick(userAddress);
            });
        }
    }
}
