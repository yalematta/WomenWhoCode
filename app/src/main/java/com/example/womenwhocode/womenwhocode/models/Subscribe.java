package com.example.womenwhocode.womenwhocode.models;

import android.util.Log;

import com.parse.CountCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by zassmin on 10/16/15.
 */
@ParseClassName("Subscribe")
public class Subscribe extends ParseObject {
    public static String NETWORK_KEY = "network";
    public static String EVENT_KEY = "event";
    public static String FEATURE_KEY = "feature";
    public static String SUBSCRIBED_KEY = "subscribed";
    private static String USER_KEY = "user";

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

    public static int getCountFor(Event event) throws ParseException {
        // Find Subscribe = true where event id is eq to this one
        ParseQuery<Subscribe> subscribeParseQuery = ParseQuery.getQuery(Subscribe.class);
        subscribeParseQuery.whereEqualTo(EVENT_KEY, event).whereEqualTo(SUBSCRIBED_KEY, true);
        final int[] count = {0};
        subscribeParseQuery.countInBackground(new CountCallback() {
            @Override
            public void done(int i, ParseException e) {
                if (e == null) {
                    count[0] = i;
                    Log.d("SUBSCRIPTION_COUNT", "Subscribe count" + i);
                } else {
                    Log.d("SUBSCRIPTION_CNT_ERROR", "0 count data");
                }
            }
        });
        return count[0];
    }

    public static int getCountFor(Feature feature) throws ParseException {
        // Find Subscribe = true where event id is eq to this one
        ParseQuery<Subscribe> subscribeParseQuery = ParseQuery.getQuery(Subscribe.class);
        subscribeParseQuery.whereEqualTo(FEATURE_KEY, feature).whereEqualTo(SUBSCRIBED_KEY, true);
        final int[] count = {0};
        subscribeParseQuery.countInBackground(new CountCallback() {
            @Override
            public void done(int i, ParseException e) {
                if (e == null) {
                    count[0] = i;
                    Log.d("SUBSCRIPTION_COUNT", "Subscribe count" + i);
                } else {
                    Log.d("SUBSCRIPTION_CNT_ERROR", "0 count data");
                }
            }
        });
        return count[0];
    }

    public static boolean isSubscribed(ParseUser user, Event event) {
        // FIXME: this should check getSubscribed after querying for user and event
        return subscribed;
    }

    public static boolean unSubscribeUserToEvent(ParseUser user, Event event) {
        if (user == null) {
            subscribed = false;
            return subscribed; // FIXME: remove once we have the concept of currentUser testing purposes
        }

        // FIXME: query locally
        // check if a subscribe object exists
        subscribeParseQuery = Subscribe.getQuery();
        subscribeParseQuery.whereEqualTo(USER_KEY, user).whereEqualTo(EVENT_KEY, event);
        Subscribe subscribe = subscribeParseQuery.getFirstInBackground().getResult();
        if (subscribe == null) {
            subscribe = new Subscribe();
            subscribe.setEvent(event);
            subscribe.setUser(user);
        }

        subscribe.setSubscribed(false);
        subscribe.saveInBackground();

        return true;
    }

    public static boolean subscribeUserToEvent(ParseUser user, Event event) {
        if (user == null) {
            subscribed = true;
            return subscribed; // FIXME: remove once we have the concept of currentUser testing purposes
        }

        // FIXME: query locally
        // check if a subscribe object exists
        subscribeParseQuery = Subscribe.getQuery();
        subscribeParseQuery.whereEqualTo(USER_KEY, user).whereEqualTo(EVENT_KEY, event);
        Subscribe subscribe = subscribeParseQuery.getFirstInBackground().getResult();
        if (subscribe == null) {
            subscribe = new Subscribe();
            subscribe.setEvent(event);
            subscribe.setUser(user);
        }

        subscribe.setSubscribed(true);
        subscribe.saveInBackground();

        return true; // FIXME: return true if success false if done, run block for in background
    }

    public static ParseQuery<Subscribe> getQuery() {
        return ParseQuery.getQuery(Subscribe.class);
    }
}
