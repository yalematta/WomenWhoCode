package com.example.womenwhocode.womenwhocode.models;

import com.example.womenwhocode.womenwhocode.utils.ModelJSONObject;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by zassmin on 10/16/15.
 */
@ParseClassName("Event")
public class Event extends ParseObject {
    public static String NETWORK_KEY = "network";
    public static String DATETIME_KEY = "event_date"; // UTC start time of the event, in milliseconds since the epoch
    public static String LOCATION_KEY = "location"; // venue should be attribute name here
    public static String URL_KEY = "url"; // could be an event url from meetup, facebook, eventbrite, etc
    public static String TITLE_KEY = "title";
    public static String FEATURED_KEY = "featured";
    public static String MEETUP_EVENT_ID_KEY = "meetup_event_id";
    public static String DESCRIPTION_KEY = "description";
    public static String TIMEZONE_KEY = "timezone";
    public static String SUBSCRIBE_COUNT = "subscribe_count";
    public static String HEX_COLOR = "hex_color";

    public void setHexColor(String hexColor) {
        put(HEX_COLOR, hexColor);
    }

    public String getHexColor() {
        return this.get(HEX_COLOR).toString();
    }

    public void setSubscribeCount(int count) {
        put(SUBSCRIBE_COUNT, count);
    }

    public int getSubscribeCount() {
        int count;
        Object c = this.get(SUBSCRIBE_COUNT);
        if (c != null) {
            count = Integer.parseInt(c.toString());
        } else {
            count = 0;
        }
        return count;
    }

    public void setNetwork(Network network) {
        put(NETWORK_KEY, network);
    }

    public Network getNetwork() {
        Network network= null;
        try {
            network = this.getParseObject(NETWORK_KEY).fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return network;
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

    public void setDescription(String description) {
        put(DESCRIPTION_KEY, description);
    }

    public String getDescription() {
        return this.get(DESCRIPTION_KEY).toString();
    }

    public void setTimeZone(String timeZone) {
        put(TIMEZONE_KEY, timeZone);
    }

    public String getTimezone() {
        return this.getString(TIMEZONE_KEY);
    }

    public static Event fromJSON(ModelJSONObject jsonObject) {
        String eventId = "";
        try {
            eventId = jsonObject.getString("id"); // this should never be null!
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Event event = new Event();
        // Find the event object
        ParseQuery<Event> eventParseQuery = ParseQuery.getQuery(Event.class);
        eventParseQuery.whereEqualTo(Event.MEETUP_EVENT_ID_KEY, eventId);
        try {
            if (eventParseQuery.count() > 0) {
                event = eventParseQuery.getFirst();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            event.setMeetupEventId(jsonObject.getString("id"));
            event.setDescription(jsonObject.getString("description"));
            event.setFeatured(jsonObject.getBoolean("featured"));
            event.setTitle(jsonObject.getString("name"));
            event.setLocation(jsonObject.getJSONObject("venue").getString("name"));
            event.setEventDateTime(jsonObject.getString("time"));
            event.setTimeZone(jsonObject.getString("timezone"));
            event.setUrl(jsonObject.getString("event_url"));
            String networkMeetupId = String.valueOf(jsonObject.getJSONObject("group").getInt("id"));
            Network network = Network.findByMeetupId(networkMeetupId);
            if (network != null) {
                event.setNetwork(network); // FIXME: optimization needed, don't find the network for each event
            }
            event.save();
            // save in background and the callback!
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return event;

    }

    public static ArrayList<Event> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Event> events = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject eventJSON = jsonArray.getJSONObject(i);
                ModelJSONObject modelJSONObject = new ModelJSONObject(eventJSON);
                Event event = fromJSON(modelJSONObject);
                if (event != null) {
                    events.add(event);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return events;
    }

    // FIXME: display time according to local time zone
}
