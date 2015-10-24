package com.example.womenwhocode.womenwhocode.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.EventsAdapter;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.network.MeetupClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by shehba.shahab on 10/16/15.
 */
public class EventsFragment extends Fragment {
    MeetupClient meetupClient;
    ArrayList<Event> events;
    EventsAdapter aEvents;
    ListView lvEvents;
    private OnEventItemClickListener listener;
    ProgressBar pb;

    public interface OnEventItemClickListener {
        public void onEventClickListener(Event event);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events = new ArrayList<>();
        aEvents = new EventsAdapter(getActivity(), events);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        lvEvents = (ListView) view.findViewById(R.id.lvEvents);
        // hide the listview until data is loaded
        lvEvents.setVisibility(ListView.INVISIBLE);

        // load progress bar
        pb = (ProgressBar) view.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        lvEvents.setAdapter(aEvents);

        populateEvents(); // FIXME: look into making this call happen on a daily basis and/or when there is a change in the user's location

        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = aEvents.getItem(position);
                listener.onEventClickListener(event);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnEventItemClickListener) {
            listener = (OnEventItemClickListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement EventsFragment.OnEventItemClickListener");
        }
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
                    aEvents.addAll(Event.fromJSONArray(response.getJSONArray("results")));
                    // remove progress bar
                    pb.setVisibility(ProgressBar.GONE);
                    lvEvents.setVisibility(ListView.VISIBLE);
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

}