package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by zassmin on 11/11/15.
 */
@ParseClassName("Notification")
public class Notification extends ParseObject {
    private static final String TYPE_KEY = "type";
    private static final String MESSAGE_KEY = "message";
    private static final String PHOTO_KEY = "photo_file";

    public ParseFile getPhotoFile() {
        return getParseFile(PHOTO_KEY);
    }

    public void setPhotoFile(ParseFile file) {
        put(PHOTO_KEY, file);
    }

    public String getMessage() {
        return getString(MESSAGE_KEY);
    }

    public void setMessage(String message) {
        put(MESSAGE_KEY, message);
    }

    public void setType(String userId) {
        put(TYPE_KEY, userId);
    }

    public String getType() {
        return getString(TYPE_KEY);
    }
}
