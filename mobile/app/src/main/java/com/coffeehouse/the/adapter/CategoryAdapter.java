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
import com.coffeehouse.the.utils.helper.ClickableRecyclerView;
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends Adapter<CategoryAdapter.CategoryViewHolder> implements ClickableRecyclerView<Category> {
    private List<Category> categories = new ArrayList<>();
    private RecyclerViewClickListener<Category> listener;

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
        if (!currentCategory.getImageUrl().isEmpty())
            Picasso.get().load(currentCategory.getImageUrl()).into((ImageView) holder.itemView.findViewById(R.id.category_image));
        holder.bindOnClick(currentCategory, listener);
    }


    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }


    @Override
    public void setItems(List<Category> items) {
        this.categories = items;
        notifyDataSetChanged();
    }

    @Override
    public List<Category> getItems() {
        return categories;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<Category> listener) {
        this.listener = listener;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final MenuListItemBinding menuListItemBinding;

        public CategoryViewHolder(@NonNull @NotNull MenuListItemBinding menuListItemBinding) {
            super(menuListItemBinding.getRoot());
            this.menuListItemBinding = menuListItemBinding;
        }

        public void bindOnClick(Category category, RecyclerViewClickListener<Category> clickListener) {
            menuListItemBinding.setCategory(category);
            menuListItemBinding.executePendingBindings();
            itemView.setOnClickListener(view -> {
                if (clickListener != null)
                    clickListener.onClick(category);
            });
        }

    }
}


