package com.coffeehouse.the.views.OthersViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;

import org.jetbrains.annotations.NotNull;

public class NewsPromotionFragment extends Fragment implements View.OnClickListener {
    private WebView webView;
    public NewsPromotionFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.newsandpromotion_fragment, container, false);
        webView = (WebView) v.findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.thecoffeehouse.com/pages/rewards");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        v.findViewById(R.id.close_website_contact).setOnClickListener(this::onClick);
        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new OthersFragment();
        getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
    }
}
