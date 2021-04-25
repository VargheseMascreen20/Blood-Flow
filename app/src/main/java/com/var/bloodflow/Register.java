package com.var.bloodflow;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.var.bloodflow.ModelClasses.Users;

import java.text.MessageFormat;
import java.util.Calendar;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";

    EditText email, pass, phno, bldgrp, dob, gender, name;
    Button register, bck;
    String userID;

    ImageView cal;
    DatabaseReference reference;
    private int mDate, mMonth, mYear;
    private Context mContext;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;

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
        reference = FirebaseDatabase.getInstance().getReference("users");
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(Register.this, Login.class));
            finish();
        }
        cal = findViewById(R.id.date_picker);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal = Calendar.getInstance();
                mDate = cal.get(Calendar.DATE);
                mMonth = cal.get(Calendar.MONTH);
                mYear = cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dob.setText(MessageFormat.format("{0}-{1}-{2}", date, month, year));
                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        register = (Button) findViewById(R.id.register1);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fname = name.getText().toString().trim();
                final String emailid = email.getText().toString().trim();
                final String password = pass.getText().toString().trim();
                final String phoneno = phno.getText().toString().trim();
                final String dateOB = dob.getText().toString().trim();
                final String gend = gender.getText().toString().trim();
                final String bloodgrp = bldgrp.getText().toString().trim();
                final String image = "";
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
                } else {
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

                                Users users = new Users(userID, fname, emailid, dateOB, phoneno, bloodgrp, gend, password, image);
                                reference.child(phoneno).setValue(users);
                                Toast.makeText(com.var.bloodflow.Register.this, "User Created.", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
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