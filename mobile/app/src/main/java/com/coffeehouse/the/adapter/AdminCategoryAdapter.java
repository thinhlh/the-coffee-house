package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminCategoryCardBinding;
import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;
import com.coffeehouse.the.utils.helper.SwipeAbleRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdminCategoryAdapter extends RecyclerView.Adapter<AdminCategoryAdapter.AdminCategoryViewHolder> implements SwipeAbleRecyclerView<Category> {
    private List<Category> categories = new ArrayList<>();
    private final List<Category> categoriesCopy = new ArrayList<>();
    private RecyclerViewClickListener<Category> listener;

    @NonNull
    @Override
    public AdminCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminCategoryCardBinding adminCategoryCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.admin_category_card, parent, false);
        return new AdminCategoryViewHolder(adminCategoryCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCategoryViewHolder holder, int position) {
        Category currentCategory = categories.get(position);
        holder.adminCategoryCardBinding.setCategory(currentCategory);
        if (!currentCategory.getImageUrl().isEmpty())
            Picasso.get().load(currentCategory.getImageUrl()).into(holder.adminCategoryCardBinding.categoryImage);
        holder.bindOnClick(currentCategory, listener);
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    @Override
    public void setItems(List<Category> items) {
        this.categories = items;
        this.categoriesCopy.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public List<Category> getItems() {
        return this.categories;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<Category> listener) {
        this.listener = listener;
    }

    @Override
    public void remove(int position) {
        notifyItemRemoved(position);
    }


    static class AdminCategoryViewHolder extends RecyclerView.ViewHolder {
        private final AdminCategoryCardBinding adminCategoryCardBinding;

        public AdminCategoryViewHolder(@NonNull AdminCategoryCardBinding adminCategoryCardBinding) {
            super(adminCategoryCardBinding.getRoot());
            this.adminCategoryCardBinding = adminCategoryCardBinding;
        }

        public void bindOnClick(Category category, RecyclerViewClickListener<Category> clickListener) {
            adminCategoryCardBinding.setCategory(category);
            adminCategoryCardBinding.executePendingBindings();
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onClick(category);
                }
            });
        }
    }
}
