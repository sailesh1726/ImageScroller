package com.sparks.techie.imagescroller;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends ActionBarActivity {
    public String authURLString;

   // public String tokenURLString;
    private LinearLayout linearLayout;
    private String accessTokenString;
    private String access_token;
    private PreferenceHelper preferenceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout= (LinearLayout) findViewById(R.id.main_linear);

        authURLString = Constants.AUTHURL + "?client_id=" + Constants.CLIENT_ID + "&redirect_uri=" + Constants.CALLBACKURL +
                "&response_type=code";

        preferenceHelper= new PreferenceHelper(this);
        access_token= preferenceHelper.getToken();
        if(access_token==null) {
            WebView webView = new WebView(this);
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);
            webView.setWebViewClient(new AuthWebViewClient(this));
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(authURLString);

            linearLayout.addView(webView);
        }
// tokenURLString = Constants.TOKENURL + "?client_id=" + Constants.CLIENT_ID + "&client_secret=" + Constants.CLIENT_SECRET
//                + "&redirect_uri=" + Constants.CALLBACKURL + "&grant_type=authorization_code";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
