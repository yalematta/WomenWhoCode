package com.example.womenwhocode.womenwhocode.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.fragments.EventsFragment;
import com.example.womenwhocode.womenwhocode.fragments.FeaturesFragment;
import com.example.womenwhocode.womenwhocode.fragments.TimelineFragment;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.models.Profile;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.LocationProvider;
import com.google.android.gms.maps.model.LatLng;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity implements
        EventsFragment.OnEventItemClickListener,
        FeaturesFragment.OnFeatureItemClickListener,
        LocationProvider.LocationCallback {

    private LocationProvider mLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // set up location
        mLocationProvider = new LocationProvider(this, this);

        // Get the viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        // Set the viewpager adapter for the pager
        vpPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        // Find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);

        ParseUser.logInInBackground("zzzzdemo", "password", new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationProvider.connectClient();
    }

    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationProvider.disconnect();
        super.onStop();
    }

    /*
    * Handle results returned to the FragmentActivity by Google Play services
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Decide what to do based on the original request code
        switch (requestCode) {

            case LocationProvider.CONNECTION_FAILURE_RESOLUTION_REQUEST:
			/*
			 * If the result code is Activity.RESULT_OK, try to connect again
			 */
                switch (resultCode) {
                case Activity.RESULT_OK:
                    mLocationProvider.connectClient();
                    break;
                }

        }
    }

    @Override
    public void onEventClickListener(Event event) {
        Intent i = new Intent(TimelineActivity.this, EventDetailsActivity.class);
        i.putExtra("event_id", event.getObjectId());
        startActivity(i);
    }

    @Override
    public void onFeatureClickListener(Feature feature) {
        Intent i = new Intent(TimelineActivity.this, FeatureDetailActivity.class);
        i.putExtra("feature_id", feature.getObjectId());
        startActivity(i);
    }

    @Override
    public void handleNewLocation(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        updateUserProfile(latLng);
    }

    public void updateUserProfile(final LatLng latLng) {
        // FIXME: only update if the location has changed.
        ParseQuery<Profile> query = ParseQuery.getQuery(Profile.class);
        query.whereEqualTo(Profile.USER_KEY, ParseUser.getCurrentUser());
        query.getFirstInBackground(new GetCallback<Profile>() {
            @Override
            public void done(Profile profile, ParseException e) {
                if (e == null && profile != null) {
                    // build object
                    ParseGeoPoint pLocation = new ParseGeoPoint();
                    pLocation.setLatitude(latLng.latitude);
                    pLocation.setLongitude(latLng.longitude);
                    profile.setLocation(pLocation);
                    // save to parse and pin locally for offline mode
                    final ArrayList<Profile> profiles = new ArrayList<>();
                    profiles.add(profile);
                    profile.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d("LOCATION_SAVED", "on profile");
                                LocalDataStore.unpinAndRepin(profiles, LocalDataStore.PROFILE_PIN);
                            } else {
                                Log.d("LOCATION_NOT_SAVED", e.toString());
                            }
                        }
                    });
                } else if (profile == null) {
                    Toast.makeText(getBaseContext(), "profile null", Toast.LENGTH_LONG).show();
                    Log.d("PROFILE_NULL", "");
                } else {
                    Log.d("PROFILE_ERROR", e.toString());
                }
            }
        });
    }

    // Return the order of the fragments in the view pager
    public class PagerAdapter extends FragmentPagerAdapter {
        private final String[] tabTitles = {(String) getResources().getText(R.string.title_fragment_timeline), (String) getResources().getText(R.string.title_fragment_topics), (String) getResources().getText(R.string.title_fragment_events)};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // The order and creation fo fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new TimelineFragment();
            } else if (position == 1) {
                return new FeaturesFragment();
            } else if (position == 2) {
                return new EventsFragment();
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
