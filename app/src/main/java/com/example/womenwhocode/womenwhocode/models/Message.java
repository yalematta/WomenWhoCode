package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by zassmin on 11/1/15.
 */
@ParseClassName("Message")
public class Message extends ParseObject {
    public static final String USER_ID_KEY = "user_id";
    public static final String PROFILE_ID_KEY = "user_id"; // to minimize search on profile - this might be better off with a pointer
    public static final String BODY_KEY = "body";
    public static final String EVENT_ID_KEY = "event_id";
    public static final String FEATURE_ID_KEY = "event_id";
    public static final String CREATED_AT_KEY = "createdAt";

    public String getProfileId() {
        return getString(PROFILE_ID_KEY);
    }

    public void setProfileId(String profileId) {
        put(PROFILE_ID_KEY, profileId);
    }

    public void setFeatureId(String userId) {
        put(FEATURE_ID_KEY, userId);
    }

    public String getFeatureId() {
        return getString(FEATURE_ID_KEY);
    }

    public void setEventId(String userId) {
        put(EVENT_ID_KEY, userId);
    }

    public String getEventId() {
        return getString(EVENT_ID_KEY);
    }

    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getBody() {
        return getString(BODY_KEY);
    }

    public void setBody(String body) {
        put(BODY_KEY, body);
    }
}
