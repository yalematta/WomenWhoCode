package com.example.womenwhocode.womenwhocode.models;

import com.example.womenwhocode.womenwhocode.utils.Utilities;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by zassmin on 10/16/15.
 */
@ParseClassName("Post")
public class Post extends ParseObject {
    public static String DESCRIPTION_KEY = "description";
    public static String NETWORK_KEY = "network";
    public static String EVENT_KEY = "event";
    public static String FEATURE_KEY = "feature";
    public static String AWESOME_COUNT_KEY = "awesome_count";
    public static String USER_KEY = "user";

    public String getDescription() {
        return this.get(DESCRIPTION_KEY).toString();
    }

    public void setDescription(String description) {
        put(DESCRIPTION_KEY, description);
    }

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

    public String getFeatureImageUrl() {
        return this.getFeature().getImageUrl();
    }

    public String getFeatureTitle()
    {
        return this.getFeature().getTitle();
    }

    public ParseUser getUser() {
        return (ParseUser) getParseObject(USER_KEY);
    }

    public void setUser(ParseUser user) {
        put(USER_KEY, user);
    }

    public int getAwesomeCount() {
        return this.getInt(AWESOME_COUNT_KEY);
    }

    // awesomeCount is for our stretch goals
    public void setAwesomeCount(int awesomeCount) {
        put(AWESOME_COUNT_KEY, awesomeCount);
    }

    public String getPostDateTime() {
        return Utilities.getRelativeTimeAgo(this.getCreatedAt().toString());
    }


}