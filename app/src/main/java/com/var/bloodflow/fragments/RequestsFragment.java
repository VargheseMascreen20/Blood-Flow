package com.var.bloodflow.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.var.bloodflow.MainActivity2;
import com.var.bloodflow.MakeRequest;
import com.var.bloodflow.ModelClasses.MakeRequestModel;
import com.var.bloodflow.MyRequests;
import com.var.bloodflow.R;

public class RequestsFragment extends Fragment {
    private RecyclerView req_list;
    private DatabaseReference reference;

    private FloatingActionButton fabAdd;

    public RequestsFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);

        reference = FirebaseDatabase.getInstance().getReference().child("request");
        reference.keepSynced(true);

        req_list = (RecyclerView) view.findViewById(R.id.req_list);
        req_list.setHasFixedSize(true);
        req_list.setLayoutManager(new LinearLayoutManager(getActivity()));


        fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakeRequest.class);
                startActivity(i);
            }
        });
        return view;
    }

    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<MakeRequestModel, RequestsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MakeRequestModel, RequestsViewHolder>
                (MakeRequestModel.class, R.layout.request_row, RequestsViewHolder.class, reference) {
            @Override
            protected void populateViewHolder(RequestsViewHolder requestsViewHolder, MakeRequestModel model, int i) {
                requestsViewHolder.setName(model.getPatientName());
                requestsViewHolder.setpBloodGrp(model.getBlood_group());
                requestsViewHolder.setHospital(model.getHospitaltName());
                requestsViewHolder.setUnits(model.getRequired_units());
                requestsViewHolder.setPlace(model.getCity());
            }
        };
        req_list.setAdapter(firebaseRecyclerAdapter);
    }

    public static class RequestsViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public RequestsViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setName(String pname) {
            TextView name = mview.findViewById(R.id.name);
            name.setText("Name : " + pname);
        }

        public void setpBloodGrp(String pBloodGrp) {
            TextView bldgrp = mview.findViewById(R.id.blood_group);
            bldgrp.setText("Blood Group : " + pBloodGrp);
        }

        public void setHospital(String hospital) {
            TextView hospt = mview.findViewById(R.id.hospital);
            hospt.setText("Hospital : " + hospital);
        }

        public void setUnits(String units) {
            TextView noOfUnits = mview.findViewById(R.id.units);
            noOfUnits.setText("Units Required : " + units);
        }

        public void setPlace(String place) {
            TextView usrPlace = mview.findViewById(R.id.place);
            usrPlace.setText("Place : " + place);
        }
    }

}
