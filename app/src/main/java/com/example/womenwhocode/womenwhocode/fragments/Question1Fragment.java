package com.example.womenwhocode.womenwhocode.fragments;

import android.os.Build;
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
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //return super.onCreateView(inflater, container, savedInstanceState);ssz
        View view = inflater.inflate(R.layout.fragment_question1, container, false);
        try {

            LinearLayout l = (LinearLayout) view.findViewById(R.id.chkLayout);
            Button btnNext = (Button) view.findViewById(R.id.btnNext);
            PersonalizationDetails pd = new PersonalizationDetails();
            //Toast.makeText(getContext(),String.valueOf(cnt),Toast.LENGTH_LONG);
            TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);

            Bundle bundle = this.getArguments();
            int clickcnt=bundle.getInt("clickCnt");
            String Question = bundle.getString("Questions");
            String[] Ans=bundle.getStringArray("Answers");


            btnNext.setId(clickcnt + 1);

            tvQuestion.setText(Question);
            tvQuestion.setTextColor(getResources().getColor(R.color.whitish));
            for (String An : Ans) {
                CheckBox cb = new CheckBox(getContext());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // known android bug, when you uncheck it goes doesn't convert properly
                    // http://stackoverflow.com/questions/28047291/api21-setbuttontintlist-on-checkbox
                    cb.setButtonTintList(getContext().getResources().getColorStateList(R.color.checkbox));
                }

                cb.setText(An);

                cb.setTextColor(getResources().getColor(R.color.whitish));
                l.addView(cb);
            }




        } catch (Exception e) {

        }


        //Toast.makeText(getContext(),String.valueOf(i),Toast.LENGTH_LONG).show();
        return view;

    }


}