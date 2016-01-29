package com.example.womenwhocode.womenwhocode;

import com.example.womenwhocode.womenwhocode.models.Awesome;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.models.FeatureTag;
import com.example.womenwhocode.womenwhocode.models.Message;
import com.example.womenwhocode.womenwhocode.models.Network;
import com.example.womenwhocode.womenwhocode.models.Notification;
import com.example.womenwhocode.womenwhocode.models.PersonalizationDetails;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.example.womenwhocode.womenwhocode.models.Profile;
import com.example.womenwhocode.womenwhocode.models.Recommendation;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.example.womenwhocode.womenwhocode.models.Tag;
import com.example.womenwhocode.womenwhocode.models.UserNotification;
import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by zassmin on 10/15/15.
 */
public class WomenWhoCodeApplication extends Application {
    private static final String PARSE_APPLICATION_ID = "twI2kWhB1ImLgnc5rDpA2F8b7TfRVImbZqsywWO1";
    private static final String PARSE_CLIENT_KEY = "QpMOPn0fte0sdkY8GYKcpsSFhEY2rn3eoJxH73wO";
    public static int currentPosition;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        // Register parse models
        ParseObject.registerSubclass(Network.class);
        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(Feature.class);
        ParseObject.registerSubclass(PersonalizationDetails.class);
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Profile.class);
        ParseObject.registerSubclass(Subscribe.class);
        ParseObject.registerSubclass(Awesome.class);
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(Tag.class);
        ParseObject.registerSubclass(FeatureTag.class);
        ParseObject.registerSubclass(Recommendation.class);
        ParseObject.registerSubclass(UserNotification.class);
        ParseObject.registerSubclass(Notification.class);

        // parse initialization
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Hind-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
