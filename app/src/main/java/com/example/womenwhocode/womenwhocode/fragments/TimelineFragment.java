package com.example.womenwhocode.womenwhocode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.TimelineAdapter;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.NetworkConnectivityReceiver;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shehba.shahab on 10/16/15.
 */
public class TimelineFragment extends Fragment {
    private TimelineAdapter aPosts;
    private ArrayList<Post> posts;
    private ListView lvPosts;
    private ProgressBar pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posts = new ArrayList<>();
        aPosts = new TimelineAdapter(getActivity(), posts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        lvPosts = (ListView) view.findViewById(R.id.lvPosts);
        lvPosts.setAdapter(aPosts);

        // hide list view until data is fully loaded
        lvPosts.setVisibility(ListView.INVISIBLE);

        // show progress bar in the meantime
        pb = (ProgressBar) view.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        populatePostsList();

        return view;
    }

    private void populatePostsList() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<Post> postQuery = ParseQuery.getQuery(Post.class);
        ParseQuery<Subscribe> subscribeQuery = ParseQuery.getQuery(Subscribe.class);
        subscribeQuery.whereEqualTo(Subscribe.USER_KEY, currentUser);
        subscribeQuery.whereEqualTo(Subscribe.SUBSCRIBED_KEY, true);

        if (!NetworkConnectivityReceiver.isNetworkAvailable(getContext())) {
            postQuery.fromPin(LocalDataStore.POSTS_PIN);
        }

        ParseQuery<Post> postFeatureQuery = ParseQuery.getQuery(Post.class);
        postFeatureQuery.whereMatchesKeyInQuery(Post.FEATURE_KEY, Subscribe.FEATURE_KEY, subscribeQuery);

        ParseQuery<Post> postEventQuery = ParseQuery.getQuery(Post.class);
        postEventQuery.whereMatchesKeyInQuery(Post.EVENT_KEY, Subscribe.EVENT_KEY, subscribeQuery);

        List<ParseQuery<Post>> subscribedFeaturesAndEvents = new ArrayList<>();
        subscribedFeaturesAndEvents.add(postFeatureQuery);
        subscribedFeaturesAndEvents.add(postEventQuery);

        // Return all posts for features or events to which the user is subscribed
        postQuery = ParseQuery.or(subscribedFeaturesAndEvents);
        postQuery.include(Post.FEATURE_KEY);
        postQuery.findInBackground(new FindCallback<Post>() {
            public void done(List<Post> listPosts, ParseException e) {
                if (e != null) {
                    Log.d("Message", "Error: " + e.getMessage());
                } else if (listPosts != null) {
                    aPosts.clear();
                    aPosts.addAll(listPosts);
                    aPosts.notifyDataSetChanged();

                    // hide progress bar, make list view appear
                    pb.setVisibility(ProgressBar.GONE);
                    lvPosts.setVisibility(ListView.VISIBLE);

                    LocalDataStore.unpinAndRepin(listPosts, LocalDataStore.POSTS_PIN);
                } else {
                    Toast.makeText(getContext(), "who knows timeline", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}