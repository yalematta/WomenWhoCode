package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

/**
 * Created by zassmin on 10/15/15.
 */
@ParseClassName("PersonalizationDetail")
public class PersonalizationDetail extends ParseObject {
    private static String USER_KEY = "user";
    private static String ANSWERS_KEY = "answers";

    public void setAnswers(JSONArray answers) {
        put(ANSWERS_KEY, answers);
    }

    public void setUser(User user) {
        put(USER_KEY, user);
    }

    public User getUser()  {
        return (User) getParseObject(USER_KEY);
    }

    // parse array data type is json array
    public JSONArray getAnswers() {
        return this.getJSONArray(ANSWERS_KEY);
    }
}
