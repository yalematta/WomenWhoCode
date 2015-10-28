package com.example.womenwhocode.womenwhocode.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.womenwhocode.womenwhocode.fragments.FeaturePostsFragment;
import com.example.womenwhocode.womenwhocode.fragments.TimelineFragment;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.NetworkConnectivityReceiver;
import com.parse.CountCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by shehba.shahab on 10/18/15.
 */
public class FeatureDetailsActivity extends AppCompatActivity {

    private String feature_id;
    private ProgressBar pb;
    private RelativeLayout rlFeatures;
    private String title;
    private Feature feature;
    private TextView tvFeatureTitle;
    private TextView tvFeatureDescription;
    private TextView tvSubscriberCount;
    private Button btnSubscribe;

    ParseUser currentUser;
    Subscribe subscribe;
    int subscribeCount;

    private static String SUBSCRIBED_TEXT = "your subscribed";
    private static String SUBSCRIBE_TEXT = "subscribe!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        currentUser = ParseUser.getCurrentUser();
        feature_id = getIntent().getStringExtra("feature_id");

        setUpView();

        // Get the viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        // Set the viewpager adapter for the pager
        vpPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        // Find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);
        
        this.setTitle(title);
    }

    private void setUpView() {
        // set the progress bar
        pb = (ProgressBar) findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        // hide scroll view so the progress bar is the center of attention
        rlFeatures = (RelativeLayout) findViewById(R.id.rlFeatures);
        rlFeatures.setVisibility(ScrollView.INVISIBLE);

        tvFeatureTitle = (TextView) findViewById(R.id.tvFeatureTitle);
        tvFeatureDescription = (TextView) findViewById(R.id.tvFeatureDescription);
        tvSubscriberCount = (TextView) findViewById(R.id.tvSubscriberCount);
        btnSubscribe = (Button) findViewById(R.id.btnSubscribe);

        ParseQuery<Feature> query = ParseQuery.getQuery(Feature.class);

        if (!NetworkConnectivityReceiver.isNetworkAvailable(this)) {
            query.fromPin(LocalDataStore.FEATURES_PIN);
        }

        query.getInBackground(feature_id, new GetCallback<Feature>() {
            public void done(Feature parseFeature, ParseException e) {
                if (e == null) {
                    if (parseFeature != null) {
                        feature = parseFeature;
                        setFeatureData();
                        ParseQuery<Subscribe> subscribeParseQuery = ParseQuery.getQuery(Subscribe.class);
                        subscribeParseQuery.whereEqualTo(Subscribe.FEATURE_KEY, feature);
                        subscribeParseQuery.whereEqualTo(Subscribe.USER_KEY, currentUser);
                        subscribeParseQuery.getFirstInBackground(new GetCallback<Subscribe>() {
                            @Override
                            public void done(Subscribe sub, ParseException e) {
                                if (sub != null && sub.getSubscribed() == true) {
                                    subscribe = sub;
                                    btnSubscribe.setText(SUBSCRIBED_TEXT);
                                } else {
                                    btnSubscribe.setText(SUBSCRIBE_TEXT);
                                }

                                ParseQuery<Subscribe> subscribeParseQuery = ParseQuery.getQuery(Subscribe.class);
                                subscribeParseQuery.whereEqualTo(Subscribe.FEATURE_KEY, feature);
                                subscribeParseQuery.whereEqualTo(Subscribe.SUBSCRIBED_KEY, true);
                                subscribeParseQuery.countInBackground(new CountCallback() {
                                    @Override
                                    public void done(int i, ParseException e) {
                                        if (e == null) {
                                            subscribeCount = i;
                                        } else {
                                            subscribeCount = 0;
                                        }

                                        tvSubscriberCount.setText(String.valueOf(subscribeCount + " Subscribed"));
                                        // hide the progress bar, show the main view
                                        pb.setVisibility(ProgressBar.GONE);
                                        rlFeatures.setVisibility(ScrollView.VISIBLE);
                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(getBaseContext(), "nothing is stored locally", Toast.LENGTH_LONG).show();
                        Log.d("FEATURE_PS_NO_DATA", e.toString());
                    }
                } else {
                    Log.d("FEATURE_PS_ERROR", e.toString());
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setFeatureData() {
        title = feature.getTitle();
        String description = feature.getDescription();

        tvFeatureTitle.setText(title);
        tvFeatureDescription.setText(description);
    }

    public void onSubscribe(View view) {
        btnSubscribe = (Button) findViewById(R.id.btnSubscribe);

        if (subscribe != null) {
            if (subscribe.getSubscribed() == true) { // maybe just check against icon value
                subscribe.setSubscribed(false);
                subscribe.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        btnSubscribe.setText(SUBSCRIBE_TEXT);
                        subscribeCount --;
                        tvSubscriberCount.setText(String.valueOf(subscribeCount + " Subscribed"));
                    }
                });
            } else {
                subscribe.setSubscribed(true);
                subscribe.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        btnSubscribe.setText(SUBSCRIBED_TEXT);
                        subscribeCount ++;
                        tvSubscriberCount.setText(String.valueOf(subscribeCount + " Subscribed"));
                    }
                });
            }
        } else {
            // create subscription - stays the same
            subscribe = new Subscribe();
            subscribe.setSubscribed(true);
            subscribe.setUser(currentUser);
            subscribe.setFeature(feature);
            subscribe.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    btnSubscribe.setText(SUBSCRIBED_TEXT);
                    subscribeCount ++;
                    tvSubscriberCount.setText(String.valueOf(subscribeCount + " Subscribed"));
                }
            });
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
                FeaturePostsFragment featurePostsFragment = FeaturePostsFragment.newInstance(feature_id);
                return featurePostsFragment;
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



