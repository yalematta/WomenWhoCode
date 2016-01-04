package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by zassmin on 10/15/15.
 */
@ParseClassName("PersonalizationDetails")
public class PersonalizationDetails extends ParseObject {
    private static final String USER_KEY = "user";
    private static final String ANSWERS_KEY = "answers";

    public ParseUser getUser() {
        return (ParseUser) getParseObject(USER_KEY);
    }

    public void setUser(ParseUser user) {
        put(USER_KEY, user);
    }

    // parse array data type is json array
    public String getAnswers() {
        return this.get(ANSWERS_KEY).toString();
    }

    public void setAnswers(String answers) {
        put(ANSWERS_KEY, answers);
    }
}
