package com.example.womenwhocode.womenwhocode.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.PostsAdapter;
import com.example.womenwhocode.womenwhocode.models.Post;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.GRAY;

/**
 * Created by zassmin on 10/26/15.
 */
public class PostsListFragment extends Fragment {
    View v;
    ListView lvPosts;
    ArrayList<Post> posts;
    PostsAdapter aPosts;
    ProgressBar pb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        posts = new ArrayList<>();
        aPosts = new PostsAdapter(getActivity(), posts);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_posts_list, container, false);
        lvPosts = (ListView) v.findViewById(R.id.lvPosts);

        // setup the view
        setUpView();

        // set adapter
        lvPosts.setAdapter(aPosts);
        return v;
    }

    protected void setSpinners() {
        lvPosts.setVisibility(ListView.INVISIBLE);
        pb = (ProgressBar) v.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);
    }

    protected void clearSpinners() {
        lvPosts.setVisibility(ListView.VISIBLE);
        pb.setVisibility(ProgressBar.GONE);
    }

    protected void add(List<Post> postList) {
        aPosts.addAll(postList);
    }

    protected void clear() {
        aPosts.clear();
    }

    protected void noPostsView() {
        RelativeLayout rlPostLists = (RelativeLayout) v.findViewById(R.id.rlPostLists);
        rlPostLists.setBackgroundColor(GRAY);
    }

    protected void populatePosts() {
        // override this in the other fragments
    }

    private void setUpView() {
        setSpinners();

        // populate data
        populatePosts();
    }
}
