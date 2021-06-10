package com.var.bloodflow;

import android.annotation.SuppressLint;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webViewController extends WebViewClient {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        view.getSettings();
        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        return true;
    }


}

