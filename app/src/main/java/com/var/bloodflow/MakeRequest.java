package com.var.bloodflow;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.var.bloodflow.ModelClasses.MakeRequestModel;

import java.util.ArrayList;
import java.util.List;

public class MakeRequest extends AppCompatActivity {
    EditText patient_name, hospt_name, blood_grp, no_of_units, usr_op_no, usr_city, req_date;
    Button post_req, deleteBtn;
    TextView title1;

    FirebaseAuth fAuth;
    DatabaseReference reference;
    List<String> op_no_list;
    //    long maxId=0;
    MakeRequestModel makeRequest;
    int flag;
    String name, hospName, bldGroup, reqUnits, opNumber, city, reqDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);

        patient_name = findViewById(R.id.patient_name);
        hospt_name = findViewById(R.id.hospt_name);
        blood_grp = findViewById(R.id.blood_grp);
        no_of_units = findViewById(R.id.no_of_units);
        usr_op_no = findViewById(R.id.usr_op_no);
        usr_city = findViewById(R.id.usr_city);
        req_date = findViewById(R.id.req_date);
        op_no_list = new ArrayList<>();
        post_req = findViewById(R.id.post_req);
        title1 = findViewById(R.id.title1);
        deleteBtn = findViewById(R.id.deleteBtn);

        makeRequest = new MakeRequestModel();
        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("request");
        checkIntent();
        if (flag == 1) {
            title1.setText("EDIT REQUEST");
            post_req.setText("EDIT REQUEST");
            name = getIntent().getExtras().getString("NAME");
            hospName = getIntent().getExtras().getString("HOSPITAL NAME");
            bldGroup = getIntent().getExtras().getString("BLOOD GROUP");
            reqUnits = getIntent().getExtras().getString("REQUIRED UNITS");
            opNumber = getIntent().getExtras().getString("OP NUMBER");
            city = getIntent().getExtras().getString("CITY");
            reqDate = getIntent().getExtras().getString("DATE");

            patient_name.setText(name);
            hospt_name.setText(hospName);
            blood_grp.setText(bldGroup);
            no_of_units.setText(reqUnits);
            usr_op_no.setText(opNumber);
            usr_city.setText(city);
            req_date.setText(reqDate);
            deleteBtn.setVisibility(View.VISIBLE);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MakeRequest.this);
                    builder.setTitle("DELETE REQUEST");
                    builder.setIcon(R.drawable.ic_delete);
                    builder.setMessage("Are you Sure you want to Delete this request ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reference.child(opNumber).removeValue();
                            finish();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            post_req.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MakeRequest.this);
                    builder.setTitle("EDIT REQUEST");
                    builder.setIcon(R.drawable.ic_edit_blk);
                    builder.setMessage("Are you Sure you want to Edit this request ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            postRequest();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        } else {
            post_req.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postRequest();
                }
            });

        }
    }

    private void postRequest() {
        final String userId = fAuth.getCurrentUser().getUid().trim();
        final String patientName = patient_name.getText().toString().trim();
        final String hospitaltName = hospt_name.getText().toString().trim();
        final String blood_group = blood_grp.getText().toString().trim();
        final String required_units = no_of_units.getText().toString().trim();
        final String op_number = usr_op_no.getText().toString().trim();
        final String date = req_date.getText().toString().trim();
        final String city = usr_city.getText().toString().trim();

        if (TextUtils.isEmpty(patientName)) {
            patient_name.setError("This field is Required");
            return;
        }

        if (TextUtils.isEmpty(hospitaltName)) {
            hospt_name.setError("This field is Required");
            return;
        }
        if (TextUtils.isEmpty(blood_group)) {
            blood_grp.setError("This field is Required");
            return;
        }
        if (TextUtils.isEmpty(required_units)) {
            no_of_units.setError("This field is Required");
            return;
        }
        if (TextUtils.isEmpty(date)) {
            req_date.setError("This field is Required");
            return;
        }
        if (TextUtils.isEmpty(city)) {
            usr_city.setError("This field is Required");
            return;
        }
        if (TextUtils.isEmpty(op_number)) {
            usr_city.setError("This field is Required");
            return;
        } else {
            makeRequest(userId, patientName, hospitaltName, blood_group, required_units, op_number, date, city);
        }
    }

    private void checkIntent() {
        try {
            flag = getIntent().getExtras().getInt("FLAG");
        } catch (Exception e) {
            flag = 0;
        }
    }

    private void makeRequest(String userId, String patientName, String hospitaltName, String blood_group, String required_units, String op_number, String date, String city) {
        MakeRequestModel request = new MakeRequestModel(userId, patientName, hospitaltName, blood_group, required_units, op_number, date, city);
        reference.child(op_number).setValue(request);
//        reference.child(String.valueOf(maxId+1)).setValue(request);
        finish();
        MakeRequest.super.onBackPressed();
    }
}