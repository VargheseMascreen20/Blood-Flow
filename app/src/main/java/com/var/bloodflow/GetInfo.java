package com.var.bloodflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.var.bloodflow.ModelClasses.Users;

public class GetInfo extends AppCompatActivity {
    EditText name, bloodgrp, dob, city;
    Button go1st;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);

        name = findViewById(R.id.name);
        bloodgrp = findViewById(R.id.bloodgroup);
        dob = findViewById(R.id.d_o_b);
        city = findViewById(R.id.city);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        int flag = getIntent().getExtras().getInt("FLAG");
        if (flag == 0) {
            name.setText(user.getDisplayName().toUpperCase());
            go1st = findViewById(R.id.go1st);
            go1st.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateGoogleUser();
                }
            });
        } else {
            go1st = findViewById(R.id.go1st);
            go1st.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUser();
                }
            });
        }


    }

    private void updateGoogleUser() {
        final String userid = id;
        final String fname = name.getText().toString().trim();
        final String emailid = user.getEmail();
        final String phoneno = "";
        final String place = city.getText().toString().trim();
        final String dateOB = dob.getText().toString().trim();
        final String gend = "";
        final String bldgrp = bloodgrp.getText().toString().trim();
        final String password = "";
        final String image = user.getPhotoUrl().toString();
        final String status = "offline";
        if (TextUtils.isEmpty(fname)) {
            name.setError("Name is Required");
            return;
        }
        if (TextUtils.isEmpty(bldgrp)) {
            bloodgrp.setError("dob is Required");
            return;
        }
        if (TextUtils.isEmpty(dateOB)) {
            dob.setError("Place is Required");
            return;
        }
        if (TextUtils.isEmpty(place)) {
            city.setError("dob is Required");
            return;
        }


        databaseReference = firebaseDatabase.getReference("users").child(id);
        Users user = new Users(userid, fname, emailid, dateOB, phoneno, bldgrp, gend, password, image, place, status);
        databaseReference.setValue(user);
        Intent i = new Intent(GetInfo.this, Nav.class);
        startActivity(i);

    }

    private void updateUser() {
        final String userid = id;
        final String fname = name.getText().toString().trim();
        final String emailid = "set your email";
        final String phoneno = getIntent().getExtras().getString("PHONE");
        final String place = city.getText().toString().trim();
        final String dateOB = dob.getText().toString().trim();
        final String gend = "";
        final String bldgrp = bloodgrp.getText().toString().trim();
        final String password = "";
        final String image = "https://firebasestorage.googleapis.com/v0/b/blood-flow-c80bc.appspot.com/o/image%2FUsers_Profile_Cover_Imgs%2FLogoMakr-4q1rZ1.png?alt=media&token=5bb4f49a-eb7c-48b3-99dc-a2590aab42a1";
        final String status = "offline";
        if (TextUtils.isEmpty(fname)) {
            name.setError("Name is Required");
            return;
        }
        if (TextUtils.isEmpty(bldgrp)) {
            bloodgrp.setError("dob is Required");
            return;
        }
        if (TextUtils.isEmpty(dateOB)) {
            dob.setError("Place is Required");
            return;
        }
        if (TextUtils.isEmpty(place)) {
            city.setError("dob is Required");
            return;
        }
        databaseReference = firebaseDatabase.getReference("users").child(id);
        Users user = new Users(userid, fname, emailid, dateOB, phoneno, bldgrp, gend, password, image, place, status);
        databaseReference.setValue(user);
        Intent i = new Intent(GetInfo.this, Nav.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
    }
}