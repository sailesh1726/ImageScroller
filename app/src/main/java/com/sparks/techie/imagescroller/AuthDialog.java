package com.sparks.techie.imagescroller;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class AuthDialog extends Dialog{
    private Context context;
    private WebView webView;
    private String authURLString;
    private LinearLayout linearLayout;
    protected AuthDialog(Context context,String authURLString) {
        super(context);
        this.context=context;
        this.authURLString=authURLString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_dialog);
        linearLayout= (LinearLayout) findViewById(R.id.web_auth);

        webView= new WebView(context);

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new AuthWebViewClient(context));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(authURLString);

        linearLayout.addView(webView);
    }
}
