package com.example.womenwhocode.womenwhocode.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.PersonalizationDetail;
import com.example.womenwhocode.womenwhocode.models.PersonalizationQuestionnaire;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by pnroy on 10/19/15.
 */
public class Question1Fragment extends Fragment {
Button btnNext;
PersonalizationDetail pd;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);ssz
        View view = inflater.inflate(R.layout.fragment_question1, container, false);
        try {

            LinearLayout l = (LinearLayout) view.findViewById(R.id.chkLayout);
            btnNext = (Button)view.findViewById(R.id.btnNext);
            pd=new PersonalizationDetail();
            //Toast.makeText(getContext(),String.valueOf(cnt),Toast.LENGTH_LONG);
            TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);

            Bundle bundle = this.getArguments();
                int clickcnt=bundle.getInt("clickCnt");
                String Question = bundle.getString("Questions");
                String[] Ans=bundle.getStringArray("Answers");


            btnNext.setId(clickcnt+1);

            tvQuestion.setText(Question);

            String[] ansArray = (String[]) Ans;
            for (int i = 0; i < ansArray.length; i++) {
                CheckBox cb = new CheckBox(getContext());
                cb.setText(ansArray[i]);
                l.addView(cb);
            }


            JSONArray ja = new JSONArray();

            LinearLayout formLayout = (LinearLayout) view.findViewById(R.id.chkLayout);

            for (int i = 0; i < formLayout.getChildCount(); i++) {
                CheckBox cb = (CheckBox) formLayout.getChildAt(i);
                if (cb.isChecked()) {
                    JSONObject jo = new JSONObject();
                    jo.put("1", cb.getText());
                    ja.put(jo);
                }
            }


        } catch (Exception e) {

        }


        //Toast.makeText(getContext(),String.valueOf(i),Toast.LENGTH_LONG).show();
        return view;

    }


}




