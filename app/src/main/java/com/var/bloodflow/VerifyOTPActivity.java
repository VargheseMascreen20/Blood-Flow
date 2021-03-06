package com.var.bloodflow;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.var.bloodflow.ModelClasses.Users;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {

    private static long mTimeLeftInMillis = 120000;
    // variable for FirebaseAuth class
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    // variable for our text input
    // field for phone and OTP.
    private EditText edtPhone, edtOTP;
    private TextView countTime;
    // buttons for generating OTP and verifying OTP
    private Button verifyOTPBtn, generateOTPBtn;

    // string for storing our verification ID
    private String verificationId;
    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                edtOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(VerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        // below line is for getting instance
        // of our FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");


        // initializing variables for button and Edittext.
        edtPhone = findViewById(R.id.idEdtPhoneNumber1);
        edtOTP = findViewById(R.id.idEdtOtp1);
        verifyOTPBtn = findViewById(R.id.idBtnVerify);
        generateOTPBtn = findViewById(R.id.idBtnGetOtp);
        countTime = findViewById(R.id.countTime);

        // setting onclick listner for generate OTP button.
        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below line is for checking weather the user
                // has entered his mobile number or not.
                generateOTPBtn.setEnabled(false);
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(VerifyOTPActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    // if the text field is not empty we are calling our
                    // send OTP method for getting OTP from Firebase.
                    String phone = "+91" + edtPhone.getText().toString();
                    sendVerificationCode(phone);
                    countTime.setVisibility(View.VISIBLE);
                    new CountDownTimer(120000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mTimeLeftInMillis = millisUntilFinished;
                            updateCountDownText();
                        }

                        @Override
                        public void onFinish() {
                            countTime.setText("TimeOut");
                            countTime.setVisibility(View.INVISIBLE);
                        }
                    }.start();
                }
            }
        });

        // initializing on click listener
        // for verify otp button
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the OTP text field is empty or not.
                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(VerifyOTPActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    try {
                        verifyCode(edtOTP.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(VerifyOTPActivity.this, "INCORRECT OTP", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (task.getResult().getAdditionalUserInfo().isNewUser()) {

                                String phone = edtPhone.getText().toString();

                                Toast.makeText(VerifyOTPActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                final String userid = user.getUid();
                                final String fname = "";
                                final String emailid = "";
                                final String phoneno = phone;
                                final String dateOB = "";
                                final String gend = "";
                                final String bloodgrp = "";
                                final String password = "";
                                final String image = "";
                                final String city = "";
                                final String status = "offline";
                                Users users = new Users(userid, fname, emailid, dateOB, phoneno, bloodgrp, gend, password, image, city, status);
                                reference.child(userid).setValue(users);
                                Intent i = new Intent(VerifyOTPActivity.this, GetInfo.class);
                                int flag = 1;
                                i.putExtra("FLAG", flag);
                                i.putExtra("PHONE", phoneno);
                                startActivity(i);
                            }

                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(VerifyOTPActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
    }

    public void updateCountDownText() {
//        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds);
        countTime.setText(timeLeftFormatted);

    }

    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number, // first parameter is user's mobile number
                60, // second parameter is time limit for OTP
                // verification which is 60 seconds in our case.
                TimeUnit.SECONDS, // third parameter is for initializing units
                // for time period which is in seconds in our case.
                VerifyOTPActivity.this, // this task will be excuted on Main thread.
                mCallBack // we are calling callback method when we recieve OTP for
                // auto verification of user.
        );

    }

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
}
