package com.coffeehouse.the.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeehouse.the.R;
import com.coffeehouse.the.viewModels.AuthViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Date;
import java.time.Instant;

public class SignUpFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView mDisplayDate;

    private AuthViewModel authViewModel=new AuthViewModel();

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_signup, container, false);

        //VALIDATION HERE
        ((MaterialButton) v.findViewById(R.id.sign_up_button)).setOnClickListener(button -> {

            String name = ((TextInputLayout) v.findViewById(R.id.name_text_input)).getEditText().getText().toString().trim();
            String email = ((TextInputLayout) v.findViewById(R.id.email_text_input)).getEditText().getText().toString().trim();
            String phone = ((TextInputLayout) v.findViewById(R.id.phone_text_input)).getEditText().getText().toString().trim();
            String password = ((TextInputLayout) v.findViewById(R.id.password_text_input)).getEditText().getText().toString().trim();

            authViewModel.signUp(email, name, password, phone, Date.from(Instant.now())).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getContext(), HomeActivity.class));
                } else {
                    Toast.makeText(getContext(), "Account incorrect", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return v;


    }
}