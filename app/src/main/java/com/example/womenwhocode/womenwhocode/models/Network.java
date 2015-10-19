package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by zassmin on 10/16/15.
 */
@ParseClassName("Network")
public class Network extends ParseObject {
    public static String TITLE_KEY = "title";
    public static String MEETUP_GROUP_ID_KEY = "meetup_group_id";
    public static String MEETUP_URL_KEY = "meetup_url";
    public static String IMAGE_URL_KEY = "image_url";
    public static String LOCATION_KEY = "location";

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

    public void setImageUrl(String url) {
        put(IMAGE_URL_KEY, url);
    }

    public String getImageUrl() {
        return this.get(IMAGE_URL_KEY).toString();
    }

    public void setLocation(ParseGeoPoint geoPoint) {
        put(LOCATION_KEY, geoPoint);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint(LOCATION_KEY);
    }

    public static Network findByMeetupId(String meetupId) {
        ParseQuery<Network> networkParseQuery = ParseQuery.getQuery(Network.class);
        // networkParseQuery.fromLocalDatastore();
        // FIXME: get from local data store!
        Network network = networkParseQuery.whereEqualTo(
                Network.MEETUP_GROUP_ID_KEY, meetupId).getFirstInBackground().getResult();
        return network;
    }
}
