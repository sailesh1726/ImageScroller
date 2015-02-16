package com.sparks.techie.imagescroller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AuthWebViewClient extends WebViewClient{
private String request_token;
    private String access_token;
private PreferenceHelper preferenceHelper;
private Context context;
private FetchData fetchData;
//public String tagURLString;
    //public String tokenURLString;
    public AuthWebViewClient(Context context) {
        this.context=context;
        preferenceHelper=new PreferenceHelper(context);
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        if (url.startsWith(Constants.CALLBACKURL))
        {
            System.out.println(url);
            String parts[] = url.split("=");
            request_token = parts[1];  //This is your request token.
            //preferenceHelper.saveToken(request_token);
            new GetConnection().execute(request_token);
//            InstagramLoginDialog.this.dismiss();
            return true;
        }
        return false;

    }
    public class GetConnection extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {
            try
            {
                fetchData= new FetchData();

                access_token = fetchData.getAccessToken(params[0]);

                preferenceHelper.saveToken(access_token);

                fetchData.fetchTag(context);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

    }
}
