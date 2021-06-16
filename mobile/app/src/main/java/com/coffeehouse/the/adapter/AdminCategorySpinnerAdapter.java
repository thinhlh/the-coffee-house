package com.coffeehouse.the.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.coffeehouse.the.models.Category;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminCategorySpinnerAdapter extends ArrayAdapter<Category> {

    private final List<Category> categories;

    public AdminCategorySpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        this.categories = objects;
    }

    @Override
    public boolean isEnabled(int position) {
        //The first item will act as hint
        return position != 0;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        if (position == 0) {
            // Set the hint text color gray
            view.setText("Choose category...");
            view.setTextColor(Color.GRAY);
        } else {
            view.setTextColor(Color.BLACK);
            view.setText(categories.get(position).getTitle());
        }
        return view;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(position == 0 ? "Choose category..." : categories.get(position).getTitle());
        return label;
    }
}
