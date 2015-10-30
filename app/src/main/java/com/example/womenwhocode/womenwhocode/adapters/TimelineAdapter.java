package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Awesome;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shehba.shahab on 10/17/15.
 */
public class TimelineAdapter extends ArrayAdapter<Post> {

    private ParseUser currentUser;
    private Post post;
    private Awesome awesome;
    private int awesomeCount;
    private ImageView ivFeaturePhoto;
    private TextView tvPostDescription;
    private TextView tvAwesomeCount;
    private TextView tvAwesomeIcon;
    private ProgressBar pb;
    private LinearLayout llTimelineItems;
    private TextView tvRelativeDate;
    private TextView tvFeatureTitle;

    public TimelineAdapter(Context context, List<Post> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        post = getItem(position);
        currentUser = ParseUser.getCurrentUser();
        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_timeline, parent, false);
        }

        // set the progress bar
        pb = (ProgressBar) convertView.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        // hide scroll view so the progress bar is the center of attention
        llTimelineItems = (LinearLayout) convertView.findViewById(R.id.llTimelineItems);
        llTimelineItems.setVisibility(ScrollView.INVISIBLE);

        // Look up views to populate data
        ivFeaturePhoto = (ImageView) convertView.findViewById(R.id.ivPostPhoto);
        tvPostDescription = (TextView) convertView.findViewById(R.id.tvPostDescription);
        tvAwesomeCount = (TextView) convertView.findViewById(R.id.tvAwesomeCount);
        tvAwesomeIcon = (TextView) convertView.findViewById(R.id.tvAwesomeIcon);
        tvRelativeDate = (TextView) convertView.findViewById(R.id.tvRelativeDate);
        tvFeatureTitle = (TextView) convertView.findViewById(R.id.tvPostTitle);

        tvAwesomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAwesome();
            }
        });

        ParseQuery<Awesome> query = Awesome.getQuery();
        query.whereEqualTo(Awesome.POST_KEY, post);
        query.whereEqualTo(Awesome.AWESOMED_KEY, true);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int i, ParseException e) {
                if (e == null) {
                    awesomeCount = i;
                } else {
                    awesomeCount = 0;
                }
                // Clear out the image views
                ivFeaturePhoto.setImageResource(0);

                // Insert the model data into each of the view items
                String description = post.getDescription();
                tvAwesomeCount.setText(Integer.valueOf(awesomeCount).toString());
                String relativeDate = post.getPostDateTime();
                String featureImageUrl = post.getFeatureImageUrl();
                String featureTitle = post.getFeatureTitle();
                tvPostDescription.setText(description);
                tvRelativeDate.setText(relativeDate);
                tvFeatureTitle.setText(featureTitle);

                // Insert the image using picasso
                Picasso.with(getContext()).load(featureImageUrl).into(ivFeaturePhoto);

                // hide the progress bar, show the main view
                pb.setVisibility(ProgressBar.GONE);
                llTimelineItems.setVisibility(ScrollView.VISIBLE);
            }
        });

        return convertView;
    }

    private void onAwesome() {
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