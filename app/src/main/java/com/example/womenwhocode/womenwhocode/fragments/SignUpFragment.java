package com.example.womenwhocode.womenwhocode.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.activities.SignUpEmailActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by pnroy on 10/23/15.
 */
public class SignUpFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        Button btnLogin = (Button) view.findViewById(R.id.btnEmailSignUp);
        Bundle bundle = this.getArguments();
       final String userAns = bundle.getString("userAns");
        ImageView img1=(ImageView)view.findViewById(R.id.img_1);
        ImageView img2=(ImageView)view.findViewById(R.id.img_2);
        ImageView img3=(ImageView)view.findViewById(R.id.img_3);
        Picasso.with(getContext())
                .load(R.raw.personalization).resize(100,100)
                .into(img1);
        Picasso.with(getContext())
                .load(R.raw.personalization).resize(100,100)
                .into(img2);
        Picasso.with(getContext())
                .load(R.raw.personalization).resize(100,100)
                .into(img3);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SignUpEmailActivity.class);
                i.putExtra("userAns",userAns);
                startActivity(i);

            }
        });
        return view;
    }
}