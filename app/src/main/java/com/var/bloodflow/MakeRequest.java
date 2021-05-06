package com.var.bloodflow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.var.bloodflow.ModelClasses.MakeRequestModel;

public class MakeRequest extends AppCompatActivity {
    EditText patient_name, hospt_name, blood_grp, no_of_units, usr_op_no, usr_city, req_date;
    Button post_req;

    FirebaseAuth fAuth;
    DatabaseReference reference;

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

        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("request");

        post_req = findViewById(R.id.post_req);
        post_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String patientName = patient_name.getText().toString().trim();
                final String hospitaltName = hospt_name.getText().toString().trim();
                final String blood_group = blood_grp.getText().toString().trim();
                final String required_units = no_of_units.getText().toString().trim();
                final String op_number = usr_op_no.getText().toString().trim();
                final String date = req_date.getText().toString().trim();
                final String city = usr_city.getText().toString().trim();
                MakeRequestModel request = new MakeRequestModel(patientName, hospitaltName, blood_group, required_units, op_number, date, city);
                reference.child(patientName).setValue(request);
            }

        });

    }
}