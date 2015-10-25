package com.example.womenwhocode.womenwhocode.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.EventsAdapter;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Network;
import com.example.womenwhocode.womenwhocode.models.Profile;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.NetworkConnectivityReceiver;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by shehba.shahab on 10/16/15.
 */
public class EventsFragment extends Fragment {
    ArrayList<Event> events;
    EventsAdapter aEvents;
    ListView lvEvents;
    private OnEventItemClickListener listener;
    ProgressBar pb;
    ParseQuery<Profile> profileParseQuery;
    ParseQuery<Event> query;
    ParseQuery<Network> networkParseQuery;
    private static int MILE_RANGE = 25;

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
        // load queries
        profileParseQuery = ParseQuery.getQuery(Profile.class); // maybe put this is shared prefs!
        query = ParseQuery.getQuery(Event.class);
        networkParseQuery = ParseQuery.getQuery(Network.class);

        if (!NetworkConnectivityReceiver.isNetworkAvailable(getContext())) {
            query.fromPin(LocalDataStore.EVENT_PIN);
            networkParseQuery.fromPin(LocalDataStore.LOCAL_NETWORK_PIN); // what if location has changed?
            profileParseQuery.fromPin(LocalDataStore.PROFILE_PIN);
        }

        // TODO: sort events by most recent
        // TODO: reduce # of network calls for profile
        // TODO: only update location if it's changed approximately

        profileParseQuery.whereEqualTo(Profile.USER_KEY, ParseUser.getCurrentUser());
        profileParseQuery.getFirstInBackground(new GetCallback<Profile>() {

            @Override
            public void done(Profile profile, ParseException e) {
                if (profile != null) {
                    networkParseQuery.whereWithinMiles(Network.LOCATION_KEY, profile.getLocation(), MILE_RANGE);
                    query.whereMatchesQuery(Event.NETWORK_KEY, networkParseQuery);
                    query.findInBackground(new FindCallback<Event>() {
                        public void done(List<Event> eventList, ParseException e) {
                            if (eventList != null && e == null) {
                                aEvents.clear();
                                aEvents.addAll(eventList);
                                // hide progress bar, make list view appear
                                pb.setVisibility(ProgressBar.GONE);
                                lvEvents.setVisibility(ListView.VISIBLE);
                                LocalDataStore.unpinAndRepin(eventList, LocalDataStore.EVENT_PIN);
                            } else if (e != null) {
                                Log.d("PARSE_EVENTS_FAIL", "Error: " + e.getMessage());
                            } else {
                                Toast.makeText(getContext(), "nothing is stored locally", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


    }
}