package com.example.womenwhocode.womenwhocode.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.womenwhocode.womenwhocode.fragments.LogInFragment;
import com.example.womenwhocode.womenwhocode.fragments.Question1Fragment;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.fragments.SignUpFragment;
import com.example.womenwhocode.womenwhocode.models.PersonalizationDetail;
import com.example.womenwhocode.womenwhocode.models.PersonalizationQuestionnaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by pnroy on 10/19/15.
 */
public class PersonalizationActivity extends AppCompatActivity {
    public Question1Fragment question1Fragment=new Question1Fragment();

    public LogInFragment logInFragment=new LogInFragment();
    public SignUpFragment signUpFragment=new SignUpFragment();
    PersonalizationQuestionnaire pq;
    PersonalizationDetail pd;
    Button btnNext;

    ArrayList<String> Question = new ArrayList<String>();
    ArrayList<String[]> Ans = new ArrayList<String[]>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pq = new PersonalizationQuestionnaire();
        pq.build();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalization);

      //  btnNext=(Button)findViewById(R.id.btnNext);
        Bundle extras=getIntent().getExtras();
        String type="";
        if(extras!=null){
            type=extras.getString("type");
        }

        //create a fragment transaction
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
//for join us
        setFragmentData();
        if(type.equals("Join")) {
            //replace contents of fragment with first fragment

            ft.replace(R.id.flPersonalization, question1Fragment);
            Bundle bundle = new Bundle();
            bundle.putString("Questions", Question.get(0));
            bundle.putStringArray("Answers", Ans.get(0));
            question1Fragment.setArguments(bundle);
           // btnNext.setVisibility(View.VISIBLE);
        }//for login
        else{
            ft.replace(R.id.flPersonalization, logInFragment);
          //  btnNext.setVisibility(View.INVISIBLE);
        }

        ft.addToBackStack(null);

        //commit the transaction
        ft.commit();
    }

    public void setFragmentData() {
        pd = new PersonalizationDetail();
        pq.build();
        HashMap<String, String[]> ques = pq.getQuestionnaire();

        Iterator it = ques.entrySet().iterator();
        try {
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();

                //tvQuestion.setText(pair.getKey().toString());
                Question.add(pair.getKey().toString());
                Ans.add((String[]) pair.getValue());


                it.remove(); // avoids a ConcurrentModificationException

            }
        }
        catch(Exception e){

        }
    }

   public void goToNextPage(View view) {

       //Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_LONG).show();

        Fragment currentFragment =getSupportFragmentManager().findFragmentById(R.id.flPersonalization);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
       Bundle bundle = new Bundle();
//Toast.makeText(getApplicationContext(), String.valueOf(view.getId()),Toast.LENGTH_LONG).show();
       switch(view.getId()){
           case 1:
               Question1Fragment question2Fragment=new Question1Fragment();
              ft.replace(R.id.flPersonalization, question2Fragment);

               bundle.putString("Questions", Question.get(1));
               bundle.putStringArray("Answers", Ans.get(1));
               bundle.putInt("clickCnt",1);
               question2Fragment.setArguments(bundle);
               break;
           case 2:
               Question1Fragment question3Fragment=new Question1Fragment();
               ft.replace(R.id.flPersonalization, question3Fragment);

               bundle.putString("Questions", Question.get(2));
               bundle.putStringArray("Answers", Ans.get(2));
               bundle.putInt("clickCnt",2);
               question3Fragment.setArguments(bundle);
               break;
           case 3:
               ft.replace(R.id.flPersonalization, signUpFragment);

       }

            ft.addToBackStack(null);

            //commit the transaction
            ft.commit();
    }
}
