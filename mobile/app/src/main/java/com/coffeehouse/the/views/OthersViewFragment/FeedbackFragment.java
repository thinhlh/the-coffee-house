package com.coffeehouse.the.views.OthersViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.FeedbackFragmentBinding;
import com.coffeehouse.the.services.UserRepo;

import org.jetbrains.annotations.NotNull;

public class FeedbackFragment extends Fragment implements View.OnClickListener {
    public FeedbackFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        FeedbackFragmentBinding feedbackFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.feedback_fragment, container, false);
        View v = feedbackFragmentBinding.getRoot();

        feedbackFragmentBinding.setUser(UserRepo.user);

        v.findViewById(R.id.close_feedback_fragment).setOnClickListener(this::onClick);

        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new OthersFragment();
        getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
    }
}
