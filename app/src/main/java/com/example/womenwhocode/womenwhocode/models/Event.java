package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by zassmin on 10/16/15.
 */
@ParseClassName("Event")
public class Event extends ParseObject {
    public static String NETWORK_KEY = "network";
    public static String DATETIME_KEY = "event_date";
    public static String LOCATION_KEY = "location"; // more like the name where the place is held
    public static String URL_KEY = "url"; // could be an event url from meetup, facebook, eventbrite, etc
    public static String TITLE_KEY = "title";
    public static String FEATURED_KEY = "featured";
    public static String MEETUP_EVENT_ID_KEY = "meetup_event_id";
    public static String RSVP_COUNT_KEY = "rsvp_count";
    public static String RSVP_LIMIT_KEY = "rsvp_limit";
    public static String AWESOME_COUNT_KEY = "awesome_count";
    public static String DESCRIPTION_KEY = "description";

    public void setNetwork(Network network) {
        put(NETWORK_KEY, network);
    }

    public Network getNetwork() {
        return (Network) getParseObject(NETWORK_KEY);
    }

    public void setEventDateTime(String datetime) {
        put(DATETIME_KEY, datetime);
    }

    public String getEventDateTime() {
        return this.get(DATETIME_KEY).toString();
    }

    public void setLocation(String location) {
        put(LOCATION_KEY, location);
    }

    public String getLocation() {
        return this.get(LOCATION_KEY).toString();
    }

    public void setUrl(String url) {
        put(URL_KEY, url);
    }

    public String getUrl() {
        return this.get(URL_KEY).toString();
    }

    public void setTitle(String title) {
        put(TITLE_KEY, title);
    }

    public String getTitle() {
        return this.get(TITLE_KEY).toString();
    }

    public void setFeatured(boolean featured) {
        put(FEATURED_KEY, featured);
    }

    public boolean getFeatured() {
        return this.getBoolean(FEATURED_KEY);
    }

    public void setMeetupEventId(String id) {
        put(MEETUP_EVENT_ID_KEY, id);
    }

    public String getMeetupEventId() {
        return this.get(MEETUP_EVENT_ID_KEY).toString();
    }

    public void setRsvpCount(int rsvpCount) {
        put(RSVP_COUNT_KEY, rsvpCount);
    }

    public int getRsvpCount() {
        return this.getInt(RSVP_COUNT_KEY);
    }

    public void getRsvpLimit(int rsvpLimit) {
        put(RSVP_LIMIT_KEY, rsvpLimit);
    }

    public int getRsvpLimit() {
        return this.getInt(RSVP_LIMIT_KEY);
    }

    public void setDescription(String description) {
        put(DESCRIPTION_KEY, description);
    }

    public String getDescription() {
        return this.get(DESCRIPTION_KEY).toString();
    }

    // awesomeCount is for our stretch goals
    public void setAwesomeCount(int awesomeCount) {
        put(AWESOME_COUNT_KEY, awesomeCount);
    }

    public int getAwesomeCount() {
        return this.getInt(AWESOME_COUNT_KEY);
    }
}
