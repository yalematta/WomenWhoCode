package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zassmin on 10/26/15.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private List<Post> mPosts;
    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onAwesomeClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public PostsAdapter(List<Post> posts) {
        this.mPosts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPosts.get(position);

        // set up vars
        TextView tvPostNameBy = holder.tvPostNameBy;
        ImageView ivUserPhoto = holder.ivUserPhoto;
        TextView tvPostDescription = holder.tvPostDescription;
        TextView tvRelativeTime = holder.tvRelativeTime;
        TextView tvAwesomeCount = holder.tvAwesomeCount;
        ImageButton btnAwesomeIcon = holder.btnAwesomeIcon;

        // to resolve bug where icon changes on many items
        btnAwesomeIcon.setImageResource(0);
        btnAwesomeIcon.setImageResource(R.drawable.awesome);

        // user details
        ivUserPhoto.setImageResource(0);
        Context context = tvPostNameBy.getContext();
        ParseUser postUser = post.getUser();
        if (postUser != null) {
            tvPostNameBy.setText(postUser.getUsername());
            // TODO: find the profile for user to get their image :(
            Picasso.with(context)
                    .load(post.getFeatureImageUrl())
                    .transform(new CircleTransform())
                    .resize(75, 75)
                    .centerCrop()
                    .into(ivUserPhoto);
        } else {
            ivUserPhoto.setImageResource(R.mipmap.ic_wwc_launcher); // TODO: switch to official logo
        }

        tvPostDescription.setText(post.getDescription());
        tvRelativeTime.setText(post.getPostDateTime());

        int awesomeCount = post.getAwesomeCount();
        tvAwesomeCount.setText(String.valueOf(awesomeCount));
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivUserPhoto) public ImageView ivUserPhoto;
        @Bind(R.id.tvPostNameBy) public TextView tvPostNameBy;
        @Bind(R.id.tvPostDescription) public TextView tvPostDescription;
        @Bind(R.id.tvAwesomeCount) public TextView tvAwesomeCount;
        @Bind(R.id.btnAwesomeIcon) public ImageButton btnAwesomeIcon;
        @Bind(R.id.tvRelativeTime) public TextView tvRelativeTime;

        public ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            btnAwesomeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAwesomeClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }
}