package com.var.bloodflow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.var.bloodflow.Adapters.DonorsAdapter;
import com.var.bloodflow.MainFragments.HomeFragment;
import com.var.bloodflow.MainFragments.MessagingFragment;
import com.var.bloodflow.ModelClasses.Users;

import java.util.ArrayList;

public class DonorsList extends AppCompatActivity {

    String bloodGroup, city;
    TextView resultText, moreResult, moreResultText;
    ImageButton messageButton;

    private ArrayList<Users> list;
    private RecyclerView recyclerView;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors_list);
        messageButton = findViewById(R.id.message_Btn);
//        Intent messageActivity = new Intent(DonorsList.Message.class);
//        startActivity(Message.class);
        moreResult = findViewById(R.id.moreResults);
        moreResultText = findViewById(R.id.moreResultsText);

        bloodGroup = getIntent().getExtras().getString("blood group");
        city = getIntent().getExtras().getString("City");
        resultText = findViewById(R.id.resText);
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.keepSynced(true);

        recyclerView = findViewById(R.id.donors_list);
//        donors_list = (RecyclerView) findViewById(R.id.donors_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultText.setText("You are viewing the result for " + bloodGroup + " blood donors in " + city + " city.");

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                MessagingFragment fragment = new MessagingFragment();
                fragmentTransaction.replace(R.id.donors_layout, fragment, "");
                fragmentTransaction.commit();
            }
        });

    }

    public void search(String bloodGroup, String city) {
        ArrayList<Users> myList = new ArrayList<>();
        for (Users object : list) {
            if (object.getBldgrp().toLowerCase().equals(bloodGroup.toLowerCase())
                    && (object.getPlace().toLowerCase().equals(city.toLowerCase())
            )) {
                myList.add(object);
            }
        }

        if (myList.isEmpty()) {
            myList.clear();
            Toast.makeText(DonorsList.this, "Unfortunately we have no " + bloodGroup + " blood donors in " + city + " city.", Toast.LENGTH_LONG).show();
            moreResultText.setVisibility(View.VISIBLE);
            moreResultText.setEnabled(true);
            moreResult.setVisibility(View.VISIBLE);
            moreResult.setEnabled(true);
            moreResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int flag = 2;
                    Intent i = new Intent(DonorsList.this, Map.class);
                    i.putExtra("City", city);
                    i.putExtra("FLAG", flag);
                    startActivity(i);
                }
            });

        }
        DonorsAdapter adapterClass = new DonorsAdapter(myList, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterClass);
    }

    public void onStart() {
        super.onStart();
        if (reference != null) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds.getValue(Users.class));
                        }
                        DonorsAdapter adapterClass = new DonorsAdapter(list, getApplicationContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapterClass);
                        search(bloodGroup, city);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
