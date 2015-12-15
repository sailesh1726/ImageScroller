package com.sparks.techie.imagescroller;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sparks.techie.imagescroller.Model.InstaImageModel;
import com.sparks.techie.imagescroller.Util.Constants;
import com.sparks.techie.imagescroller.Util.PreferenceHelper;
import com.sparks.techie.imagescroller.Util.VolleyTon;


public class MainActivity extends ActionBarActivity {
    public String authURLString;
    private String access_token;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authURLString = Constants.AUTHURL + "?client_id=" + Constants.CLIENT_ID + "&redirect_uri=" + Constants.CALLBACKURL +
                "&response_type=code";

        preferenceHelper = new PreferenceHelper(this);
        access_token = preferenceHelper.getToken();
        if (access_token == null)
            showDialog();
        else {
            fetchData();
        }
    }

    void showDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new LoginDialog();
        newFragment.show(ft, "dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchData() {


        preferenceHelper = new PreferenceHelper(this);
        access_token = preferenceHelper.getToken();
        String url = Constants.APIURL + "/tags/" + Constants.TAG_NAME + "/media/recent" + "/?access_token=" + access_token;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response.length();
                Gson gson = new Gson();
                InstaImageModel instaImageModel = gson.fromJson(response, InstaImageModel.class);
                instaImageModel.getData();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyTon.getInstance().addToRequestQueue(stringRequest);


    }
}
