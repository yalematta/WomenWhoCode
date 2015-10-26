package com.example.womenwhocode.womenwhocode.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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

import com.astuetz.PagerSlidingTabStrip;
import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.fragments.EventPostsFragment;
import com.example.womenwhocode.womenwhocode.fragments.EventsFragment;
import com.example.womenwhocode.womenwhocode.fragments.FeaturesFragment;
import com.example.womenwhocode.womenwhocode.fragments.TimelineFragment;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.LocationProvider;
import com.example.womenwhocode.womenwhocode.utils.NetworkConnectivityReceiver;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // for up button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // get event from intent
        event_id = getIntent().getStringExtra("event_id");

        // Get the viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        // Set the viewpager adapter for the pager
        vpPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        // Find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);

        setUpView();
//
//        if (savedInstanceState == null) {
//            // set up Event Post Fragment
//            EventPostsFragment eventPostsFragment = EventPostsFragment.newInstance(event_id);
//            // display fragment within activity
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.flEventContainer, eventPostsFragment);
//            ft.commit();
//        }
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
                        // hide the progress bar, show the main view
                        pb.setVisibility(ProgressBar.GONE);
                        rlEvents.setVisibility(ScrollView.VISIBLE);
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

        int subscribeCount = 0;
        try {
            subscribeCount = Subscribe.getCountFor(event);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvSubscribeCount.setText(String.valueOf(subscribeCount + " Subscribed"));
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
        ParseUser currentUser = null;
        if (Subscribe.isSubscribed(currentUser, event)) {
            Subscribe.unSubscribeUserToEvent(currentUser, event);
            btnSubscribeIcon.setText("subscribe");
        } else {
            Subscribe.subscribeUserToEvent(currentUser, event);
            btnSubscribeIcon.setText("you're subscribed");
        }
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        private final String[] tabTitles = { "posts", "chatter" };

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // The order and creation fo fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                EventPostsFragment eventPostsFragment = EventPostsFragment.newInstance(event_id);
                return eventPostsFragment;
            } else if (position == 1) {
                return new TimelineFragment();
            } else return null;
        }

        // Return the tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        // How many fragments there are to swipe between
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
