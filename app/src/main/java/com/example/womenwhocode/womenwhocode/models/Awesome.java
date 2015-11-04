package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by shehba.shahab on 10/24/15.
 */

@ParseClassName("Awesome")
public class Awesome extends ParseObject {
    public static final String USER_KEY = "user";
    public static final String POST_KEY = "post";
    public static final String AWESOMED_KEY = "awesomed";

    private static ParseQuery<Awesome> query;

    public static ParseQuery<Awesome> getQuery() {
        return ParseQuery.getQuery(Awesome.class);
    }

    public boolean getAwesomed() {
        return getBoolean(AWESOMED_KEY);
    }

    public void setAwesomed(boolean awesomed) {
        put(AWESOMED_KEY, awesomed);
    }

    public Post getPost() {
        return (Post) getParseObject(POST_KEY);
    }

    public void setPost(Post post) {
        put(POST_KEY, post);
    }

    public ParseUser getUser() {
        return (ParseUser) getParseObject(USER_KEY);
    }

    public void setUser(ParseUser user) {
        put(USER_KEY, user);
    }

}
