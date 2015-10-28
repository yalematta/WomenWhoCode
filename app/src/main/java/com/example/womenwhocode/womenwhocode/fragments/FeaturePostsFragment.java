package com.example.womenwhocode.womenwhocode.fragments;

import android.os.Bundle;
import android.util.Log;

import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.NetworkConnectivityReceiver;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by shehba.shahab on 10/27/15.
 */
public class FeaturePostsFragment extends PostsListFragment {
    public static String FEATURE_ID = "feature_id";
    public static String featureId;
    protected ParseQuery<Feature> featureParseQuery;
    protected ParseQuery<Post> postParseQuery;

    public static FeaturePostsFragment newInstance(String featureObjectId) {
        FeaturePostsFragment featurePostsFragment = new FeaturePostsFragment();
        Bundle args = new Bundle();
        args.putString(FEATURE_ID, featureObjectId);
        featurePostsFragment.setArguments(args);
        return featurePostsFragment;
    }

    @Override
    protected void populatePosts() {
        featureId = getArguments().getString(FEATURE_ID, "");

        featureParseQuery = ParseQuery.getQuery(Feature.class);
        postParseQuery = ParseQuery.getQuery(Post.class);
        if (!NetworkConnectivityReceiver.isNetworkAvailable(getContext())) {
            postParseQuery.fromPin(featureId);
        }

        featureParseQuery.getInBackground(featureId, new GetCallback<Feature>() {
            @Override
            public void done(Feature feature, ParseException e) {
                postParseQuery.whereEqualTo(Post.FEATURE_KEY, feature);
                postParseQuery.findInBackground(new FindCallback<Post>() {
                    @Override
                    public void done(List<Post> list, ParseException e) {
                        if (e == null && list.size() > 0) {
                            clear();
                            add(list);
                            clearSpinners();
                            LocalDataStore.unpinAndRepin(list, featureId);
                        } else if (e != null) {
                            Log.d("FEATURES_POST_FAIL", "Error: " + e.getMessage());
                        } else {
                            clearSpinners();
                            noPostsView();
                        }
                    }
                });
            }
        });
    }
}
