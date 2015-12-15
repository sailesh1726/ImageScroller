package com.sparks.techie.imagescroller;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sparks.techie.imagescroller.Model.AccessTokenModel;
import com.sparks.techie.imagescroller.Util.Constants;
import com.sparks.techie.imagescroller.Util.PreferenceHelper;
import com.sparks.techie.imagescroller.Util.VolleyTon;

import java.util.HashMap;
import java.util.Map;

public class AuthWebViewClient extends WebViewClient {
    private final LoginDialog loginDialog;
    private String request_token;
    private PreferenceHelper preferenceHelper;
    private Context context;
    private String TAG = AuthWebViewClient.class.getSimpleName();
    private Gson gson;

    public AuthWebViewClient(Context context, LoginDialog loginDialog) {
        this.context = context;
        preferenceHelper = new PreferenceHelper(context);
        this.loginDialog = loginDialog;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith(Constants.CALLBACKURL)) {
            Log.e(TAG, url);
            System.out.println(url);
            String parts[] = url.split("=");
            request_token = parts[1];  //This is your request token.

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.TOKENURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    response.length();
                    gson = new Gson();
                    AccessTokenModel accessTokenModel = gson.fromJson(response, AccessTokenModel.class);
                    String access_token = accessTokenModel.getAccess_token();
                    preferenceHelper.saveToken(access_token);
                    loginDialog.dismiss();
                    ((MainActivity) context).fetchData();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    byte[] gg = error.networkResponse.data;
                    Log.e(TAG, error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    // the POST parameters:
                    params.put("client_id", Constants.CLIENT_ID);
                    params.put("client_secret", Constants.CLIENT_SECRET);
                    params.put("grant_type", "authorization_code");
                    params.put("redirect_uri", Constants.CALLBACKURL);
                    params.put("code", request_token);
                    return params;
                }
            };

            VolleyTon.getInstance().addToRequestQueue(stringRequest);

            return true;
        }
        return false;

    }
}