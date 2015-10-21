package com.example.womenwhocode.womenwhocode.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

/**
 * Created by pnroy on 10/19/15.
 */
public class UserProfileActivity extends AppCompatActivity {
     TextView txtName;
    TextView txtEmail;
    TextView txtPwd;
    String name="";
    String email="";
    String password="";
private static final int SELECTED_PICTURE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("Name");
             email = extras.getString("Email");
            password = extras.getString("Password");
        }
        txtName=(TextView)findViewById(R.id.txtName);
        txtEmail=(TextView)findViewById(R.id.txtEmail);
         txtPwd=(TextView)findViewById(R.id.txtPwd);
        txtName.setText(name);
        txtEmail.setText(email);
        txtPwd.setText(password);

    }

    public void OnFinalize(View view) {
        ParseUser user = new ParseUser();
        user.setUsername(name);
        user.setPassword(password);
        user.setEmail(email);


// Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {

                    Intent i = new Intent(UserProfileActivity.this, TimelineActivity.class);
                    startActivity(i);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }

    public void selectImage(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, SELECTED_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == SELECTED_PICTURE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filepath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.ivphoto);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(filepath));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
