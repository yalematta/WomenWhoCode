package com.example.womenwhocode.womenwhocode.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.PersonalizationQuestionnaire;


public class LoginActivity extends AppCompatActivity {
    PersonalizationQuestionnaire questionnaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questionnaire = new PersonalizationQuestionnaire();
        questionnaire.build();

        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_laptop);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void JoinApp(View view) {
        // Puja, you can use this to get the questionnaire data.
        questionnaire.getQuestionnaire();

        Intent i = new Intent(this, PersonalizationActivity.class);
        startActivity(i);
    }

    public void LogInToApp(View view) {
//        ParseUser.logInInBackground("Puja Roy", "Puja", new LogInCallback() {
//            public void done(ParseUser user, ParseException e) {
//                if (user != null) {
//                    Intent i = new Intent(LoginActivity.this, TimelineActivity.class);
//                    startActivity(i);
//                } else {
//                    // Signup failed. Look at the ParseException to see what happened.
//                }
//            }
//        });
    }
}
