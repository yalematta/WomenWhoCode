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

    private String title;
    private String imageUrl;
    private ViewHolder viewHolder;

    public FeaturesAdapter(Context context, List<Feature> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    private static class ViewHolder {
        TextView tvFeatureTitle;
        ImageView ivFeatureImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Feature feature = getItem(position);

        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_feature, parent, false);

            // Look up views to populate data
            viewHolder.tvFeatureTitle = (TextView) convertView.findViewById(R.id.tvFeatureTitle);
            viewHolder.ivFeatureImage = (ImageView) convertView.findViewById(R.id.ivFeatureImage);
        }

        // Insert the model data into each of the view items
        title = feature.getTitle();
        imageUrl = feature.getImageUrl();

        viewHolder.ivFeatureImage.setImageResource(0);
        viewHolder.tvFeatureTitle.setText(title);
        Picasso.with(getContext())
                .load(imageUrl)
                .resize(75, 75)
                .centerCrop()
                .into(viewHolder.ivFeatureImage);

        return convertView;
    }
}
