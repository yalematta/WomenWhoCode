package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Awesome;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.example.womenwhocode.womenwhocode.utils.CircleTransform;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zassmin on 10/26/15.
 */
public class PostsAdapter extends ArrayAdapter<Post> {
    private ParseUser currentUser;

    public PostsAdapter(Context context, ArrayList<Post> posts) {
        super(context, android.R.layout.simple_list_item_1, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        currentUser = ParseUser.getCurrentUser();

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_post, parent, false);

            viewHolder = new ViewHolder(convertView);
            viewHolder.post = post;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
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
                    viewHolder.tvAwesomeIcon.setTag(a);
                    // TODO if awesome.getAwesome == true change the icon to the awesomedd icon
                } else {
                    viewHolder.tvAwesomeIcon.setTag(null);
                }
            }
        });

        ParseUser postUser = post.getUser();
        if (postUser != null) {
            viewHolder.tvPostNameBy.setText(postUser.getUsername());
            // TODO: find the profile for user to get their image :(
            // TODO: maybe add image icon instead
            Picasso.with(getContext())
                    .load(post.getFeatureImageUrl())
                    .transform(new CircleTransform())
                    .resize(75, 75)
                    .centerCrop()
                    .into(viewHolder.ivUserPhoto);
        } else {
            viewHolder.ivUserPhoto.setImageResource(R.mipmap.ic_wwc_launcher); // TODO: switch to official logo
        }

        viewHolder.tvPostDescription.setText(post.getDescription());
        viewHolder.tvRelativeTime.setText(post.getPostDateTime());

        int awesomeCount = post.getAwesomeCount();
        viewHolder.tvAwesomeCount.setText(String.valueOf(awesomeCount));

        // Store all necessary data for click
        viewHolder.tvPostDescription.setTag(post);

        viewHolder.tvAwesomeIcon.setOnClickListener(new View.OnClickListener() {
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
                // ObjectAnimator anim = ObjectAnimator.ofFloat(savedAwesomeCount, "alpha", 1, 0, 1, 0, 1); // Flash
                // anim.setDuration(1000);
                // anim.start();

                onAwesome(tvAwesomeCount, awesome, post, v);
            }
        });

        return convertView;
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

//        ObjectAnimator anim = ObjectAnimator.ofFloat(savedAwesomeCount, "alpha", 1, 0, 1, 0, 1); // Flash
//        anim.setDuration(1000);
//        anim.start();

        // Send data to parse
        awesome.saveInBackground();
        savedPost.setAwesomeCount(awesomeCount);
        savedPost.saveInBackground();
    }

    static class ViewHolder {
        @Bind(R.id.ivUserPhoto) ImageView ivUserPhoto;
        @Bind(R.id.tvPostNameBy) TextView tvPostNameBy;
        @Bind(R.id.tvPostDescription) TextView tvPostDescription;
        @Bind(R.id.tvAwesomeCount) TextView tvAwesomeCount;
        @Bind(R.id.btnAwesomeIcon) ImageButton tvAwesomeIcon;
        @Bind(R.id.tvRelativeTime) TextView tvRelativeTime;
        Post post;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}