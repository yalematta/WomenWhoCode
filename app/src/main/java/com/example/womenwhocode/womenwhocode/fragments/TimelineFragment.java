package com.example.womenwhocode.womenwhocode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posts = new ArrayList<>();
        aPosts = new TimelineAdapter(getActivity(), posts);
        populatePostsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        lvPosts = (ListView) view.findViewById(R.id.lvPosts);
        lvPosts.setAdapter(aPosts);
        return view;
    }

    void addAll(List<Post> posts) {
        aPosts.addAll(posts);
    }

    void populatePostsList() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.orderByAscending("title");
        query.findInBackground(new FindCallback<Post>() {
            public void done(List<Post> lvPosts, ParseException e) {
                if (e == null) {
                    aPosts.clear();
                    addAll(lvPosts);
                    aPosts.notifyDataSetChanged();
                } else {
                    Log.d("Message", "Error: " + e.getMessage());
                }
            }
        });
    }
}