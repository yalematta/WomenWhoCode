package com.example.womenwhocode.womenwhocode.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by zassmin on 10/16/15.
 */
class MeetupClient {
    private static final String MEETUP_KEY = "";

    public void getLocalEventsByGroups(String[] groupIds, AsyncHttpResponseHandler handler) {
        String url = "https://api.meetup.com/2/events.json/";

        RequestParams params = new RequestParams();
        params.put("group_id", join(groupIds));
        params.put("key", MEETUP_KEY);
        params.put("status", "upcoming");
        params.put("time", ",3m");
        params.put("limited_events", false);
        params.put("fields", "featured");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params, handler);
    }

    // FIXME: consider overriding toString() on String[] with this
    private String join(String[] groupIds) {
        String joinedString = "";
        for (int i = 0; i < groupIds.length; i++) {
            if (i == (groupIds.length - 1)) {
                joinedString += groupIds[i];
            } else {
                joinedString += groupIds[i] + ",";
            }
        }
        return joinedString;
    }
}
