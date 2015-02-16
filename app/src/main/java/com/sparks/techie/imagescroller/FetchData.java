package com.sparks.techie.imagescroller;


import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchData {

private String access_token;
private PreferenceHelper preferenceHelper;
    public String getAccessToken(String request_token){

        try
        {
            URL url = new URL(Constants.TOKENURL);
            //URL url = new URL(mTokenUrl + "&code=" + code);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            //urlConnection.connect();
            OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
            writer.write("client_id="+Constants.CLIENT_ID+
                    "&client_secret="+Constants.CLIENT_SECRET+
                    "&grant_type=authorization_code" +
                    "&redirect_uri="+Constants.CALLBACKURL+
                    "&code=" + request_token);
            writer.flush();
            String response = streamToString(urlConnection.getInputStream());
//                Log.i(TAG, "response " + response);
            JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();

            access_token = jsonObj.getString("access_token");
////                Log.i(TAG, "Got access token: " + mAccessToken);
//
//            preferenceHelper.saveToken(access_token);
//            String id = jsonObj.getJSONObject("user").getString("id");
//            String user = jsonObj.getJSONObject("user").getString("username");
//            String name = jsonObj.getJSONObject("user").getString("full_name");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return access_token;
    }

    public void fetchTag(Context context) {


                try {
                    preferenceHelper= new PreferenceHelper(context);
                    access_token= preferenceHelper.getToken();
                    URL url = new URL(Constants.APIURL + "/tags/" + Constants.TAG_NAME +"/media/recent"+ "/?access_token=" + access_token);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoInput(true);
                    urlConnection.connect();
                    String response = streamToString(urlConnection.getInputStream());
                    System.out.println(response);
                    JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
//                    String name = jsonObj.getJSONObject("data").getString("full_name");
//                    String bio = jsonObj.getJSONObject("data").getString("bio");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }



    }

    private String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = sb.toString();
        }

        return str;
    }
}
