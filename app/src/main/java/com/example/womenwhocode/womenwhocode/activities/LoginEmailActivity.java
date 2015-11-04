package com.example.womenwhocode.womenwhocode.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by pnroy on 10/19/15.
 */
public class LoginEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void Login(View view) {
        EditText etEmail=(EditText)findViewById(R.id.etEmail);
        String email=etEmail.getText().toString();
        EditText etPassword=(EditText)findViewById(R.id.etPwd);
        final String pwd=etPassword.getText().toString();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("email", email); //
        query.findInBackground(new FindCallback<ParseUser>() {

            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() != 0) {
                        ValidateUser(objects.get(0).getString("username"), pwd);
                        // Toast.makeText(getBaseContext(), objects.get(0).getString("username"), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Query unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ValidateUser(String username,String pwd) {
        ParseUser.logInInBackground(username, pwd, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    //Toast.makeText(getBaseContext(), "User Login successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginEmailActivity.this, TimelineActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                } else {
                    Toast.makeText(getBaseContext(), "User Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
