package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shehba.shahab on 10/17/15.
 */
public class FeaturesAdapter extends ArrayAdapter<Feature> {

    String title;
    String description;
    int awesomeCount;
    String imageUrl;

    public FeaturesAdapter(Context context, List<Feature> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Feature feature = getItem(position);

        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_feature, parent, false);
        }

        // Look up views to populate data
        ImageView ivFeaturePhoto = (ImageView) convertView.findViewById(R.id.ivFeaturePhoto);
        TextView tvFeatureTitle = (TextView) convertView.findViewById(R.id.tvFeatureTitle);
        TextView tvFeatureDescription = (TextView) convertView.findViewById(R.id.tvFeatureDescription);
        TextView tvAwesomeCount = (TextView) convertView.findViewById(R.id.tvAwesomeCount);

        // Clear out the image views
        ivFeaturePhoto.setImageResource(0);

        // Insert the model data into each of the view items
        title = feature.getTitle();
        description = feature.getDescription();
        awesomeCount = feature.getAwesomeCount();
        imageUrl = feature.getImageUrl();

        tvFeatureTitle.setText(title);
        tvFeatureDescription.setText(description);
        tvAwesomeCount.setText(Integer.valueOf(awesomeCount).toString());

        // Insert the image using picasso
        Picasso.with(getContext()).load(imageUrl).into(ivFeaturePhoto);

        return convertView;
    }
}
