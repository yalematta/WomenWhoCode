package com.example.womenwhocode.womenwhocode.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.womenwhocode.womenwhocode.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by pnroy on 10/22/15.
 */
public class SignUpEmailActivity extends AppCompatActivity {
    private String userAns = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sign_up);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userAns = extras.getString("userAns");
        }
    }

    @Override
    public void onBackPressed() {
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void signUpUser(View view) {

    //ParseUser userDetail = new ParseUser();
    EditText tvName = (EditText) findViewById(R.id.txtName);
    EditText tvEmail = (EditText) findViewById(R.id.txtEmail);
    EditText tvPassword = (EditText) findViewById(R.id.txtPwd);
    final String name = tvName.getText().toString();
    final String email = tvEmail.getText().toString();
    final String password = tvPassword.getText().toString();
//create the parse user and save it
   ParseUser user = new ParseUser();
    user.setUsername(tvName.getText().toString());
    user.setPassword(tvPassword.getText().toString());
    user.setEmail(tvEmail.getText().toString());



    user.signUpInBackground(new SignUpCallback() {
        public void done(ParseException e) {
            if (e == null) {
                try {
//                    Toast.makeText(getBaseContext(), "User created", Toast.LENGTH_SHORT).show();
                    // [ ] TODO: auto subscribe user to features with auto subscribe true

                    Intent i = new Intent(SignUpEmailActivity.this, UserProfileActivity.class);
                    i.putExtra("Name", name);
                    i.putExtra("Email", email);
                    i.putExtra("Password", password);
                    i.putExtra("userAns",userAns);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }catch(Exception ex){

                }
            } else {
//                Toast.makeText(getBaseContext(), "User creation failed" + e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("User Creation failed", e.toString());
            }
        }
    });



    }
}


