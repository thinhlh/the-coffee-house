package com.coffeehouse.the.adapter;

import com.coffeehouse.the.models.Promotion;
import com.coffeehouse.the.utils.helper.Searchable;
import com.coffeehouse.the.utils.helper.SwipeAbleRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AdminPromotionAdapter extends PromotionAdapter implements SwipeAbleRecyclerView<Promotion>, Searchable {

    private final List<Promotion> promotionsCopy = new ArrayList<>();

    @Override
    public void setItems(List<Promotion> items) {
        super.setItems(items);
        this.promotionsCopy.clear();
        this.promotionsCopy.addAll(items);
    }

    @Override
    public void remove(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public void filter(String query) {
        super.promotions.clear();
        if (query.isEmpty()) {
            super.promotions.addAll(promotionsCopy);
        } else {
            query = query.toLowerCase();
            String regex = ".*" + query + ".*";

            for (Promotion promotion : promotionsCopy) {
                if (Pattern.matches(regex, promotion.getTitle().toLowerCase())
                        ||
                        Pattern.matches(regex, promotion.getCode().toLowerCase())) {
                    promotions.add(promotion);
                }
            }
        }
        notifyDataSetChanged();
    }
}
