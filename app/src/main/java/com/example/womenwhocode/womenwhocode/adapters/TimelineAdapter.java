package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.activities.EventDetailsActivity;
import com.example.womenwhocode.womenwhocode.activities.FeatureDetailsActivity;
import com.example.womenwhocode.womenwhocode.activities.TimelineActivity;
import com.example.womenwhocode.womenwhocode.models.Awesome;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.example.womenwhocode.womenwhocode.utils.CircleTransform;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shehba.shahab on 10/17/15.
 */
public class TimelineAdapter extends ArrayAdapter<Post> {
    private ParseUser currentUser;

    public TimelineAdapter(Context context, List<Post> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        currentUser = ParseUser.getCurrentUser();

        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_timeline, parent, false);

            holder = new ViewHolder(convertView);
            holder.post = post;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // TODO: set awesome icon here - default - not awesome yet
        // get the awesome object
        ParseQuery<Awesome> awesomeParseQuery = ParseQuery.getQuery(Awesome.class);
        awesomeParseQuery.whereEqualTo(Awesome.POST_KEY, post);
        awesomeParseQuery.whereEqualTo(Awesome.USER_KEY, currentUser);
        awesomeParseQuery.getFirstInBackground(new GetCallback<Awesome>() {
            @Override
            public void done(Awesome a, ParseException e) {
                if (e == null) {
                    holder.tvAwesomeIcon.setTag(a);
                    // TODO if awesome.getAwesome == true change the icon to the awesomedd icon
                } else {
                    holder.tvAwesomeIcon.setTag(null);
                }
            }
        });

        // Set the progress bar
        holder.pb.setVisibility(ProgressBar.VISIBLE);

        // Hide relative layout so the progress bar is the center of attention
        holder.cvPostFeature.setVisibility(CardView.INVISIBLE);

        // Clear out the image views
        holder.ivPostPhoto.setImageResource(0);

        String title = "WWCode";
        // final so on click of feature has access to it, these values don't change anyway
        final Feature feature = post.getFeature();
        final Event event = post.getEvent();
        if (feature != null) {
            String imageUrl = feature.getImageUrl();
            title = feature.getTitle();
            String hexColor = feature.getHexColor();

            // set feature background color
            // set color!
            int color = Color.parseColor(String.valueOf(hexColor));
            holder.rlPostFeature.setBackgroundColor(color); // default color is set in xml

            // Insert the image using picasso
            Picasso.with(getContext())
                    .load(imageUrl)
                    .transform(new CircleTransform())
                    .resize(75, 75)
                    .centerCrop()
                    .into(holder.ivPostPhoto);

        } else if (event != null) {
            title = event.getTitle();

            // insert icon
            holder.ivPostPhoto.setImageResource(R.drawable.ic_calendar_check);
        }

        // in case a post has a user
        ParseUser postUser = post.getUser();
        if (postUser != null) {
            holder.tvPostNameBy.setText(postUser.getUsername());
        }

        // Insert the model data into each of the view items
        String description = post.getDescription();
        String relativeDate = post.getPostDateTime();
        int awesomeCount = post.getAwesomeCount();

        holder.tvPostDescription.setText(description);
        holder.tvRelativeDate.setText(relativeDate);
        holder.tvFeatureTitle.setText(title);
        holder.tvAwesomeCount.setText(String.valueOf(awesomeCount));

        // Store all necessary data for click
        holder.tvPostDescription.setTag(post);

        // Hide the progress bar, show the main view
        holder.pb.setVisibility(ProgressBar.GONE);
        holder.cvPostFeature.setVisibility(CardView.VISIBLE);

        holder.tvAwesomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent = (View) v.getParent();
                // grab the tagged objects
                Post post = (Post) parent.findViewById(R.id.tvPostDescription).getTag();
                Awesome awesome = (Awesome) v.getTag();
                TextView tvAwesomeCount = (TextView) parent.findViewById(R.id.tvAwesomeCount);

                // TODO: start icon and counter animation here!
                // check which icon is it - awesome or unawesome
                // do animation on the icon
                // switch them with a nice scale in out
                // update count value based on awesome count (+||-)

                // could the value of a be null?
                onAwesome(tvAwesomeCount, awesome, post, v);
            }
        });

        holder.rlPostFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feature != null) {
                    Intent i = new Intent(getContext(), FeatureDetailsActivity.class);
                    i.putExtra("feature_id", feature.getObjectId());
                    i.putExtra(TimelineActivity.SELECTED_TAB_EXTRA_KEY, TimelineActivity.TIMELINE_TAB);
                    getContext().startActivity(i);
                } else if (event != null) {
                    Intent i = new Intent(getContext(), EventDetailsActivity.class);
                    i.putExtra("event_id", event.getObjectId());
                    i.putExtra(TimelineActivity.SELECTED_TAB_EXTRA_KEY, TimelineActivity.TIMELINE_TAB);
                    getContext().startActivity(i);
                }
            }
        });
        return convertView;
    }

    private void animateOnAwesome(final ImageButton awesomeIcon) {
        Animation animateOnAwesome;
        animateOnAwesome = AnimationUtils.loadAnimation(getContext(),
                R.anim.scale_up);
        awesomeIcon.startAnimation(animateOnAwesome);
        animateOnAwesome.setAnimationListener(new AnimationListener() {
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


    private void onAwesome(TextView tvAwesomeCount, Awesome awesome, Post savedPost, View v) {
        int awesomeCount = savedPost.getAwesomeCount(); // Get latest value
        ImageButton awesomeIcon = (ImageButton) v.findViewById(R.id.btnAwesomeIcon);

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
        // reset the awesome account in case it was null before!
        v.setTag(awesome);

        // Send data to parse
        awesome.saveInBackground();
        savedPost.setAwesomeCount(awesomeCount);
        savedPost.saveInBackground();
    }

    static class ViewHolder {
        @Bind(R.id.ivPostPhoto)
        ImageView ivPostPhoto;
        @Bind(R.id.tvPostDescription)
        TextView tvPostDescription;
        @Bind(R.id.tvAwesomeCount)
        TextView tvAwesomeCount;
        @Bind(R.id.btnAwesomeIcon)
        ImageButton tvAwesomeIcon;
        @Bind(R.id.cvPostFeature)
        CardView cvPostFeature;
        @Bind(R.id.tvRelativeDate)
        TextView tvRelativeDate;
        @Bind(R.id.tvPostTitle)
        TextView tvFeatureTitle;
        @Bind(R.id.pbLoading)
        ProgressBar pb;
        @Bind(R.id.rlPostFeature)
        RelativeLayout rlPostFeature;
        @Bind(R.id.tvPostNameBy)
        TextView tvPostNameBy;
        Post post;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}