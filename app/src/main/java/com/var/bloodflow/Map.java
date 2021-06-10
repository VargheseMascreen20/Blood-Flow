package com.var.bloodflow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Map extends AppCompatActivity {
    WebView mywebView;
    String lat, lon;
    String url;
    int flag;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mywebView = (WebView) findViewById(R.id.webview);
        mywebView.setWebViewClient(new WebViewClient());
        flag = getIntent().getExtras().getInt("FLAG");
        if (flag == 0) {
            lat = getIntent().getExtras().getString("Lat");
            lon = getIntent().getExtras().getString("Lontd");
//        url= "https://www.eraktkosh.in/BLDAHIMS/bloodbank/stockAvailability.cnt";
//        url = "https://www.social-searcher.com/google-social-search/?q=blood+donors+kochi&fb=on";
            url = "https://www.google.com/maps/search/blood+donors+near+me/@" + lat + "," + lon + ",12.13z";
            mywebView.loadUrl(url);
            //"https://www.google.com/maps/dir///"
            //https://www.google.com/search?tbs=lf:1,lf_ui:2&tbm=lcl&sxsrf=ALeKk029u99ZT1noBW6eau9WZbWAej4ugA:1623167753673&q=blood+donations+near+me&rflfq=1&num=10&sa=X&ved=2ahUKEwiJ7fiKs4jxAhWBXisKHXgkAn4QjGp6BAgQEEE&biw=1536&bih=758&dpr=1.25#rlfi=hd:;si:;mv:[[10.862584026176384,78.25198313671876],[9.234563353411698,75.18679758984376],null,[10.049598423883234,76.71939036328126],9]
            WebSettings webSettings = mywebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
        } else if (flag == 1) {
            url = "https://www.eraktkosh.in/BLDAHIMS/bloodbank/stockAvailability.cnt";
            mywebView.loadUrl(url);
            WebSettings webSettings = mywebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
        } else if (flag == 2) {
            String city = getIntent().getExtras().getString("City");
            url = "https://www.helpingera.in/search/a%252B/" + city + "/";
            mywebView.loadUrl(url);
            WebSettings webSettings = mywebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
        }
    }

    public class mywebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
//    @Override
//    public void onBackPressed(){
//        if(mywebView.canGoBack()) {
//            mywebView.goBack();
//        }
//        else{
//            super.onBackPressed();
//        }
//    }
}