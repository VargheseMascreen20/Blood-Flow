package com.var.bloodflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.var.bloodflow.ModelClasses.MakeRequestModel;
import com.var.bloodflow.ModelClasses.Users;
import com.var.bloodflow.fragments.RequestsFragment;

public class DonorsList extends AppCompatActivity {
    Button messageBtn;
    private RecyclerView donors_list;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors_list);
        Button messageBtn = findViewById(R.id.message_Btn);
//        Intent messageActivity = new Intent(DonorsList.Message.class);
//        startActivity(Message.class);
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.keepSynced(true);

        donors_list = (RecyclerView) findViewById(R.id.donors_list);
        donors_list.setHasFixedSize(true);
        donors_list.setLayoutManager(new LinearLayoutManager(this));

    }

    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Users, DonorsList.DonorsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, DonorsList.DonorsViewHolder>
                (Users.class, R.layout.donors_row, DonorsList.DonorsViewHolder.class, reference) {
            @Override
            protected void populateViewHolder(DonorsViewHolder donorsViewHolder, Users users, int i) {
                donorsViewHolder.setName(users.getName());
                donorsViewHolder.setDBloodGrp(users.getBldgrp());
                donorsViewHolder.setPlace(users.getPlace());
            }

        };
        donors_list.setAdapter(firebaseRecyclerAdapter);
    }

    public static class DonorsViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public DonorsViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setName(String dName) {
            TextView name = mview.findViewById(R.id.donorName);
            name.setText("Name : " + dName);
        }

        public void setDBloodGrp(String bldGrp) {
            TextView donorBloodGrp = mview.findViewById(R.id.donorBloodGrp);
            donorBloodGrp.setText("Units Required : " + bldGrp);
        }

        public void setPlace(String place) {
            TextView donorPlace = mview.findViewById(R.id.donorPlace);
            donorPlace.setText("Place : " + place);
        }
    }
}