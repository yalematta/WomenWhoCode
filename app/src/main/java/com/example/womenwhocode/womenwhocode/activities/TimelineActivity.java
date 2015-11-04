package com.example.womenwhocode.womenwhocode.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.fragments.EventsFragment;
import com.example.womenwhocode.womenwhocode.fragments.FeaturesFragment;
import com.example.womenwhocode.womenwhocode.fragments.RecommendFeatureDialog.RecommendFeatureDialogListener;
import com.example.womenwhocode.womenwhocode.fragments.TimelineFragment;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.models.Profile;
import com.example.womenwhocode.womenwhocode.utils.CircleTransform;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.LocationProvider;
import com.google.android.gms.maps.model.LatLng;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TimelineActivity extends AppCompatActivity implements
        EventsFragment.OnEventItemClickListener,
        FeaturesFragment.OnFeatureItemClickListener,
        LocationProvider.LocationCallback,
        RecommendFeatureDialogListener {

    public final static String SELECTED_TAB_EXTRA_KEY = "selectedTabIndex";
    public final static int TIMELINE_TAB = 0;
    private final static int TOPICS_TAB = 1;
    private final static int EVENTS_TAB = 2;
    public Profile profile;
    private LocationProvider mLocationProvider;
    private ViewPager vpPager;
    private View parentLayout;

    private DrawerLayout mDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        parentLayout = findViewById(R.id.timeline_activity_view);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // set tool bar to replace actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // hide the action bar title to only so toolbar title

        //for drawer view
        ParseUser currentUser = ParseUser.getCurrentUser();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_list_icon);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);

        // Inflate the header view at runtime
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);

        // We can now look up items within the header if needed
        final ImageView ivHeaderPhoto = (ImageView) headerLayout.findViewById(R.id.ivPhoto);
        final TextView tvFullName = (TextView) headerLayout.findViewById(R.id.tvFullName);
        final TextView tvJobTitle = (TextView) headerLayout.findViewById(R.id.tvJobTitle);

        ParseQuery<Profile> parseQuery = ParseQuery.getQuery(Profile.class);
        parseQuery.whereEqualTo(Profile.USER_KEY, currentUser);
        parseQuery.getFirstInBackground(new GetCallback<Profile>() {
            @Override
            public void done(Profile profile, ParseException e) {
                if (e == null) {
                    if (profile != null) {
                        if (profile.getPhotoFile() != null) {
                            Picasso.with(getApplicationContext())
                                    .load(profile.getPhotoFile().getUrl())
                                    .transform(new CircleTransform())
                                    .resize(50, 50)
                                    .centerCrop()
                                    .into(ivHeaderPhoto);
                        }
                        tvFullName.setText(profile.getFullName());
                        tvJobTitle.setText(profile.getJobTitle());
                    }
                }
            }
        });

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.app_name);

        // set up location
        mLocationProvider = new LocationProvider(this, this);

        // Get the viewpager
        vpPager = (ViewPager) findViewById(R.id.viewpager);

        // Set the viewpager adapter for the pager
        vpPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        // Find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf"), Typeface.NORMAL);

        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);

        setSelectedTab();
    }

    private void setSelectedTab() {
        // Fetch the selected tab index with default
        int selectedTabIndex = getIntent().getIntExtra(SELECTED_TAB_EXTRA_KEY, TIMELINE_TAB);
        // Switch to page based on index
        vpPager.setCurrentItem(selectedTabIndex);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            mDrawer.openDrawer(GravityCompat.START);
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
        Intent i = new Intent(this, EventDetailsActivity.class);
        i.putExtra("event_id", event.getObjectId());
        i.putExtra(SELECTED_TAB_EXTRA_KEY, EVENTS_TAB);
        startActivity(i);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out); // TODO: Replace with Shared Element Activity Transition
    }

    @Override
    public void onFeatureClickListener(Feature feature) {
        Intent i = new Intent(TimelineActivity.this, FeatureDetailsActivity.class);
        i.putExtra("feature_id", feature.getObjectId());
        i.putExtra(SELECTED_TAB_EXTRA_KEY, TOPICS_TAB);
        startActivity(i);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out); // TODO: Replace with Shared Element Activity Transition
    }

    @Override
    public void handleNewLocation(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        updateUserProfile(latLng);
    }

    private void updateUserProfile(final LatLng latLng) {
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

    @Override
    public void onFinishEditDialog(String inputText) {
        if (!TextUtils.isEmpty(inputText)) {
            Snackbar.make(
                    parentLayout, "We'll review and get back to you soon enough!", Snackbar.LENGTH_SHORT)
                    .show();
        }
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
