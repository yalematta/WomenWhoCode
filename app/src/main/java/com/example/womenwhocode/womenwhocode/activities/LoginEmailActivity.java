package com.example.womenwhocode.womenwhocode.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.womenwhocode.womenwhocode.R;

/**
 * Created by pnroy on 10/19/15.
 */
public class LoginEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emaillogin);
    }

    public void signUpUser(View view) {
        //ParseUser userDetail = new ParseUser();
        EditText tvName = (EditText) findViewById(R.id.txtName);
        EditText tvEmail = (EditText) findViewById(R.id.txtEmail);
        EditText tvPassword = (EditText) findViewById(R.id.txtPwd);

        String name=tvName.getText().toString();
        String email=tvEmail.getText().toString();
        String password=tvPassword.getText().toString();
        Intent i = new Intent(LoginEmailActivity.this, UserProfileActivity.class);
        i.putExtra("Name",name);
        i.putExtra("Email",email);
        i.putExtra("Password",password);
        startActivity(i);
    }
}
