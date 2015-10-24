package com.example.womenwhocode.womenwhocode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.TimelineAdapter;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shehba.shahab on 10/16/15.
 */
public class TimelineFragment extends Fragment {
    TimelineAdapter aPosts;
    ArrayList<Post> posts;
    ListView lvPosts;
    ProgressBar pb;

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

    void addAll(List<Post> posts) {
        aPosts.addAll(posts);
    }

    void populatePostsList() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.whereExists(Post.DESCRIPTION_KEY);
        query.findInBackground(new FindCallback<Post>() {
            public void done(List<Post> listPosts, ParseException e) {
                if (e == null) {
                    aPosts.clear();
                    addAll(listPosts);
                    aPosts.notifyDataSetChanged();

                    pb.setVisibility(ProgressBar.GONE);
                    lvPosts.setVisibility(ListView.VISIBLE);
                } else {
                    Log.d("Message", "Error: " + e.getMessage());
                }
            }
        });
    }
}