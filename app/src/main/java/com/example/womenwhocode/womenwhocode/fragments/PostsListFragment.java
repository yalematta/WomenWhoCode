package com.example.womenwhocode.womenwhocode.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.PostsAdapter;
import com.example.womenwhocode.womenwhocode.models.Awesome;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by zassmin on 10/26/15.
 */
public class PostsListFragment extends Fragment {
    private View v;
    private RecyclerView rvPosts;
    private ArrayList<Post> posts;
    private PostsAdapter aPosts;
    private ProgressBar pb;
    private ParseQuery<Awesome> awesomeParseQuery;
    private ParseUser currentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        posts = new ArrayList<>();
        aPosts = new PostsAdapter(posts);
        awesomeParseQuery = ParseQuery.getQuery(Awesome.class);
        currentUser = ParseUser.getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_posts_list, container, false);
        rvPosts = (RecyclerView) v.findViewById(R.id.lvPosts);

        // setup the view
        setUpView();

        // set adapter
        rvPosts.setAdapter(aPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setItemAnimator(new SlideInUpAnimator());

        aPosts.setOnItemClickListener(new PostsAdapter.OnItemClickListener() {
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

        return v;
    }

    protected void setSpinners() {
        rvPosts.setVisibility(ListView.INVISIBLE);
        pb = (ProgressBar) v.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);
    }

    protected void clearSpinners() {
        rvPosts.setVisibility(ListView.VISIBLE);
        pb.setVisibility(ProgressBar.GONE);
    }

    protected void add(List<Post> postList) {
        posts.addAll(postList);
    }

    protected void clear() {
        posts.clear();
    }

    protected void notifiedDataChanged() {
        aPosts.notifyDataSetChanged();
    }

    protected void noPostsView(String color) {
        RelativeLayout rlPostLists = (RelativeLayout) v.findViewById(R.id.rlPostLists);
        int intColor = Color.parseColor(String.valueOf(color));
        rlPostLists.setBackgroundColor(intColor);
    }

    protected void populatePosts() {
        // override this in the other fragments
    }

    private void setUpView() {
        setSpinners();

        // populate data
        populatePosts();
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

    private void animateOnUnawesome(ImageButton awesomeIcon) {
        Animation animation;
        animation = AnimationUtils.loadAnimation(getContext(),
                R.anim.scale_down);
        awesomeIcon.startAnimation(animation);
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
        // TODO: it's probably safe to do this before the onAwesome
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
