package com.example.womenwhocode.womenwhocode.models;

import com.example.womenwhocode.womenwhocode.utils.ModelJSONObject;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    public static String RSVP_COUNT_KEY = "rsvp_count";
    public static String RSVP_LIMIT_KEY = "rsvp_limit";
    public static String AWESOME_COUNT_KEY = "awesome_count";
    public static String DESCRIPTION_KEY = "description";
    public static String TIMEZONE_KEY = "timezone";

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

    public void setRsvpLimit(int rsvpLimit) {
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
            event.setLocation(jsonObject.getString("venue"));
            event.setEventDateTime(jsonObject.getString("time"));
            event.setTimeZone(jsonObject.getString("timezone"));
            event.setUrl(jsonObject.getString("event_url"));
            event.setRsvpLimit(jsonObject.getInt("rsvp_limit"));
            event.setRsvpCount(jsonObject.getInt("yes_rsvp_count"));
            String networkMeetupId = String.valueOf(jsonObject.getJSONObject("group").getInt("id"));
            event.setNetwork(Network.findByMeetupId(networkMeetupId)); // FIXME: optimization needed, don't find the network for each event
            event.save();
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
}
