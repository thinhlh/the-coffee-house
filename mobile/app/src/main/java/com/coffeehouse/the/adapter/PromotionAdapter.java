package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.PromotionListItemBinding;
import com.coffeehouse.the.models.Promotion;
import com.coffeehouse.the.utils.helper.ClickableRecyclerView;
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PromotionAdapter extends Adapter<PromotionAdapter.PromotionViewHolder> implements ClickableRecyclerView<Promotion> {
    protected List<Promotion> promotions = new ArrayList<>();
    private RecyclerViewClickListener<Promotion> listener;
    private boolean flag = true;

    @NonNull
    @NotNull
    @Override
    public PromotionAdapter.PromotionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PromotionListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.promotion_list_item, parent, false);
        return new PromotionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PromotionAdapter.PromotionViewHolder holder, int position) {
        Promotion currentPromotion = promotions.get(position);
        holder.binding.setPromotion(currentPromotion);
        if (!currentPromotion.getImageUrl().isEmpty())
            Picasso.get().load(currentPromotion.getImageUrl()).into(holder.binding.image1CardviewPromotion1);
        holder.bindOnClick(currentPromotion, listener);
    }

    @Override
    public int getItemCount() {
        if (promotions.isEmpty())
            return 0;

        if (flag)
            return promotions.size();
        else {
            return Math.min(promotions.size(), 3);
        }
    }

    @Override
    public void setItems(List<Promotion> items) {
        this.promotions = items;
        notifyDataSetChanged();
    }

    @Override
    public List<Promotion> getItems() {
        return this.promotions;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<Promotion> listener) {
        this.listener = listener;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    static class PromotionViewHolder extends RecyclerView.ViewHolder {
        private final PromotionListItemBinding binding;

        public PromotionViewHolder(@NonNull @NotNull PromotionListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindOnClick(Promotion promotion, RecyclerViewClickListener<Promotion> clickListener) {
            binding.setPromotion(promotion);
            binding.executePendingBindings();
            itemView.setOnClickListener(l -> {
                if (clickListener != null) {
                    clickListener.onClick(promotion);
                }
            });
        }
    }
}