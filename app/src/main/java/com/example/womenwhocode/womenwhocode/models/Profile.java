package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by zassmin on 10/16/15.
 */
@ParseClassName("Profile")
public class Profile extends ParseObject {
    public static String FULL_NAME_KEY = "full_name";
    public static String JOB_TITLE_KEY = "job_title";
    public static String IMAGE_URL_KEY = "image_url";
    public static String NETWORK_KEY = "network";
    public static String LOCATION_KEY = "location";
    public static String USER_KEY = "user";
    public static String ABOUT_YEARS_EXPERIENCE_KEY = "about_years_experience";

    public void setFullName(String fullName) {
        put(FULL_NAME_KEY, fullName);
    }

    public String getFullName() {
        return this.get(FULL_NAME_KEY).toString();
    }

    public void setJobTitle(String jobTitle) {
        put(JOB_TITLE_KEY, jobTitle);
    }

    public String getJobTitle() {
        return this.get(JOB_TITLE_KEY).toString();
    }

    public void setImageUrl(String imageUrl) {
        put(IMAGE_URL_KEY, imageUrl);
    }

    public String getImageUrl() {
        return this.get(IMAGE_URL_KEY).toString();
    }

    public void setNetwork(Network network) {
        put(NETWORK_KEY, network);
    }

    public Network getNetwork() {
        return (Network) getParseObject(NETWORK_KEY);
    }

    public void setUser(User user) {
        put(USER_KEY, user);
    }

    public User getUser() {
        return (User) getParseObject(USER_KEY);
    }

    public void setLocation(ParseGeoPoint geoPoint) {
        put(LOCATION_KEY, geoPoint);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint(LOCATION_KEY);
    }

    // NOTE: this a stretch goal getter and setter
    public void setYearsExperience(String range) {
        put(ABOUT_YEARS_EXPERIENCE_KEY, range);
    }

    public String getAboutYearsExperience() {
        return this.get(ABOUT_YEARS_EXPERIENCE_KEY).toString();
    }
}
