package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shehba.shahab on 10/17/15.
 */
public class FeaturesAdapter extends  RecyclerView.Adapter<FeaturesAdapter.ViewHolder> {
    private List<Feature> mFeatures;

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public FeaturesAdapter(List<Feature> features) {
        mFeatures = features;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFeatureTitle;
        public ImageView ivFeatureImage;
        public CardView cvFeature;

        public ViewHolder(final View itemView) {
            super(itemView);

            tvFeatureTitle = (TextView) itemView.findViewById(R.id.tvFeatureTitle);
            ivFeatureImage = (ImageView) itemView.findViewById(R.id.ivFeatureImage);
            cvFeature = (CardView) itemView.findViewById(R.id.cvFeature);

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

    public FeaturesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View featureView = inflater.inflate(R.layout.item_feature, parent, false);

        ViewHolder viewHolder = new ViewHolder(featureView);
        return viewHolder;
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
}
