package com.example.womenwhocode.womenwhocode.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.TimelineAdapter;
import com.example.womenwhocode.womenwhocode.models.Awesome;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.NetworkConnectivityReceiver;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by shehba.shahab on 10/16/15.
 */
public class TimelineFragment extends Fragment {
    private TimelineAdapter aPosts;
    private ArrayList<Post> posts;
    private RecyclerView rvPosts;
    private ProgressBar pb;
    private ParseUser currentUser;
    private ParseQuery<Awesome> awesomeParseQuery;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onFeatureTimelineClickListener(Feature feature);
        void onEventTimelineClickListener(Event event);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posts = new ArrayList<>();
        aPosts = new TimelineAdapter(posts);
        currentUser = ParseUser.getCurrentUser();
        awesomeParseQuery = ParseQuery.getQuery(Awesome.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        rvPosts = (RecyclerView) view.findViewById(R.id.lvPosts);
        rvPosts.setAdapter(aPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setItemAnimator(new SlideInUpAnimator());

        // hide list view until data is fully loaded
        rvPosts.setVisibility(ListView.INVISIBLE);

        // show progress bar in the meantime
        pb = (ProgressBar) view.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        // listeners
        aPosts.setOnItemClickListener(new TimelineAdapter.OnItemClickListener() {

            @Override
            public void onPostFeatureClick(View itemView, int position) {
                Post post = posts.get(position);
                Feature feature = post.getFeature();
                Event event = post.getEvent();

                if (feature != null) {
                    listener.onFeatureTimelineClickListener(feature);
                } else if (event != null) {
                    listener.onEventTimelineClickListener(event);
                }
            }

            @Override
            public void onAwesomeClick(final View itemView, final int position) {
                final Post post = posts.get(position);

                // find awesome object
                awesomeParseQuery.whereEqualTo(Awesome.POST_KEY, post);
                awesomeParseQuery.whereEqualTo(Awesome.USER_KEY, currentUser);
                awesomeParseQuery.getFirstInBackground(new GetCallback<Awesome>() {
                    @Override
                    public void done(Awesome awesome, ParseException e) {
                        if (e == null) {
                            onAwesome(awesome, post, itemView, position);
                        } else {
                            onAwesome(null, post, itemView, position);
                        }
                    }
                });
            }
        });

        populatePostsList();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemClickListener) {
            listener = (OnItemClickListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement TimelineFragment.OnItemClickListener");
        }
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
        postQuery.include(Post.USER_KEY);
        postQuery.findInBackground(new FindCallback<Post>() {
            public void done(List<Post> listPosts, ParseException e) {
                if (e != null) {
                    Log.d("Message", "Error: " + e.getMessage());
                } else if (listPosts != null) {
                    posts.clear();
                    posts.addAll(listPosts);
                    aPosts.notifyDataSetChanged();

                    // hide progress bar, make list view appear
                    pb.setVisibility(ProgressBar.GONE);
                    rvPosts.setVisibility(ListView.VISIBLE);

                    LocalDataStore.unpinAndRepin(listPosts, LocalDataStore.POSTS_PIN);
                } else {
//                    Toast.makeText(getContext(), "who knows timeline", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void animateOnUnawesome(ImageButton awesomeIcon) {
        Animation animation;
        animation = AnimationUtils.loadAnimation(getContext(),
                R.anim.scale_down);
        awesomeIcon.startAnimation(animation);
    }

    private void animateOnAwesome(final ImageButton awesomeIcon) {
        Animation animateOnAwesome;
        animateOnAwesome = AnimationUtils.loadAnimation(getContext(),
                R.anim.scale_up);
        awesomeIcon.startAnimation(animateOnAwesome);
        animateOnAwesome.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                awesomeIcon.setImageResource(R.drawable.awesomeddd);
            }

            public void onAnimationEnd(Animation anim) {
                awesomeIcon.setImageResource(R.drawable.awesome);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void onAwesome(Awesome awesome, Post savedPost, View v, final int position) {
        int awesomeCount = savedPost.getAwesomeCount(); // Get latest value
        ImageButton awesomeIcon = (ImageButton) v.findViewById(R.id.btnAwesomeIcon);
        TextView tvAwesomeCount = (TextView) v.findViewById(R.id.tvAwesomeCount);

        if (awesome != null) {
            if (awesome.getAwesomed()) {
                // Update UI thread
                awesomeCount--;
                animateOnUnawesome(awesomeIcon);

                // Build parse request
                awesome.setAwesomed(false);
            } else {
                // Update UI thread
                awesomeCount++;
                animateOnAwesome(awesomeIcon);

                // Build parse request
                awesome.setAwesomed(true);
            }
        } else {
            // Update UI thread
            awesomeCount++;
            animateOnAwesome(awesomeIcon);

            // Build parse request
            awesome = new Awesome();
            awesome.setAwesomed(true);
            awesome.setUser(currentUser);
            awesome.setPost(savedPost);
        }

        // Update the UI thread
        tvAwesomeCount.setText(String.valueOf(awesomeCount));

        // Send data to parse
        awesome.saveInBackground();
        savedPost.setAwesomeCount(awesomeCount);
        savedPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // notify adapter on successful save
                    aPosts.notifyItemChanged(position);
                }
            }
        });
    }
}