package com.sparks.techie.imagescroller;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.sparks.techie.imagescroller.Util.Constants;


public class LoginDialog extends DialogFragment {
    private String authURLString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        LinearLayout authLinearLayout = (LinearLayout) v.findViewById(R.id.authLinearLayout);

        authURLString = Constants.AUTHURL + "?client_id=" + Constants.CLIENT_ID + "&redirect_uri=" + Constants.CALLBACKURL +
                "&response_type=code";

        this.getDialog().setCanceledOnTouchOutside(false);
        WebView webView = new WebView(getActivity());
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new AuthWebViewClient(getActivity(), LoginDialog.this));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(authURLString);

        authLinearLayout.addView(webView);
        return v;
    }
}
