package com.example.womenwhocode.womenwhocode.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.PostsAdapter;
import com.example.womenwhocode.womenwhocode.fragments.ChatFragment;
import com.example.womenwhocode.womenwhocode.fragments.FeatureChatFragment;
import com.example.womenwhocode.womenwhocode.fragments.FeaturePostsFragment;
import com.example.womenwhocode.womenwhocode.fragments.PostsListFragment;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.example.womenwhocode.womenwhocode.utils.CircleTransform;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.NetworkConnectivityReceiver;
import com.example.womenwhocode.womenwhocode.widgets.CustomTabStrip;
import com.example.womenwhocode.womenwhocode.widgets.CustomViewPager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by shehba.shahab on 10/18/15.
 */
public class FeatureDetailsActivity extends AppCompatActivity implements PostsListFragment.OnFeatureScroll {

    private String feature_id;
    private ProgressBar pb;
    private RelativeLayout rlFeatures;
    private Feature feature;
    private TextView tvFeatureTitle;
    private TextView tvFeatureDescription;
    private TextView tvSubscriberCount;
    private Button btnSubscribe;
    private Toolbar toolbar;
    private TextView tvToolbarTitle;
    private ParseUser currentUser;
    private Subscribe subscribe;
    private int subscribeCount;
    private ImageView ivFeatureImage;
    private CustomViewPager vpPager;
    private CustomTabStrip tabStrip;
    private static final String SUBSCRIBED_TEXT = "unfollow";
    private static final String SUBSCRIBE_TEXT = "follow";
    private static final String SUBSCRIBERS_TEXT = " followers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_details);

        // set tool bar to replace actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false); // hide the action bar title to only so toolbar title

        currentUser = ParseUser.getCurrentUser();
        feature_id = getIntent().getStringExtra("feature_id");

        setUpView();
        setUpViewPager();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        // get fragment position from parent class
        int fragmentPosition = getIntent().getIntExtra(TimelineActivity.SELECTED_TAB_EXTRA_KEY, 0);
        // send position back to parent
        Intent newIntent = new Intent(this, TimelineActivity.class);
        newIntent.putExtra(TimelineActivity.SELECTED_TAB_EXTRA_KEY, fragmentPosition);
        // Return the created intent as the "up" activity
        return newIntent;

    }

    private void setUpView() {
        // set the progress bar
        pb = (ProgressBar) findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        // hide scroll view so the progress bar is the center of attention
        rlFeatures = (RelativeLayout) findViewById(R.id.rlFeatures); // TODO: animate!
//        rlFeatures.setVisibility(ScrollView.INVISIBLE);

        tvToolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        tvFeatureTitle = (TextView) findViewById(R.id.tvFeatureTitle);
        tvFeatureDescription = (TextView) findViewById(R.id.tvFeatureDescription);
        tvSubscriberCount = (TextView) findViewById(R.id.tvSubscriberCount);
        btnSubscribe = (Button) findViewById(R.id.btnSubscribe);
        ivFeatureImage = (ImageView) findViewById(R.id.ivFeatureImage);

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

                        // set up count
                        subscribeCount = feature.getSubscribeCount();
                        tvSubscriberCount.setText(String.valueOf(subscribeCount + SUBSCRIBERS_TEXT));

                        ParseQuery<Subscribe> subscribeParseQuery = ParseQuery.getQuery(Subscribe.class);
                        subscribeParseQuery.whereEqualTo(Subscribe.FEATURE_KEY, feature);
                        subscribeParseQuery.whereEqualTo(Subscribe.USER_KEY, currentUser);
                        subscribeParseQuery.getFirstInBackground(new GetCallback<Subscribe>() {
                            @Override
                            public void done(Subscribe sub, ParseException e) {
                                if (sub != null) {
                                    subscribe = sub;
                                    if (sub.getSubscribed() == true) {
                                        displayChat();
                                        btnSubscribe.setText(SUBSCRIBED_TEXT);
                                    } else {
                                        hideChat();
                                        btnSubscribe.setText(SUBSCRIBE_TEXT);
                                    }
                                } else {
                                    hideChat();
                                    btnSubscribe.setText(SUBSCRIBE_TEXT);
                                }

                                // hide the progress bar, show the main view
                                pb.setVisibility(ProgressBar.GONE);
//                                rlFeatures.setVisibility(RelativeLayout.VISIBLE);
                            }
                        });
                    } else {
                        Log.d("FEATURE_PS_NO_DATA", e.toString());
                    }
                } else {
                    Log.d("FEATURE_PS_ERROR", e.toString());
                }
            }
        });
    }

    private void setFeatureData() {
        String title = feature.getTitle();

        String description = feature.getDescription();

        int color = Color.parseColor(String.valueOf(feature.getHexColor()));
        rlFeatures.setBackgroundColor(color);

        Picasso.with(this)
                .load(feature.getImageUrl())
                .transform(new CircleTransform())
                .resize(50, 50)
                .centerCrop()
                .into(ivFeatureImage);

        tvFeatureTitle.setText(title);
        tvToolbarTitle.setText(title);
        tvFeatureDescription.setText(description);
    }

    public void onSubscribe(View view) {
        btnSubscribe = (Button) findViewById(R.id.btnSubscribe);

        if (subscribe != null) {
            if (subscribe.getSubscribed() == true) { // maybe just check against icon value
                subscribe.setSubscribed(false);
                hideChat();

                // decrement counter
                subscribeCount = feature.getSubscribeCount() - 1;
                feature.setSubscribeCount(subscribeCount);
                feature.saveInBackground();
                tvSubscriberCount.setText(String.valueOf(subscribeCount + SUBSCRIBERS_TEXT));

                subscribe.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        btnSubscribe.setText(SUBSCRIBE_TEXT);
                    }
                });
            } else {
                subscribe.setSubscribed(true);
                displayChat();

                // increment counter
                subscribeCount = feature.getSubscribeCount() + 1;
                feature.setSubscribeCount(subscribeCount);
                feature.saveInBackground();
                tvSubscriberCount.setText(String.valueOf(subscribeCount + SUBSCRIBERS_TEXT));

                subscribe.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        btnSubscribe.setText(SUBSCRIBED_TEXT);
                    }
                });
            }
        } else {
            // create subscription - stays the same
            subscribe = new Subscribe();
            subscribe.setSubscribed(true);
            subscribe.setUser(currentUser);
            subscribe.setFeature(feature);
            displayChat();

            // increment counter
            subscribeCount = feature.getSubscribeCount() + 1;
            feature.setSubscribeCount(subscribeCount);
            feature.saveInBackground();
            tvSubscriberCount.setText(String.valueOf(subscribeCount + SUBSCRIBERS_TEXT));

            subscribe.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    btnSubscribe.setText(SUBSCRIBED_TEXT);
                }
            });
        }
    }

    @Override
    public void onFeatureScrollListner(int itemPosition) {
        // listener place holder
//            rlFeatures.setVisibility(RelativeLayout.GONE);
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        private final String[] tabTitles = {"posts", "chat"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // The order and creation fo fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return FeaturePostsFragment.newInstance(feature_id);
            } else if (position == 1) {
                return FeatureChatFragment.newInstance(feature_id);
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

    private void hideChat() {
        vpPager.setPagingEnabled(false);
        tabStrip.setDisabled(true);
        ViewGroup view = (ViewGroup) this.findViewById(R.id.llFeatureView);
        Snackbar.make(view, "Please follow to chat!", Snackbar.LENGTH_LONG).show();
        setTab();
    }

    private void displayChat() {
        vpPager.setPagingEnabled(true);
        tabStrip.setDisabled(false);
    }

    private void setUpViewPager() {
        // Get the viewpager
        vpPager = (CustomViewPager) findViewById(R.id.viewpager);

        // Set the viewpager adapter for the pager
        vpPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        // Find the sliding tabstrip
        tabStrip = (CustomTabStrip) findViewById(R.id.tabs);
        tabStrip.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf"), Typeface.NORMAL);


        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);

        // Hides header card when the chat view is selected
        tabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    rlFeatures.setVisibility(View.GONE);
                } else {
                    rlFeatures.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
        });
    }

    private void setTab() {
        // Switch to page based on index
        vpPager.setCurrentItem(0);
    }
}



