package com.example.womenwhocode.womenwhocode.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.squareup.picasso.Picasso;

/**
 * Created by shehba.shahab on 10/18/15.
 */
public class FeatureDetailActivity extends AppCompatActivity {

    String title;
    String description;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_detail);

        // Look up views to populate data
        ImageView ivFeaturePhoto = (ImageView) findViewById(R.id.ivFeaturePhoto);
        TextView tvFeatureTitle = (TextView) findViewById(R.id.tvFeatureTitle);
        TextView tvFeatureDescription = (TextView) findViewById(R.id.tvFeatureDescription);

        // Clear out the image views
        ivFeaturePhoto.setImageResource(0);

        // Insert the model data into each of the view items
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        imageUrl = getIntent().getStringExtra("imageUrl");

        tvFeatureTitle.setText(title);
        tvFeatureDescription.setText(description);

        // Insert the image using picasso
        Picasso.with(getApplicationContext()).load(imageUrl).into(ivFeaturePhoto);

        // Set Activity Title to Feature Title
        this.setTitle(title);
    }
}



