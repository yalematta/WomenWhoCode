package com.example.womenwhocode.womenwhocode.models;

import android.util.Log;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by zassmin on 10/16/15.
 */
@ParseClassName("Subscribe")
public class Subscribe extends ParseObject {
    public static String NETWORK_KEY = "network";
    public static String EVENT_KEY = "event";
    public static String FEATURE_KEY = "feature";
    public static String SUBSCRIBED_KEY = "subscribed";
    public static String USER_KEY = "user";

    public static ParseQuery<Subscribe> subscribeParseQuery;
    // FIXME: temp pointer, should remove when currentUser is in!
    public static boolean subscribed = false;

    public void setNetwork(Network network) {
        put(NETWORK_KEY, network);
    }

    public Network getNetwork() {
        return (Network) getParseObject(NETWORK_KEY);
    }

    public void setEvent(Event event) {
        put(EVENT_KEY, event);
    }

    public Event getEvent() {
        return (Event) getParseObject(EVENT_KEY);
    }

    public void setFeature(Feature feature) {
        put(FEATURE_KEY, feature);
    }

    public Feature getFeature() {
        return (Feature) getParseObject(FEATURE_KEY);
    }

    public void setSubscribed(boolean subscribed) {
        put(SUBSCRIBED_KEY, subscribed);
    }

    public boolean getSubscribed() {
        return getBoolean(SUBSCRIBED_KEY);
    }

    public void setUser(ParseUser user) {
        put(USER_KEY, user);
    }

    public ParseUser getUser() {
        return (ParseUser) getParseObject(USER_KEY);
    }

    public static int getCountFor(Feature feature) {
        // Find Subscribe = true where event id is eq to this one
        subscribeParseQuery = Subscribe.getQuery();
        subscribeParseQuery.whereEqualTo(FEATURE_KEY, feature);
        subscribeParseQuery.whereEqualTo(SUBSCRIBED_KEY, true);
        Integer count = subscribeParseQuery.countInBackground().getResult();
        if (count == null) {
            return 0;
        }
        return count;
    }

    public static ParseQuery<Subscribe> getQuery() {
        return ParseQuery.getQuery(Subscribe.class);
    }
}
