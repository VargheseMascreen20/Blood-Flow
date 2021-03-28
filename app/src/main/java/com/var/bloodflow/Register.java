package com.var.bloodflow;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    EditText email, pass, phno, bldgrp,dob,gender,name;
    public static final String TAG = "TAG";
    Button register,bck;
    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore fStore;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        phno = findViewById(R.id.phno);
        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);
        bldgrp = findViewById(R.id.bldGrp);
        bck = findViewById(R.id.bck);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(com.var.bloodflow.Register.this, com.var.bloodflow.Login.class));
            finish();
        }
        register = (Button)findViewById(R.id.register1);
        register.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                final String fname = name.getText().toString().trim();
                final String emailid = email.getText().toString().trim();
                final String password = pass.getText().toString().trim();
                final String phoneno =  phno.getText().toString().trim();
                final String dateOB =   dob.getText().toString().trim();
                final String gend = gender.getText().toString().trim();
                final String bloodgrp = bldgrp.getText().toString().trim();

                if (TextUtils.isEmpty(emailid)) {
                    email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    pass.setError("Password is Required");
                    return;
                }
                if (password.length() < 6) {
                    pass.setError("Password Length must be >= 6 Characters");
                    return;
                }
                else {
                    fAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override

                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser fuser = fAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(com.var.bloodflow.Register.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                    }
                                });

                                Toast.makeText(com.var.bloodflow.Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                               /* DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("email", email);
                                user.put("phone", phno);
                                databaseReference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                              */
                                Users users=new Users(fname,emailid,dateOB,phoneno,bloodgrp,gend,password);
                                reference.child(phoneno).setValue(users);
                                Toast.makeText(com.var.bloodflow.Register.this, "User Created.", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            } else {
                                Toast.makeText(com.var.bloodflow.Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                }
            }
        });

    }
}