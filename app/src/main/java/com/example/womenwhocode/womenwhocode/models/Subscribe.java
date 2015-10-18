package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

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

    public void setUser(User user) {
        put(USER_KEY, user);
    }

    public User getUser()  {
        return (User) getParseObject(USER_KEY);
    }
}
