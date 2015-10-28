package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by shehba.shahab on 10/24/15.
 */

@ParseClassName("Awesome")
public class Awesome extends ParseObject {
    private static String USER_KEY = "user";
    private static String POST_KEY = "post";
    private static String AWESOMED_KEY = "awesomed";

    private static ParseQuery<Awesome> query;


    private static ParseQuery<Awesome> getQuery() {
        return ParseQuery.getQuery(Awesome.class);
    }

    public static boolean isAwesomeForUser(ParseUser user, Post post) {
        query = Awesome.getQuery();
        query.whereEqualTo(USER_KEY, user);
        query.whereEqualTo(POST_KEY, post);
        Integer count = query.countInBackground().getResult();
        return count != null;
    }

    // Make this work async
    public static int getAwesomeCountFor(Post post) {
        query = Awesome.getQuery();
        query.whereEqualTo(POST_KEY, post);
        //Integer count = query.countInBackground().getResult();
        Integer count = null;
        try {
            count = query.count();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (count == null) {
            return 0;
        }
        return count;
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

    public static void awesomePostForCurrentUser(final Post post) {
        final ParseObject newEntry = new ParseObject("Awesome");
        final ParseUser user = ParseUser.getCurrentUser();
        newEntry.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                newEntry.put(USER_KEY, user);
                newEntry.put(POST_KEY, post);
                newEntry.put(AWESOMED_KEY, true);
                newEntry.saveInBackground();
            }
        });
    }

    public static void unAwesomePostForCurrentUser(final Post post) {
        final ParseObject newEntry = new ParseObject("Awesome");
        final ParseUser user = ParseUser.getCurrentUser();
        newEntry.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                newEntry.put(USER_KEY, user);
                newEntry.put(POST_KEY, post);
                newEntry.put(AWESOMED_KEY, false);
                newEntry.saveInBackground();
            }
        });
    }
}
