package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Created by zassmin on 11/9/15.
 */
@ParseClassName("Recommendation")
public class Recommendation extends ParseObject {
    public static final String VALID_KEY = "valid";
    public static final String USER_ID_KEY = "user_id";
    private static final String EVENT_KEY = "event";
    private static final String EVENT_ID_KEY = "event_id";
    private static final String FEATURE_KEY = "feature";
    private static final String FEATURE_ID_KEY = "feature_id";

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

    public String getFeatureId() {
        return getString(FEATURE_ID_KEY);
    }

    public void setFeatureId(String userId) {
        put(FEATURE_ID_KEY, userId);
    }

    public String getEventId() {
        return getString(EVENT_ID_KEY);
    }

    public void setEventId(String userId) {
        put(EVENT_ID_KEY, userId);
    }

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public boolean getValid() {
        return getBoolean(VALID_KEY);
    }

    public void setValid(boolean valid) {
        put(VALID_KEY, valid);
    }
}
