package com.sparks.techie.imagescroller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class AuthWebViewClient extends WebViewClient {
    private String request_token;
    //private String access_token;
    private PreferenceHelper preferenceHelper;
    private Context context;
    private FetchData fetchData;
    private String TAG = AuthWebViewClient.class.getSimpleName();
    private Gson gson;
    //public String tagURLString;
    //public String tokenURLString;
    public AuthWebViewClient(Context context) {
        this.context = context;
        preferenceHelper = new PreferenceHelper(context);
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith(Constants.CALLBACKURL)) {
            Log.e(TAG, url);
            System.out.println(url);
            String parts[] = url.split("=");
            request_token = parts[1];  //This is your request token.
            //preferenceHelper.saveToken(request_token);

            StringRequest stringRequest  = new StringRequest(Request.Method.POST,
                    Constants.TOKENURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    response.length();
                    gson = new Gson();
                    AccessTokenModel accessTokenModel=gson.fromJson(response, AccessTokenModel.class);
                    String access_token=accessTokenModel.getAccess_token();
                    preferenceHelper.saveToken(access_token);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    byte[] gg = error.networkResponse.data;
                    Log.e(TAG, error.toString());
                }
            }){

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
            //new GetConnection().execute(request_token);
//            InstagramLoginDialog.this.dismiss();
            return true;
        }
        return false;

    }

    public class GetConnection extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                fetchData = new FetchData();

//                access_token = fetchData.getAccessToken(params[0]);
//
//                preferenceHelper.saveToken(access_token);

                fetchData.fetchTag(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}