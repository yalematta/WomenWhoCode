package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.activities.FeatureDetailsActivity;
import com.example.womenwhocode.womenwhocode.activities.TimelineActivity;
import com.example.womenwhocode.womenwhocode.models.Awesome;
import com.example.womenwhocode.womenwhocode.models.Feature;
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
    private ViewHolder viewHolder;
    private ParseUser currentUser;
    private Post post;
    private Awesome awesome;
    private int awesomeCount;
    private Feature feature;

    public TimelineAdapter(Context context, List<Post> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        post = getItem(position);
        currentUser = ParseUser.getCurrentUser();

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_timeline, parent, false);
            viewHolder.pb = (ProgressBar) convertView.findViewById(R.id.pbLoading);
            // top level relative layout
            viewHolder.cvPostFeature = (CardView) convertView.findViewById(R.id.cvPostFeature);

            // Look up views to populate data
            viewHolder.ivFeaturePhoto = (ImageView) convertView.findViewById(R.id.ivPostPhoto);
            viewHolder.tvPostDescription = (TextView) convertView.findViewById(R.id.tvPostDescription);
            viewHolder.tvAwesomeCount = (TextView) convertView.findViewById(R.id.tvAwesomeCount);
            viewHolder.tvAwesomeIcon = (TextView) convertView.findViewById(R.id.tvAwesomeIcon);
            viewHolder.tvRelativeDate = (TextView) convertView.findViewById(R.id.tvRelativeDate);
            viewHolder.tvFeatureTitle = (TextView) convertView.findViewById(R.id.tvPostTitle);
            // header relative layout
            viewHolder.rlPostFeature = (RelativeLayout) convertView.findViewById(R.id.rlPostFeature);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set the progress bar
        viewHolder.pb.setVisibility(ProgressBar.VISIBLE);

        // Hide relative layout so the progress bar is the center of attention
        viewHolder.cvPostFeature.setVisibility(CardView.INVISIBLE);

        viewHolder.tvAwesomeIcon.setOnClickListener(new View.OnClickListener() {
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
                viewHolder.ivFeaturePhoto.setImageResource(0);

                // Insert the model data into each of the view items
                String description = post.getDescription();
                String relativeDate = post.getPostDateTime();
                feature = post.getFeature();
                String featureImageUrl = feature.getImageUrl();
                String featureTitle = feature.getTitle();
                String featureColor = feature.getHexColor();

                // set feature background color
                // set color!
                int color = Color.parseColor(String.valueOf(featureColor));
                viewHolder.rlPostFeature.setBackgroundColor(color);

                viewHolder.tvAwesomeCount.setText(Integer.valueOf(awesomeCount).toString());
                viewHolder.tvPostDescription.setText(description);
                viewHolder.tvRelativeDate.setText(relativeDate);
                viewHolder.tvFeatureTitle.setText(featureTitle);

                // Insert the image using picasso
                Picasso.with(getContext()).load(featureImageUrl).into(viewHolder.ivFeaturePhoto);

                // Hide the progress bar, show the main view
                viewHolder.pb.setVisibility(ProgressBar.GONE);
                viewHolder.cvPostFeature.setVisibility(CardView.VISIBLE);
            }
        });

        viewHolder.rlPostFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), FeatureDetailsActivity.class);
                i.putExtra("feature_id", feature.getObjectId());
                i.putExtra(TimelineActivity.SELECTED_TAB_EXTRA_KEY, TimelineActivity.TIMELINE_TAB);
                getContext().startActivity(i);
            }
        });

        return convertView;
    }

    private void onAwesome() {
        if (awesome != null) {
            if (awesome.getAwesomed()) {

                // Update UI thread
                awesomeCount--;
                viewHolder.tvAwesomeCount.setText(Integer.valueOf(awesomeCount).toString());

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
                viewHolder.tvAwesomeCount.setText(Integer.valueOf(awesomeCount).toString());

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
            viewHolder.tvAwesomeCount.setText(Integer.valueOf(awesomeCount).toString());

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

    private static class ViewHolder {
        ImageView ivFeaturePhoto;
        TextView tvPostDescription;
        TextView tvAwesomeCount;
        TextView tvAwesomeIcon;
        CardView cvPostFeature;
        TextView tvRelativeDate;
        TextView tvFeatureTitle;
        ProgressBar pb;
        RelativeLayout rlPostFeature;
    }
}