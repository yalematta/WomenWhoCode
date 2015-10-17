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
public class FeaturesArrayAdapter extends ArrayAdapter<Feature> {

    public FeaturesArrayAdapter(Context context, List<Feature> objects) {
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
        String title = feature.TITLE_KEY;
        String description = feature.DESCRIPTION_KEY;
        String awesomeCount = feature.AWESOME_COUNT_KEY;

        tvFeatureTitle.setText(title);
        tvFeatureDescription.setText(description);
        tvAwesomeCount.setText(awesomeCount);

        // Insert the image using picasso
        Picasso.with(getContext()).load(feature.IMAGE_URL_KEY).into(ivFeaturePhoto);

        return convertView;
    }
}
