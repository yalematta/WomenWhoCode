package com.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
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
    public static final String USER_KEY = "user";
    private static final String FULL_NAME_KEY = "full_name";
    private static final String JOB_TITLE_KEY = "job_title";
    private static final String NETWORK_KEY = "network";
    private static final String LOCATION_KEY = "location";
    private static final String ABOUT_YEARS_EXPERIENCE_KEY = "about_years_experience";
    private static final String GITHUB_ACCESS_TOKEN = "github_access_token";
    private static final String GITHUB_ID = "github_id";
    private static final String THEME_KEY = "theme_type";
    public static String IMAGE_URL_KEY = "image_url";

    public String getFullName() {
        String fn = "";
        try {
            fn = this.get(FULL_NAME_KEY).toString();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
        return fn;
    }

    public void setFullName(String fullName) {
        put(FULL_NAME_KEY, fullName);
    }

    public String getJobTitle() {
        String fn = "";
        try {
            fn = this.get(JOB_TITLE_KEY).toString();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
        return fn;
    }

    public void setJobTitle(String jobTitle) {
        put(JOB_TITLE_KEY, jobTitle);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }

    public Network getNetwork() {
        return (Network) getParseObject(NETWORK_KEY);
    }

    public void setNetwork(String network) {
        put(NETWORK_KEY, network);
    }

    public ParseUser getUser() {
        return (ParseUser) getParseObject(USER_KEY);
    }

    public void setUser(ParseUser user) {
        put(USER_KEY, user);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint(LOCATION_KEY);
    }

    public void setLocation(ParseGeoPoint geoPoint) {
        put(LOCATION_KEY, geoPoint);
    }

    // NOTE: this a stretch goal getter and setter
    public void setYearsExperience(String range) {
        put(ABOUT_YEARS_EXPERIENCE_KEY, range);
    }

    public String getAboutYearsExperience() {
        return this.get(ABOUT_YEARS_EXPERIENCE_KEY).toString();
    }

    public String getGithubAccessToken() {
        return this.get(GITHUB_ACCESS_TOKEN).toString();
    }

    public void setGithubAccessToken(String token) {
        this.put(GITHUB_ACCESS_TOKEN, token);
    }

    public long getGithubId() {
        return (long) this.get(GITHUB_ID);
    }

    // for user's github id after github oauth call GET https://api.github.com/user
    public void setGithubId(long githubId) {
        this.put(GITHUB_ID, githubId);
    }

    public int getTheme() {
        return (int) this.getNumber(THEME_KEY);
    }

    public void setTheme(int type) {
        this.put(THEME_KEY, type);
    }
}
