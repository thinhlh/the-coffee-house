package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.MenuListItemBinding;
import com.coffeehouse.the.models.Category;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoryAdapter extends Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categories;
    private CategoryClickListener listener;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        MenuListItemBinding menuListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.menu_list_item, parent, false);
        return new CategoryViewHolder(menuListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category currentCategory = categories.get(position);
        holder.menuListItemBinding.setCategory(currentCategory);
        Picasso.get().load(currentCategory.getImageUrl()).into((ImageView) holder.itemView.findViewById(R.id.category_image));
        holder.bindOnClick(currentCategory, listener);
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    public void setListener(CategoryClickListener listener) {
        this.listener = listener;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final MenuListItemBinding menuListItemBinding;

        public CategoryViewHolder(@NonNull @NotNull MenuListItemBinding menuListItemBinding) {
            super(menuListItemBinding.getRoot());
            this.menuListItemBinding = menuListItemBinding;
        }

        public void bindOnClick(Category category, CategoryClickListener clickListener) {
            menuListItemBinding.setCategory(category);
            menuListItemBinding.executePendingBindings();
            itemView.setOnClickListener(view -> {
                if (clickListener != null)
                    clickListener.onCategoryClick(category);
            });
        }
    }
}


