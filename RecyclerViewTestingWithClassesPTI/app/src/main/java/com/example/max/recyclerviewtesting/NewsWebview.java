package com.example.max.recyclerviewtesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsWebview extends AppCompatActivity {

    WebView webview;
    String website_adress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_webview);
        webview=findViewById(R.id.web_view);
         webview.getSettings().setJavaScriptEnabled(true);
         Intent intent= getIntent();
         String link = intent.getStringExtra("NewsLink");

         webview.loadUrl(link);
         webview.setWebViewClient(new MyWebViewClient());
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if(webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
