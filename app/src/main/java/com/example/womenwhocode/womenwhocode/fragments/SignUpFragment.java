package com.example.womenwhocode.womenwhocode.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.activities.LoginEmailActivity;
import com.example.womenwhocode.womenwhocode.activities.SignUpEmailActivity;

/**
 * Created by pnroy on 10/23/15.
 */
public class SignUpFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        Button btnLogin = (Button) view.findViewById(R.id.btnEmailSignUp);


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SignUpEmailActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}