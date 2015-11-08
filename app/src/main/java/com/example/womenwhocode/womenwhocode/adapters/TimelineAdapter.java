package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.example.womenwhocode.womenwhocode.utils.CircleTransform;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shehba.shahab on 10/17/15.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private List<Post> mPosts;
    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onPostFeatureClick(View itemView, int position);
        void onAwesomeClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TimelineAdapter(List<Post> posts) {
        mPosts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View timelineView = inflater.inflate(R.layout.item_timeline, parent, false);
        ViewHolder viewHolder = new ViewHolder(timelineView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPosts.get(position);

        ProgressBar pb = holder.pb;
        CardView cvPostFeature = holder.cvPostFeature;
        ImageView ivPostPhoto = holder.ivPostPhoto;
        RelativeLayout rlPostFeature = holder.rlPostFeature;
        TextView tvPostNameBy = holder.tvPostNameBy;
        TextView tvPostDescription = holder.tvPostDescription;
        TextView tvRelativeDate = holder.tvRelativeDate;
        TextView tvFeatureTitle = holder.tvFeatureTitle;
        TextView tvAwesomeCount = holder.tvAwesomeCount;
        ImageButton btnAwesomeIcon = holder.btnAwesomeIcon;

        // Set the progress bar
        pb.setVisibility(ProgressBar.VISIBLE);
        // Hide relative layout so the progress bar is the center of attention
        cvPostFeature.setVisibility(CardView.INVISIBLE);

        // to resolve bug where icon changes on many items
        btnAwesomeIcon.setImageResource(0);
        btnAwesomeIcon.setImageResource(R.drawable.awesome);

        // Set up feature/event specific attrs
        ivPostPhoto.setImageResource(0);
        Context context = ivPostPhoto.getContext();
        String title = "WWCode";
        Feature feature = post.getFeature();
        Event event = post.getEvent();
        if (feature != null) {
            String imageUrl = feature.getImageUrl();
            title = feature.getTitle();
            String hexColor = feature.getHexColor();

            // set feature background color
            // set color!
            int color = Color.parseColor(String.valueOf(hexColor));
            rlPostFeature.setBackgroundColor(color); // default color is set in xml

            // Insert the image using picasso
            Picasso.with(context)
                    .load(imageUrl)
                    .transform(new CircleTransform())
                    .resize(75, 75)
                    .centerCrop()
                    .into(ivPostPhoto);

        } else if (event != null) {
            title = event.getTitle();

            // insert icon
            ivPostPhoto.setImageResource(R.drawable.ic_calendar_check); // since we know at this point they are following
        }

        // in case a post has a user
        ParseUser postUser = post.getUser();
        if (postUser != null) { // FIXME: should be full name from profile
            tvPostNameBy.setText(postUser.getUsername());
        }

        // Insert the model data into each of the view items
        String description = post.getDescription();
        String relativeDate = post.getPostDateTime();
        int awesomeCount = post.getAwesomeCount();

        tvPostDescription.setText(description);
        tvRelativeDate.setText(relativeDate);
        tvFeatureTitle.setText(title);
        tvAwesomeCount.setText(String.valueOf(awesomeCount));

        // Hide the progress bar, show the main view
        pb.setVisibility(ProgressBar.GONE);
        cvPostFeature.setVisibility(CardView.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivPostPhoto) public ImageView ivPostPhoto;
        @Bind(R.id.tvPostDescription) public TextView tvPostDescription;
        @Bind(R.id.tvAwesomeCount) public TextView tvAwesomeCount;
        @Bind(R.id.btnAwesomeIcon) public ImageButton btnAwesomeIcon;
        @Bind(R.id.cvPostFeature) public CardView cvPostFeature;
        @Bind(R.id.tvRelativeDate) public TextView tvRelativeDate;
        @Bind(R.id.tvPostTitle) public TextView tvFeatureTitle;
        @Bind(R.id.pbLoading) public ProgressBar pb;
        @Bind(R.id.rlPostFeature) public RelativeLayout rlPostFeature;
        @Bind(R.id.tvPostNameBy) public TextView tvPostNameBy;

        public ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            // FIXME: this may not work and I may need to reference: http://stackoverflow.com/questions/24885223/why-doesnt-recyclerview-have-onitemclicklistener-and-how-recyclerview-is-dif/24933117#24933117
            rlPostFeature.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onPostFeatureClick(itemView, getLayoutPosition());
                    }
                }
            });

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