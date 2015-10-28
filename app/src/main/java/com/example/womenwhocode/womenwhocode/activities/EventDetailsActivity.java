package com.example.womenwhocode.womenwhocode.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.fragments.EventPostsFragment;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.NetworkConnectivityReceiver;
import com.parse.CountCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EventDetailsActivity extends AppCompatActivity {
    TextView tvEventTitle;
    TextView tvEventDate;
    TextView tvEventVenue;
    TextView tvEventUrl;
    TextView tvSubscribeCount;
    Button btnSubscribeIcon;
    Event event;
    ProgressBar pb;
    RelativeLayout rlEvents;
    String event_id;
    ParseUser currentUser;
    Subscribe subscribe;
    int subscribeCount;

    private static String SUBSCRIBED_TEXT = "your subscribed";
    private static String SUBSCRIBE_TEXT = "subscribe!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // for up button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        currentUser = ParseUser.getCurrentUser();

        setUpView();

        if (savedInstanceState == null) {
            // set up Event Post Fragment
            EventPostsFragment eventPostsFragment = EventPostsFragment.newInstance(event_id);
            // display fragment within activity
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flEventContainer, eventPostsFragment);
            ft.commit();
        }
    }

    private void setUpView() {
        // set the progress bar
        pb = (ProgressBar) findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        // hide scroll view so the progress bar is the center of attention
        rlEvents = (RelativeLayout) findViewById(R.id.rlEvents);
        rlEvents.setVisibility(ScrollView.INVISIBLE);

        // look up views
        tvEventTitle = (TextView) findViewById(R.id.tvEventTitle);
        tvEventDate = (TextView) findViewById(R.id.tvEventDate);
        // TextView tvEventTime = (TextView) findViewById(R.id.tvEventTime);
        tvEventVenue = (TextView) findViewById(R.id.tvEventVenue);
        tvEventUrl = (TextView) findViewById(R.id.tvEventUrl);
        tvSubscribeCount = (TextView) findViewById(R.id.tvSubscribeCount);
        btnSubscribeIcon = (Button) findViewById(R.id.btnSubscribeIcon);

        // get event from intent
        event_id = getIntent().getStringExtra("event_id");

        // query parse
        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);

        if (!NetworkConnectivityReceiver.isNetworkAvailable(this)) {
            query.fromPin(LocalDataStore.EVENT_PIN);
        }

        // Execute the query to find the object with ID
        query.getInBackground(event_id, new GetCallback<Event>() {
            public void done(Event parseEvent, ParseException e) {
                if (e == null) {
                    if (parseEvent != null) {
                        event = parseEvent;
                        setEventData();
                        ParseQuery<Subscribe> subscribeParseQuery = ParseQuery.getQuery(Subscribe.class);
                        subscribeParseQuery.whereEqualTo(Subscribe.EVENT_KEY, event);
                        subscribeParseQuery.whereEqualTo(Subscribe.USER_KEY, currentUser);
                        subscribeParseQuery.getFirstInBackground(new GetCallback<Subscribe>() {
                            @Override
                            public void done(Subscribe sub, ParseException e) {
                                if (sub != null && sub.getSubscribed() == true) {
                                    subscribe = sub;
                                    btnSubscribeIcon.setText(SUBSCRIBED_TEXT);
                                } else {
                                    btnSubscribeIcon.setText(SUBSCRIBE_TEXT);
                                }

                                ParseQuery<Subscribe> subscribeParseQuery = ParseQuery.getQuery(Subscribe.class);
                                subscribeParseQuery.whereEqualTo(Subscribe.EVENT_KEY, event);
                                subscribeParseQuery.whereEqualTo(Subscribe.SUBSCRIBED_KEY, true);
                                subscribeParseQuery.countInBackground(new CountCallback() {
                                    @Override
                                    public void done(int i, ParseException e) {
                                        if (e == null) {
                                            subscribeCount = i;
                                        } else {
                                            subscribeCount = 0;
                                        }

                                        tvSubscribeCount.setText(String.valueOf(subscribeCount + " Subscribed"));
                                        // hide the progress bar, show the main view
                                        pb.setVisibility(ProgressBar.GONE);
                                        rlEvents.setVisibility(ScrollView.VISIBLE);
                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(getBaseContext(), "nothing is stored locally", Toast.LENGTH_LONG).show();
                        Log.d("EVENT_PS_NO_DATA", e.toString());
                    }
                } else {
                    Log.d("EVENT_PS_ERROR", e.toString());
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setEventData() {
        // setup views
        tvEventTitle.setText(event.getTitle());
        tvEventDate.setText(event.getEventDateTime());
        // tvEventTime.setText(event.getDateTime(event.getEventDateTime()));
        tvEventVenue.setText(event.getLocation());
        tvEventUrl.setText(event.getUrl());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void onSubscribe(View view) {
        btnSubscribeIcon = (Button) view.findViewById(R.id.btnSubscribeIcon);
        // could make a parse user for fun right now? -> try to do it without a parse user
        if (subscribe != null) {
            if (subscribe.getSubscribed() == true) { // maybe just check against icon value
                subscribe.setSubscribed(false);
                subscribe.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        btnSubscribeIcon.setText(SUBSCRIBE_TEXT);
                        subscribeCount --;
                        tvSubscribeCount.setText(String.valueOf(subscribeCount + " Subscribed"));
                    }
                });
            } else {
                subscribe.setSubscribed(true);
                subscribe.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        btnSubscribeIcon.setText(SUBSCRIBED_TEXT);
                        subscribeCount ++;
                        tvSubscribeCount.setText(String.valueOf(subscribeCount + " Subscribed"));
                    }
                });
            }
        } else {
            // create subscription - stays the same
            subscribe = new Subscribe();
            subscribe.setSubscribed(true);
            subscribe.setUser(currentUser);
            subscribe.setEvent(event);
            subscribe.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    btnSubscribeIcon.setText(SUBSCRIBED_TEXT);
                    subscribeCount ++;
                    tvSubscribeCount.setText(String.valueOf(subscribeCount + " Subscribed"));
                }
            });
        }
    }
}
