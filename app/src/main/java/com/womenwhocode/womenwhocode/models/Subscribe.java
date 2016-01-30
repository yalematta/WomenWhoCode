package com.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by zassmin on 10/16/15.
 */
@ParseClassName("Subscribe")
public class Subscribe extends ParseObject {
    public static final String EVENT_KEY = "event";
    public static final String FEATURE_KEY = "feature";
    public static final String SUBSCRIBED_KEY = "subscribed";
    public static final String USER_KEY = "user";
    private static final String NETWORK_KEY = "network";

    public Network getNetwork() {
        return (Network) getParseObject(NETWORK_KEY);
    }

    public void setNetwork(Network network) {
        put(NETWORK_KEY, network);
    }

    public Event getEvent() {
        return (Event) getParseObject(EVENT_KEY);
    }

    public void setEvent(Event event) {
        put(EVENT_KEY, event);
    }

    public Feature getFeature() {
        return (Feature) getParseObject(FEATURE_KEY);
    }

    public void setFeature(Feature feature) {
        put(FEATURE_KEY, feature);
    }

    public boolean getSubscribed() {
        return getBoolean(SUBSCRIBED_KEY);
    }

    public void setSubscribed(boolean subscribed) {
        put(SUBSCRIBED_KEY, subscribed);
    }

    public ParseUser getUser() {
        return (ParseUser) getParseObject(USER_KEY);
    }

    public void setUser(ParseUser user) {
        put(USER_KEY, user);
    }
}
