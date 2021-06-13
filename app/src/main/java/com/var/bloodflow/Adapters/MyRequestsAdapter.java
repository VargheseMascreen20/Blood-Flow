package com.var.bloodflow.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.var.bloodflow.AcceptedDonorsList;
import com.var.bloodflow.DonorsList;
import com.var.bloodflow.MakeRequest;
import com.var.bloodflow.ModelClasses.AcceptedRequestsModel;
import com.var.bloodflow.ModelClasses.Chat;
import com.var.bloodflow.ModelClasses.MakeRequestModel;
import com.var.bloodflow.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyRequestsAdapter extends RecyclerView.Adapter<MyRequestsAdapter.MyViewHolder> {
    List<MakeRequestModel> list;
    Context mContext;

    public MyRequestsAdapter(Context mContext, List<MakeRequestModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void MyRequestAdapter(ArrayList<MakeRequestModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_requests_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText("Name : " + list.get(i).getPatientName());
        myViewHolder.blodGrp.setText("Blood Group : " + list.get(i).getBlood_group());
        myViewHolder.hospt.setText("Hospital Name : " + list.get(i).getHospitaltName());
        myViewHolder.units.setText("Units : " + list.get(i).getRequired_units());
        myViewHolder.place.setText("Place : " + list.get(i).getCity());
        myViewHolder.date.setText("Date : " + list.get(i).getDate());

        final LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        myViewHolder.editRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchIntent(i);
            }
        });
        myViewHolder.donorsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AcceptedDonorsList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String opNumber = list.get(i).getOp_number();
                String userID = list.get(i).getUser_id();
                intent.putExtra("PATIENT USERID", userID);
                intent.putExtra("OP NUMBER", opNumber);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void launchIntent(int i) {
        Intent intent = new Intent(mContext, MakeRequest.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        int flag = 1;
        String name = list.get(i).getPatientName();
        String hostptName = list.get(i).getHospitaltName();
        String bloodGroup = list.get(i).getBlood_group();
        String requiredUnits = list.get(i).getRequired_units();
        String opNumber = list.get(i).getOp_number();
        String city = list.get(i).getCity();
        String date = list.get(i).getDate();
        intent.putExtra("FLAG", flag);
        intent.putExtra("NAME", name);
        intent.putExtra("HOSPITAL NAME", hostptName);
        intent.putExtra("BLOOD GROUP", bloodGroup);
        intent.putExtra("REQUIRED UNITS", requiredUnits);
        intent.putExtra("OP NUMBER", opNumber);
        intent.putExtra("CITY", city);
        intent.putExtra("DATE", date);
        mContext.startActivity(intent);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, blodGrp, hospt, units, place, date;
        Button editRequestBtn;
        ImageButton donorsList;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            blodGrp = itemView.findViewById(R.id.blood_group);
            hospt = itemView.findViewById(R.id.hospital);
            units = itemView.findViewById(R.id.units);
            place = itemView.findViewById(R.id.place);
            editRequestBtn = itemView.findViewById(R.id.editRequestBtn);
            date = itemView.findViewById(R.id.date);
            donorsList = itemView.findViewById(R.id.donorsList);
            //            itemView.findViewById(R.id.editRequestBtn).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
//                    builder.setTitle("EDIT REQUEST");
//                    builder.setIcon(R.drawable.ic_edit_blk);
//                    builder.setMessage("Are you Sure you want to edit this request ?");
//                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//////                          Intent intent = new Intent(getContext(), Login.class);
//////                          startActivity(intent);
//////                          getActivity().finish();
//                            LaunchIntent(i);
//                        }
//                    });
//                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//                }
//            });
        }
    }
}
