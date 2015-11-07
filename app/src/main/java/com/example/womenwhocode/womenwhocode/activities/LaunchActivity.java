package com.example.womenwhocode.womenwhocode.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.activities.LoginActivity;
import com.example.womenwhocode.womenwhocode.activities.TimelineActivity;
import com.parse.ParseUser;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            Intent signUpIntent = new Intent(this, LoginActivity.class);
            startActivity(signUpIntent);
        } else {
            Intent timelineIntent = new Intent(this, TimelineActivity.class);
            startActivity(timelineIntent);
        }
    }
}
