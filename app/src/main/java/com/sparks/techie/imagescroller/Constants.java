package com.sparks.techie.imagescroller;

public class Constants {
    public static final String AUTHURL = "https://api.instagram.com/oauth/authorize/";
    //Used for Authentication.
    public static final String TOKENURL ="https://api.instagram.com/oauth/access_token";
    //Used for getting token and User details.
    public static final String APIURL = "https://api.instagram.com/v1";

    public static final String TAG_NAME = "selfie";

    //Used to specify the API version which we are going to use.
    public static final String CALLBACKURL = "http://selfie_image_scroller";
//The callback url that we have used while registering the application.
    public static final String CLIENT_ID= "421600e2357246e497055eddc467be3d";
    public static final String CLIENT_SECRET= "468f970d310040cfa763a18a862c40a9";

    public final static String PREFERENCES_FILE = "imagescroller";

}
