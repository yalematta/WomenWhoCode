package com.example.womenwhocode.womenwhocode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.network.MeetupClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by shehba.shahab on 10/16/15.
 */
public class EventsFragment extends Fragment {
    MeetupClient meetupClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateEvents(); // FIXME: look into making this call happen on a daily basis and/or when there is a change in the user's location

    }

    private void populateEvents() {
        meetupClient = new MeetupClient();
        /*
        * TODO: will need to know the user's location
        * with something like below:
        * ParseGeoPoint parseGeoPoint = new ParseGeoPoint(37.7016537, -121.9014677);
        * ParseQuery<Network> networkParseQuery = ParseQuery.getQuery(Network.class);
        * networkParseQuery.whereWithinMiles(Network.LOCATION_KEY, parseGeoPoint, 25);
        */

        // FIXME: when ^^^ is added dynamically add in value below!
        String[] groupIds = new String[]{"11940602"};

        meetupClient.getLocalEventsByGroups(groupIds, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Event.fromJSONArray(response.getJSONArray("results"));
                    Toast.makeText(getContext(), "success!" + response.getJSONArray("results").length() + " result " + response.getJSONArray("results").getJSONObject(0).getString("name"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    Log.i("MEETUP_EVENTS_FAILURE", errorResponse.toString());
                } else {
                    Log.d("MEETUP_EVENTS_FAILURE", "null error");
                }
            }
        });
    }

    // Inflate the fragment layout we defined above for this fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        return view;
    }
}