package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseObject;

/**
 * Created by zassmin on 10/16/15.
 */
public class Network extends ParseObject {
    public static String TITLE_KEY = "title";
    public static String MEETUP_GROUP_ID_KEY = "meetup_group_id";
    public static String MEETUP_URL_KEY = "meetup_url";

    public void setTitle(String title) {
        put(TITLE_KEY, title);
    }

    public String getTitle() {
        return this.get(TITLE_KEY).toString();
    }

    public void setMeetupGroupId(String meetupGroupId) {
        put(MEETUP_GROUP_ID_KEY, meetupGroupId);
    }

    public String getMeetupGroupId() {
        return this.get(MEETUP_GROUP_ID_KEY).toString();
    }

    public void setMeetupUrl(String url) {
        put(MEETUP_URL_KEY, url);
    }

    public String getMeetupUrl() {
        return this.get(MEETUP_URL_KEY).toString();
    }
}
