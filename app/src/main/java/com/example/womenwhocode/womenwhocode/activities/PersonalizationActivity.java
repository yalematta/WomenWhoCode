package com.example.womenwhocode.womenwhocode.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.womenwhocode.womenwhocode.fragments.LogInFragment;
import com.example.womenwhocode.womenwhocode.fragments.Question1Fragment;
import com.example.womenwhocode.womenwhocode.fragments.Question2Fragment;
import com.example.womenwhocode.womenwhocode.fragments.Question3Fragment;
import com.example.womenwhocode.womenwhocode.R;

/**
 * Created by pnroy on 10/19/15.
 */
public class PersonalizationActivity extends AppCompatActivity {
    public Question1Fragment question1Fragment=new Question1Fragment();
    public Question2Fragment question2Fragment=new Question2Fragment();
    public Question3Fragment question3Fragment=new Question3Fragment();
    public LogInFragment logInFragment=new LogInFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalization);

        //create a fragment trasaction
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();


        //replace contents of frament with first fragment
        ft.replace(R.id.flPersonalization,question1Fragment);

        ft.addToBackStack(null);

        //commit the trasaction
        ft.commit();
    }

    public void goToNextPage(View view) {


        Fragment currentFragment =getSupportFragmentManager().findFragmentById(R.id.flPersonalization);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();


            //replace contents of frament with first fragment
        if (currentFragment instanceof Question1Fragment) {
            ft.replace(R.id.flPersonalization, question2Fragment);
        }
        else if(currentFragment instanceof Question2Fragment){
            ft.replace(R.id.flPersonalization, question3Fragment);
        }
        else{
            ft.replace(R.id.flPersonalization, logInFragment);
            Button btnNext=(Button)findViewById(R.id.btnNext);
            btnNext.setVisibility(View.INVISIBLE);
        }
            ft.addToBackStack(null);

            //commit the trasaction
            ft.commit();


    }
}
