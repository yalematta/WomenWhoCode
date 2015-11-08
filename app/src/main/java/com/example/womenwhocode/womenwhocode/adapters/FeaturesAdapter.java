package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shehba.shahab on 10/17/15.
 */
public class FeaturesAdapter extends RecyclerView.Adapter<FeaturesAdapter.ViewHolder> {
    private static OnItemClickListener listener;
    private final List<Feature> mFeatures;

    public FeaturesAdapter(List<Feature> features) {
        mFeatures = features;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        FeaturesAdapter.listener = listener;
    }

    public FeaturesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View featureView = inflater.inflate(R.layout.item_feature, parent, false);

        return new ViewHolder(featureView);
    }

    @Override
    public void onBindViewHolder(FeaturesAdapter.ViewHolder holder, int position) {
        Feature feature = mFeatures.get(position);

        TextView tvFeatureTitle = holder.tvFeatureTitle;
        ImageView ivFeatureImage = holder.ivFeatureImage;
        CardView cvFeature = holder.cvFeature;

        // Insert the model data into each of the view items
        String title = feature.getTitle();
        String imageUrl = feature.getImageUrl();

        // set color!
        int color = Color.parseColor(String.valueOf(feature.getHexColor()));
        cvFeature.setCardBackgroundColor(color);

        // set title
        tvFeatureTitle.setText(title);

        // set image
        Context context = holder.ivFeatureImage.getContext();
        ivFeatureImage.setImageResource(0); // clear image, to avoid memory leak
        if (title.contains("Recommend")) { // this may not be accurate - only for the recommended piece
            ivFeatureImage.setImageResource(R.drawable.ic_plus);
        } else {
            Picasso.with(context)
                    .load(imageUrl)
                    .transform(new CircleTransform())
                    .resize(75, 75)
                    .centerCrop()
                    .into(ivFeatureImage);
        }
    }

    @Override
    public int getItemCount() {
        return mFeatures.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvFeatureTitle) public TextView tvFeatureTitle;
        @Bind(R.id.ivFeatureImage) public ImageView ivFeatureImage;
        @Bind(R.id.cvFeature) public CardView cvFeature;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }
}



