package com.example.womenwhocode.womenwhocode.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity implements
        EventsFragment.OnEventItemClickListener,
        FeaturesFragment.OnFeatureItemClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private long UPDATE_INTERVAL = 60000;  /* 60 secs, change to 10800000 - 3hrs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs, change to 3600000 - 60 min */
    LocationRequest mLocationRequest;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        if (isLocationPermissionGranted()) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
        }


        // Get the viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        // Set the viewpager adapter for the pager
        vpPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        // Find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);
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

    protected void onStart() {
        super.onStart();
        // Connect the client.
        connectClient();
    }

    protected void onStop() {
        // Disconnecting the client invalidates it.
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mCurrentLocation != null) {
            Log.d("DEBUG", "current location: " + mCurrentLocation.toString());
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            // set the latlng to user profile
            updateUserProfile(latLng);
            // setting intervals on the frequency this should be updated
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(this, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("LOCATION_CNNT_FAIL", connectionResult.toString());
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    protected void connectClient() {
        // Connect the client.
        if (isGooglePlayServicesAvailable() && mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            // try to connect again...

            return false;
        }
    }

    private boolean isLocationPermissionGranted() {
        return checkCallingOrSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                checkCallingOrSelfPermission(
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
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
    public void onLocationChanged(Location location) {
        // Report to the UI that the location was updated
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void updateUserProfile(final LatLng latLng) {
        // only update if the location has changed.
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
