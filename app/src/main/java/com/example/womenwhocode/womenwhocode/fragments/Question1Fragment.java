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

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.PersonalizationDetails;

/**
 * Created by pnroy on 10/19/15.
 */
public class Question1Fragment extends Fragment {
private Button btnNext;
private PersonalizationDetails pd;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //return super.onCreateView(inflater, container, savedInstanceState);ssz
        View view = inflater.inflate(R.layout.fragment_question1, container, false);
        try {

            LinearLayout l = (LinearLayout) view.findViewById(R.id.chkLayout);
            btnNext = (Button)view.findViewById(R.id.btnNext);
            pd=new PersonalizationDetails();
            //Toast.makeText(getContext(),String.valueOf(cnt),Toast.LENGTH_LONG);
            TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);

            Bundle bundle = this.getArguments();
                int clickcnt=bundle.getInt("clickCnt");
                String Question = bundle.getString("Questions");
                String[] Ans=bundle.getStringArray("Answers");


            btnNext.setId(clickcnt+1);

            tvQuestion.setText(Question);
            tvQuestion.setTextColor(getResources().getColor(R.color.whitish));
            for (int i = 0; i < Ans.length; i++) {
                CheckBox cb = new CheckBox(getContext());
                cb.setText(Ans[i]);

                cb.setTextColor(getResources().getColor(R.color.whitish));
                l.addView(cb);
            }




        } catch (Exception e) {

        }


        //Toast.makeText(getContext(),String.valueOf(i),Toast.LENGTH_LONG).show();
        return view;

    }


}




