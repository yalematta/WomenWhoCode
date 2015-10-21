package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by zassmin on 10/16/15.
 * To create a user, we are currently using the ParseUser, which includes a email, username,
 * password. This model is meant to store all the other data for a user we need that isn't
 * bound by the ParseUser implementation details.
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
    public static String GITHUB_ACCESS_TOKEN = "github_access_token";
    public static String GITHUB_ID = "github_id";

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

    public void setUser(ParseUser user) {
        put(USER_KEY, user);
    }

    public ParseUser getUser() {
        return (ParseUser) getParseObject(USER_KEY);
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

    public void setGithubAccessToken(String token) {
        this.put(GITHUB_ACCESS_TOKEN, token);
    }

    public String getGithubAccessToken() {
        return this.get(GITHUB_ACCESS_TOKEN).toString();
    }

    // for user's github id after github oauth call GET https://api.github.com/user
    public void setGithubId(long githubId) {
        this.put(GITHUB_ID, githubId);
    }

    public long getGithubId() {
        return (long) this.get(GITHUB_ID);
    }
}
