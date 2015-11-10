package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

/**
 * Created by zassmin on 10/15/15.
 */
@ParseClassName("PersonalizationDetails")
public class PersonalizationDetails extends ParseObject {
    public static final String USER_KEY = "user";
    public static final String ANSWERS_KEY = "answers";

    public void setAnswers(String answers) {
        put(ANSWERS_KEY, answers);
    }

    public void setUser(ParseUser user) {
        put(USER_KEY, user);
    }

    public ParseUser getUser()  {
        return (ParseUser) getParseObject(USER_KEY);
    }

    // parse array data type is json array
    public String getAnswers() {
        return this.get(ANSWERS_KEY).toString();
    }
}
