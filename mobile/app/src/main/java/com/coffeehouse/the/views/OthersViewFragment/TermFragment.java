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

public class TermFragment extends Fragment implements View.OnClickListener {
    private WebView webView;

    public TermFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.term_fragment, container, false);
        webView = (WebView) v.findViewById(R.id.term_web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://order.thecoffeehouse.com/term");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        v.findViewById(R.id.close_term_fragment).setOnClickListener(this::onClick);
        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new OthersFragment();
        getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
    }
}
