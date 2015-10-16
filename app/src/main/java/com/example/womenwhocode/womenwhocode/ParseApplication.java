package com.example.womenwhocode.womenwhocode;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by zassmin on 10/15/15.
 */
public class ParseApplication extends Application {
    public static final String PARSE_APPLICATION_ID = "";
    public static final String PARSE_CLIENT_KEY = "";

    @Override
    public void onCreate() {
        super.onCreate();

        // parse initialization
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
    }
}
