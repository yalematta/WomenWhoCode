package com.example.womenwhocode.womenwhocode.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.fragments.FeaturePostsFragment;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.NetworkConnectivityReceiver;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        feature_id = getIntent().getStringExtra("feature_id");

        setUpView();

        if (savedInstanceState == null) {
            FeaturePostsFragment featurePostsFragment = FeaturePostsFragment.newInstance(feature_id);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flFeatureContainer, featurePostsFragment);
            ft.commit();
        }

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

                        // Hide the progress bar, show the main view
                        pb.setVisibility(ProgressBar.GONE);
                        rlFeatures.setVisibility(ScrollView.VISIBLE);
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
        int subscriberCount = feature.getAwesomeCount();

        tvFeatureTitle.setText(title);
        tvFeatureDescription.setText(description);
        tvSubscriberCount.setText(Integer.valueOf(subscriberCount).toString() + " Following!");
    }
}



