package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by shehba.shahab on 10/17/15.
 */
public class FeaturesAdapter extends ArrayAdapter<Feature> {

    String title;
    String imageUrl;
    String description;
    int featureSubscribe = 0;

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
        TextView tvFeatureTitle = (TextView) convertView.findViewById(R.id.tvFeatureTitle);
        TextView tvSubscriberCount = (TextView) convertView.findViewById(R.id.tvSubscriberCount);

        // Insert the model data into each of the view items
        title = feature.getTitle();
        imageUrl = feature.getImageUrl();
        description = feature.getDescription();
        try {
            featureSubscribe = Subscribe.getCountFor(feature);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvFeatureTitle.setText(title);
        tvSubscriberCount.setText(String.valueOf(featureSubscribe + " FOLLOWERS"));

        return convertView;
    }
}
