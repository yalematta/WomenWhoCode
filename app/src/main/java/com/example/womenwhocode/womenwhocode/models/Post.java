package com.example.womenwhocode.womenwhocode.models;

import com.example.womenwhocode.womenwhocode.utils.Utilities;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by zassmin on 10/16/15.
 */
@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String EVENT_KEY = "event";
    public static final String FEATURE_KEY = "feature";
    public static final String AWESOME_COUNT_KEY = "awesome_count";
    public static final String USER_KEY = "user";
    private static final String DESCRIPTION_KEY = "description";
    private static final String NETWORK_KEY = "network";

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
        Event event = null;
        try {
            event = getParseObject(EVENT_KEY).fetchIfNeeded();
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return event;
    }

    public void setEvent(Event event) {
        put(EVENT_KEY, event);
    }

    public Feature getFeature() {
        Feature feature = null;
        try {
            feature = getParseObject(FEATURE_KEY).fetchIfNeeded();
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return feature;
    }

    public void setFeature(Feature feature) {
        put(FEATURE_KEY, feature);
    }

    public String getFeatureImageUrl() {
        Feature feature = null;
        try {
            feature = this.getFeature().fetchIfNeeded();
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return feature != null ? feature.getImageUrl() : null;
    }

    public String getFeatureTitle() {
        Feature feature = null;
        try {
            feature = this.getFeature().fetchIfNeeded();
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return feature != null ? feature.getTitle() : null;
    }

    public ParseUser getUser() {
        ParseUser user = null;
        try {
            user = getParseObject(USER_KEY).fetchIfNeeded();
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return user;
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
        String rtime = "now";
        if (this.getCreatedAt() != null) {
            rtime = Utilities.getRelativeTimeAgo(this.getCreatedAt().toString());
        }
        return rtime;
    }

    public ParseFile getPostPicFile() {
        return getParseFile("photo");
    }

    public void setPostPicFile(ParseFile file) {
        put("photo", file);
    }


}