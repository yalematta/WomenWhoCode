package com.example.womenwhocode.womenwhocode.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.womenwhocode.womenwhocode.fragments.LogInFragment;
import com.example.womenwhocode.womenwhocode.fragments.Question1Fragment;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.fragments.SignUpFragment;
import com.example.womenwhocode.womenwhocode.models.PersonalizationDetails;
import com.example.womenwhocode.womenwhocode.models.PersonalizationQuestionnaire;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by pnroy on 10/19/15.
 */
public class PersonalizationActivity extends AppCompatActivity {
    public Question1Fragment question1Fragment = new Question1Fragment();

    public LogInFragment logInFragment = new LogInFragment();
    public SignUpFragment signUpFragment = new SignUpFragment();
    PersonalizationQuestionnaire pq;
    PersonalizationDetails pd;
    int pageCnt = 0;
    JSONArray arr1=null;
    JSONArray arr2=null;
    JSONArray arr3=null;
    ArrayList<String> Question = new ArrayList<String>();
    ArrayList<String[]> Ans = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pq = new PersonalizationQuestionnaire();
        pq.build();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalization);


        Bundle extras = getIntent().getExtras();
        String type = "";
        if (extras != null) {
            type = extras.getString("type");
        }

        //create a fragment transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        //for join us
        setFragmentData();
        if (type.equals("Join")) {
            //replace contents of fragment with first fragment
            ft.replace(R.id.flPersonalization, question1Fragment);
            Bundle bundle = new Bundle();
            bundle.putString("Questions", Question.get(0));
            bundle.putStringArray("Answers", Ans.get(0));
            bundle.putInt("clickCnt", 0);

            question1Fragment.setArguments(bundle);
        }//for login
        else {
            ft.replace(R.id.flPersonalization, logInFragment);
        }

        //commit the transaction
        ft.commit();
    }




    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void setFragmentData() {
        pd = new PersonalizationDetails();
        pq.build();
        HashMap<String, String[]> ques = pq.getQuestionnaire();

        Iterator it = ques.entrySet().iterator();
        try {
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                Question.add(pair.getKey().toString());
                Ans.add((String[]) pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException

            }
        } catch (Exception e) {

        }
    }

    public void goToNextPage(View view) {
        try {

            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flPersonalization);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            Bundle bundle = new Bundle();

            switch (view.getId()) {
                case 1:
                    //ParseObject.unpinAll("pDetail");
                    Question1Fragment question2Fragment = new Question1Fragment();
                    ft.replace(R.id.flPersonalization, question2Fragment);
                    bundle.putString("Questions", Question.get(1));
                    bundle.putStringArray("Answers", Ans.get(1));
                    bundle.putInt("clickCnt", 1);
                    question2Fragment.setArguments(bundle);
                     arr1 = getAnswersArray(view);
                    ft.addToBackStack(null);
                    break;
                case 2:
                    Question1Fragment question3Fragment = new Question1Fragment();
                    ft.replace(R.id.flPersonalization, question3Fragment);
                    bundle.putString("Questions", Question.get(2));
                    bundle.putStringArray("Answers", Ans.get(2));
                    bundle.putInt("clickCnt", 2);
                    question3Fragment.setArguments(bundle);
                     arr2 = getAnswersArray(view);
                    ft.addToBackStack(null);
                    break;
                case 3:
                     arr3 = getAnswersArray(view);
                    JSONArray finalArray=concatArray(arr1,arr2,arr3);
                    ft.replace(R.id.flPersonalization, signUpFragment);
                    bundle.putString("userAns", finalArray.toString());
                    signUpFragment.setArguments(bundle);
                    ft.addToBackStack(null);
                    break;
                default:
            }

            ft.commit();
        } catch (Exception e) {

        }
    }

    public JSONArray getAnswersArray(View view) {
        final JSONArray ja = new JSONArray();
        RelativeLayout formLayout = (RelativeLayout) view.getParent();
        EditText etOther=(EditText)formLayout.findViewById(R.id.etOther);
        LinearLayout layout = (LinearLayout) formLayout.findViewById(R.id.chkLayout);

        for (int i = 0; i < layout.getChildCount(); i++) {
            CheckBox cb = (CheckBox) layout.getChildAt(i);
            if (cb.isChecked()) {

                ja.put(cb.getText());

            }
        }
        if(etOther.getText().length()>0) {
            ja.put(etOther.getText());
        }
        return ja;
    }

    private JSONArray concatArray(JSONArray... arrs)
            throws JSONException {
        JSONArray result = new JSONArray();
        for (JSONArray arr : arrs) {
            for (int i = 0; i < arr.length(); i++) {
                result.put(arr.get(i));
            }
        }
        return result;
    }


}
