package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Awesome;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by zassmin on 10/26/15.
 */
public class PostsAdapter extends ArrayAdapter<Post> {

    Post post;
    private ParseUser currentUser;
    private Awesome awesome;
    private int awesomeCount;
    private ParseUser postUser;

    private static class ViewHolder {
        ImageView ivUserPhoto;
        TextView tvPostNameBy;
        TextView tvPostDescription;
        TextView tvAwesomeCount;
        TextView tvAwesomeIcon;
        TextView tvRelativeTime;
    }

    public PostsAdapter(Context context, ArrayList<Post> posts) {
        super(context, android.R.layout.simple_list_item_1, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        post = getItem(position);
        currentUser = ParseUser.getCurrentUser();

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_post, parent, false);
            viewHolder.ivUserPhoto = (ImageView) convertView.findViewById(R.id.ivUserPhoto);
            viewHolder.tvPostNameBy = (TextView) convertView.findViewById(R.id.tvPostNameBy);
            viewHolder.tvPostDescription = (TextView) convertView.findViewById(R.id.tvPostDescription);
            viewHolder.tvAwesomeCount = (TextView) convertView.findViewById(R.id.tvAwesomeCount);
            viewHolder.tvAwesomeIcon = (TextView) convertView.findViewById(R.id.tvAwesomeIcon);
            viewHolder.tvRelativeTime = (TextView) convertView.findViewById(R.id.tvRelativeTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        postUser = post.getUser();
        if (postUser != null) {
            viewHolder.tvPostNameBy.setText(postUser.getUsername());
            // TODO: find the profile for user to get their image :(
            // TODO: maybe add image icon instead
            Picasso.with(getContext()).load(post.getFeatureImageUrl()).into(viewHolder.ivUserPhoto);
        } else {
            viewHolder.ivUserPhoto.setImageResource(R.mipmap.ic_wwc_launcher); // TODO: switch to official logo
        }

        viewHolder.tvPostDescription.setText(post.getDescription());

        int awesomeCount = Awesome.getAwesomeCountFor(post);
        viewHolder.tvAwesomeCount.setText(Integer.toString(awesomeCount));

        // TODO: Hack - will consolidate into single icon for awesome/unawesome
        viewHolder.tvAwesomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAwesome(viewHolder.tvAwesomeCount);
            }
        });

        // TODO: load awesome icon

        viewHolder.tvRelativeTime.setText(post.getPostDateTime());

        return convertView;
    }

    private void onAwesome(TextView tvAwesomeCount) {
        if (awesome != null) {
            if (awesome.getAwesomed()) {

                // Update UI thread
                awesomeCount--;
                tvAwesomeCount.setText(Integer.valueOf(awesomeCount).toString());

                // Send data to Parse
                awesome.setAwesomed(false);
                awesome.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        // TODO: Sync Parse response with UI thread
                    }
                });
            } else {
                // Update UI thread
                awesomeCount++;
                tvAwesomeCount.setText(Integer.valueOf(awesomeCount).toString());

                // Send data to Parse
                awesome.setAwesomed(true);
                awesome.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        // TODO: Sync Parse response with UI thread
                    }
                });
            }
        } else {
            // Update UI thread
            awesomeCount++;
            tvAwesomeCount.setText(Integer.valueOf(awesomeCount).toString());

            // Send data to Parse
            awesome = new Awesome();
            awesome.setAwesomed(true);
            awesome.setUser(currentUser);
            awesome.setPost(post);
            awesome.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    // TODO: Sync Parse response with UI thread
                }
            });
        }
    }
}
