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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Network;
import com.example.womenwhocode.womenwhocode.models.Profile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnroy on 10/19/15.
 */
public class UserProfileActivity extends AppCompatActivity {
    EditText txtName;
    EditText txtEmail;
    EditText txtjobTitle;
    Spinner spnNetwork;
    String name="";
    String email="";
    String password="";
    Profile userProfile;
   String filepath;
    ArrayAdapter<String> adapterForSpinner;
private static final int SELECTED_PICTURE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        Bundle extras = getIntent().getExtras();
final ArrayList<String> networks=new ArrayList<String>();
        // TODO: needs a take photo intent for when where is no camera

        if (extras != null) {

            email = extras.getString("Email");

        }
        txtName=(EditText)findViewById(R.id.txtName);
        txtEmail=(EditText)findViewById(R.id.txtEmail);
        txtjobTitle=(EditText)findViewById(R.id.etJob);
        spnNetwork=(Spinner)findViewById(R.id.spnNetwork);
       //get the Network data
        Network ntwkAll=new Network();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Network");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> networkList, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < networkList.size(); i++) {
                        networks.add(networkList.get(i).getString("title"));
                    }

                    //  Toast.makeText(getApplicationContext(),networkList.get(0).getString("title"),Toast.LENGTH_LONG).show();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

       // txtName.setText(name);
        txtEmail.setText(email);
        networks.add("select");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, networks);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnNetwork.setAdapter(spinnerArrayAdapter);

        spnNetwork.setSelection(0);
//        txtPwd.setText(password);
    }

    public void OnFinalize(View view) {
        try {
            // Save user with the updated input in Profile model
            userProfile = new Profile();
            userProfile.setFullName(txtName.getText().toString());
            if(spnNetwork.getSelectedItem().toString().equals("select")){
                userProfile.setNetwork("");
            }
            else{
                userProfile.setNetwork(spnNetwork.getSelectedItem().toString());
            }

            userProfile.setJobTitle(txtjobTitle.getText().toString());
            userProfile.setUser(ParseUser.getCurrentUser());
//            userProfile.setImageUrl(filepath);
            userProfile.save();
            Intent i = new Intent(UserProfileActivity.this, TimelineActivity.class);
            startActivity(i);
        }catch(ParseException p){

        }
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
                 filepath = cursor.getString(columnIndex);
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
