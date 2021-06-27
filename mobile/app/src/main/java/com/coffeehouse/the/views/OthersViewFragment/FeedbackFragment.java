package com.coffeehouse.the.views.OthersViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.FeedbackFragmentBinding;
import com.coffeehouse.the.models.CustomUser;
import com.coffeehouse.the.models.Feedback;
import com.coffeehouse.the.services.repositories.FeedbacksRepo;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FeedbackFragment extends Fragment implements View.OnClickListener {
    private final FeedbacksRepo feedbacksRepo = new FeedbacksRepo();
    private FeedbackFragmentBinding feedbackFragmentBinding;
    private final CustomUser user = UserRepo.user;
    ;

    public FeedbackFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        feedbackFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.feedback_fragment, container, false);

        feedbackFragmentBinding.setUser(user);

        feedbackFragmentBinding.btnSendFeedback.setOnClickListener(l -> {
            sendFeedback();
        });
        feedbackFragmentBinding.closeFeedbackFragment.setOnClickListener(this::onClick);

        return feedbackFragmentBinding.getRoot();
    }

    private void sendFeedback() {
        String s = Objects.requireNonNull(feedbackFragmentBinding.inputFeedback.getText()).toString();
        if (s.isEmpty()) {
            feedbackFragmentBinding.inputFeedback.setError("Yêu cầu nội dung phản hồi");
        } else {
            Feedback feedback = new Feedback(FirebaseAuth.getInstance().getUid(), user.getName(), user.getEmail(), user.getPhoneNumber(), feedbackFragmentBinding.inputFeedback.getText().toString());
            feedbacksRepo.addFeedbackData(feedback).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Góp ý của bạn đã được gửi đi. Cảm ơn đã đóng góp!", Toast.LENGTH_SHORT).show();
                    feedbackFragmentBinding.inputFeedback.setText("");
                    feedbackFragmentBinding.inputFeedback.setError(null);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new OthersFragment();
        getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
    }
}
