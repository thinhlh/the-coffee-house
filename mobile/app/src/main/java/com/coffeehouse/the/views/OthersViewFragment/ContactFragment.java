package com.coffeehouse.the.views.OthersViewFragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.ContactFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class ContactFragment extends Fragment implements View.OnClickListener {
    private ContactFragmentBinding contactFragmentBinding;

    public ContactFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        contactFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.contact_fragment, container, false);
        View v = contactFragmentBinding.getRoot();

        contactFragmentBinding.closeContactFragment.setOnClickListener(this::onClick);
        contactFragmentBinding.phoneContact.setOnClickListener(this::onClick);
        contactFragmentBinding.emailContact.setOnClickListener(this::onClick);
        contactFragmentBinding.websiteContact.setOnClickListener(this::onClick);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_contact_fragment:
                Fragment fragment = new OthersFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
            case R.id.phone_contact:
                openPhone();
                break;
            case R.id.email_contact:
                openEmail();
                break;
            case R.id.website_contact:
                openWebsite();
                break;
        }
    }

    private void openPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + contactFragmentBinding.telephonenumberText.getText().toString()));
        startActivity(intent);
    }

    private void openEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"," + contactFragmentBinding.emailaddressText.getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, ""));
    }

    private void openWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + contactFragmentBinding.websiteaddressText.getText().toString() + "/"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            startActivity(intent);
        }
    }

}
