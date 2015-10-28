package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by zassmin on 10/16/15.
 */
@ParseClassName("Feature")
public class Feature extends ParseObject {
    public static final String AUTO_SUBSCRIBE_KEY = "auto_subscribe";
    public static final String IMAGE_URL_KEY = "image_url";
    public static final String TITLE_KEY = "title";
    public static final String NETWORK_KEY = "network";
    public static final String DESCRIPTION_KEY = "description";
    public static final String AWESOME_COUNT_KEY = "awesome_count";

    public void setAutoSubscribe(boolean autoSubscribe) {
        put(AUTO_SUBSCRIBE_KEY, autoSubscribe);
    }

    public boolean getAutoSubscribe() {
        return this.getBoolean(AUTO_SUBSCRIBE_KEY);
    }

    public void setDescription(String description) {
        put(DESCRIPTION_KEY, description);
    }

    public String getDescription() {
        return this.get(DESCRIPTION_KEY).toString();
    }

    public void setTitle(String title) {
        put(TITLE_KEY, title);
    }

    public String getTitle() {
        return this.get(TITLE_KEY).toString();
    }

    public void setNetwork(Network network) {
        put(NETWORK_KEY, network);
    }

    public Network getNetwork() {
        return (Network) getParseObject(NETWORK_KEY);
    }

    public void setImageUrl(String imageUrl) {
        put(IMAGE_URL_KEY, imageUrl);
    }

    public String getImageUrl() {
        return this.get(IMAGE_URL_KEY).toString();
    }

    // awesomeCount is for our stretch goals
    public void setAwesomeCount(int awesomeCount) {
        put(AWESOME_COUNT_KEY, awesomeCount);
    }

    public int getAwesomeCount() {
        return this.getInt(AWESOME_COUNT_KEY);
    }
}
