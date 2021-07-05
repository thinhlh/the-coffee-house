package com.coffeehouse.the.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminUserCardBinding;
import com.coffeehouse.the.models.AdminCustomUser;
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;
import com.coffeehouse.the.utils.helper.Searchable;
import com.coffeehouse.the.utils.helper.SwipeAbleRecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements SwipeAbleRecyclerView<AdminCustomUser>, Searchable {

    private List<AdminCustomUser> users = new ArrayList<>();
    private RecyclerViewClickListener<AdminCustomUser> listener;
    private List<AdminCustomUser> usersCopy = new ArrayList<>();


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        AdminUserCardBinding adminUserCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.admin_user_card, parent, false);
        return new UserViewHolder(adminUserCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        AdminCustomUser currentUser = users.get(position);
        holder.adminUserCardBinding.setUser(currentUser);
        holder.adminUserCardBinding.role.setTextColor(currentUser.getAdmin() ? Color.RED : Color.BLACK);
        holder.bindOnClick(currentUser, listener);
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    @Override
    public void remove(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public void setItems(List<AdminCustomUser> items) {
        this.users = items;
        this.usersCopy.clear();
        this.usersCopy.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public List<AdminCustomUser> getItems() {
        return this.users;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<AdminCustomUser> listener) {
        this.listener = listener;
    }

    @Override
    public void filter(String query) {
        users.clear();
        if (query.isEmpty()) {
            users.addAll(usersCopy);
        } else {
            query = query.toLowerCase();
            String regex = ".*" + query + ".*";

            for (AdminCustomUser user : usersCopy) {
                if (Pattern.matches(regex, user.getName().toLowerCase()) || Pattern.matches(regex, user.getEmail().toLowerCase())) {
                    users.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }


    static class UserViewHolder extends RecyclerView.ViewHolder {
        private final AdminUserCardBinding adminUserCardBinding;

        public UserViewHolder(@NonNull AdminUserCardBinding adminUserCardBinding) {
            super(adminUserCardBinding.getRoot());
            this.adminUserCardBinding = adminUserCardBinding;
        }

        public void bindOnClick(AdminCustomUser user, RecyclerViewClickListener<AdminCustomUser> clickListener) {
            adminUserCardBinding.setUser(user);
            adminUserCardBinding.executePendingBindings();
            itemView.setOnClickListener(v -> {
                        if (clickListener != null) {
                            clickListener.onClick(user);
                        }
                    }
            );
        }
    }
}
