package com.example.womenwhocode.womenwhocode.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by pnroy on 10/19/15.
 */
public class UserProfileActivity extends AppCompatActivity {
    EditText txtName;
    EditText txtEmail;
    String name="";
    String email="";
    String password="";
private static final int SELECTED_PICTURE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        Bundle extras = getIntent().getExtras();

        // TODO: needs a take photo intent for when where is no camera

        if (extras != null) {
            name = extras.getString("Name");
            email = extras.getString("Email");
            password = extras.getString("Password");
        }
        txtName=(EditText)findViewById(R.id.txtName);
        txtEmail=(EditText)findViewById(R.id.txtEmail);
        txtName.setText(name);
        txtEmail.setText(email);
//        txtPwd.setText(password);
    }

    public void OnFinalize(View view) {
        // Save user with the updated input


        Intent i = new Intent(UserProfileActivity.this, TimelineActivity.class);
        startActivity(i);
    }

    public void onSelectImage(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, SELECTED_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
