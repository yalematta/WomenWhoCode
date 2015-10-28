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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by zassmin on 10/26/15.
 */
public class PostsAdapter extends ArrayAdapter<Post> {

    Post post;

    private static class ViewHolder {
        ImageView ivUserPhoto;
        TextView tvPostNameBy;
        TextView tvPostDescription;
        TextView tvAwesomeCount;
        TextView tvAwesomeIcon;
        TextView tvUnawesomeIcon;
        TextView tvRelativeTime;
    }

    public PostsAdapter(Context context, ArrayList<Post> posts) {
        super(context, android.R.layout.simple_list_item_1, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        post = getItem(position);

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
            viewHolder.tvUnawesomeIcon = (TextView) convertView.findViewById(R.id.tvUnawesomeIcon); // TODO: Remove this icon
            viewHolder.tvRelativeTime = (TextView) convertView.findViewById(R.id.tvRelativeTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (post.getUser() != null) {
            // TODO: find the profile for that user to load image and full name
            viewHolder.tvPostNameBy.setText("Fun Name");
            Picasso.with(getContext()).load(post.getFeatureImageUrl()).into(viewHolder.ivUserPhoto); // FIXME: for now
        }

        viewHolder.tvPostDescription.setText(post.getDescription());

        int awesomeCount = Awesome.getAwesomeCountFor(post);
        viewHolder.tvAwesomeCount.setText(Integer.toString(awesomeCount));

        // TODO: Hack - will consolidate into single icon for awesome/unawesome
        viewHolder.tvAwesomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Awesome.awesomePostForCurrentUser(post);
                viewHolder.tvAwesomeCount.setText("Awesomed");
            }
        });
        viewHolder.tvUnawesomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Awesome.unAwesomePostForCurrentUser(post);
                viewHolder.tvAwesomeCount.setText("Unawesomed");
            }
        });

        // TODO: load awesome icon

        viewHolder.tvRelativeTime.setText(post.getPostDateTime());

        return convertView;
    }
}
