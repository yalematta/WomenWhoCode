package com.example.womenwhocode.womenwhocode.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.parse.ParseUser;

/**
 * Created by zassmin on 11/12/15.
 */
public class AddPostFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_post, container, false);
        Button btnAddPost = (Button) v.findViewById(R.id.btnAddPost);

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FIXME: make listener for the detail view or where ever once we nail down flow
                // TODO: support media uploads!
                EditText etAddPost = (EditText) v.findViewById(R.id.etAddPost);
                String postBody = etAddPost.getText().toString();
                if (TextUtils.isEmpty(postBody)) {
                    return;
                }
                addPost(postBody);


                // close this fragment
            }
        });

        return v;
    }



    private void addPost(String postBody) {
//        ParseUser currentUser = ParseUser.getCurrentUser();
//        Post post = new Post();
//        post.setDescription(postBody);
//        post.setUser(currentUser);
//        if (feature != null) {
//            post.setFeature(feature); // FIXME: get this working
//        }
//
//        if (event != null) {
//            post.setEvent(event); // FIXME: get this working
//        }
//        post.saveInBackground();
    }
}
