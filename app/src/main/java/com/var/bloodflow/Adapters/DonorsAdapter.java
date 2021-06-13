package com.var.bloodflow.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.var.bloodflow.MessageActivity;
import com.var.bloodflow.ModelClasses.Users;
import com.var.bloodflow.R;

import java.util.ArrayList;

public class DonorsAdapter extends RecyclerView.Adapter<DonorsAdapter.MyViewHolder> {
    ArrayList<Users> list;
    Context mContext;

    public DonorsAdapter(ArrayList<Users> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
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
        myViewHolder.messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(i);
            }
        });

    }

    private void sendMessage(int i) {
        Intent messageDonor = new Intent(mContext, MessageActivity.class);
        String userID = list.get(i).getUser_id();
        messageDonor.putExtra("userid", userID);
        messageDonor.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(messageDonor);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, blodGrp, place;
        Button messageBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.donorName);
            blodGrp = itemView.findViewById(R.id.donorBloodGrp);
            place = itemView.findViewById(R.id.donorPlace);
            messageBtn = itemView.findViewById(R.id.messageBtn);
        }
    }
}