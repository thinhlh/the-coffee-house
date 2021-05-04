package com.coffeehouse.the.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.coffeehouse.the.R;
import com.coffeehouse.the.services.FetchNotifications;
import com.coffeehouse.the.services.FetchUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextInputLayout input_email, input_password;
    Button btn_login;
    ProgressBar pb_loginload;
    FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangkiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        input_email = (TextInputLayout) v.findViewById(R.id.text_input_email);
        input_password = (TextInputLayout) v.findViewById(R.id.text_input_password);
        btn_login = (Button) v.findViewById(R.id.login_button);
        pb_loginload = (ProgressBar) v.findViewById(R.id.loginload_progressbar);
        mAuth = FirebaseAuth.getInstance();

//        Button login = (Button) v.findViewById(R.id.login);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new FetchNotifications().fetchNotifications().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        //new FetchUser().fetchUser().getResult().
//
//                        if(task.isSuccessful()){
//                            //BINDING HERE
//                        }
//                        else {
//                            //Show error
//                        }
//                    }
//                });
//            }
//        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.login_button:
                        AuthLogin();
                        break;
                }
            }


        });

        return v;
    }

    private boolean emailValidation(){
        String email = input_email.getEditText().getText().toString().trim();
        if (email.isEmpty()){
            input_email.setError("Email is required");
            input_email.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            input_email.setError("Invalid Email");
            input_email.requestFocus();
            return false;
        } else {
            input_email.setError(null);
            return true;
        }
    }
    private boolean passwordValidation(){
        String pw = input_password.getEditText().getText().toString().trim();
        if (pw.isEmpty()){
            input_password.setError("Password is required");
            input_password.requestFocus();
            return false;
        } else if (pw.length() < 6){
            input_password.setError("Password required at least 6 characters");
            input_password.requestFocus();
            return false;
        } else{
            input_password.setError(null);
            return true;
        }
    }

    private void AuthLogin() {
        pb_loginload.setVisibility(View.VISIBLE);
        if (!emailValidation() || !passwordValidation()){
            pb_loginload.setVisibility(View.GONE);
            return;
        }
        String email = input_email.getEditText().getText().toString().trim();
        String password = input_password.getEditText().getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //new intent
                            Toast.makeText(getContext(), "Login success", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getContext(), HomeControlActivity.class));
                            pb_loginload.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(getContext(), "Account incorrect", Toast.LENGTH_SHORT).show();
                            pb_loginload.setVisibility(View.GONE);
                        }
                    }
                });
    }
}