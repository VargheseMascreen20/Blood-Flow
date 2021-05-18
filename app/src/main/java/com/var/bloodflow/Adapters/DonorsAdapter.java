package com.var.bloodflow.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.var.bloodflow.ModelClasses.Users;
import com.var.bloodflow.R;

import java.util.ArrayList;

public class DonorsAdapter extends RecyclerView.Adapter<DonorsAdapter.MyViewHolder> {
    ArrayList<Users> list;

    public DonorsAdapter(ArrayList<Users> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.donors_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText("Name : " + list.get(i).getName());
        myViewHolder.blodGrp.setText("Blood Group : " + list.get(i).getBldgrp());
        myViewHolder.place.setText("Place : " + list.get(i).getPlace());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, blodGrp, place;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.donorName);
            blodGrp = itemView.findViewById(R.id.donorBloodGrp);
            place = itemView.findViewById(R.id.donorPlace);
        }
    }
}