package com.var.bloodflow.Adapters;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.var.bloodflow.ModelClasses.MakeRequestModel;
import com.var.bloodflow.R;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    ArrayList<MakeRequestModel> list;


    public RequestAdapter(ArrayList<MakeRequestModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_row, viewGroup, false);
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, blodGrp, hospt, units, place;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            blodGrp = itemView.findViewById(R.id.blood_group);
            hospt = itemView.findViewById(R.id.hospital);
            units = itemView.findViewById(R.id.units);
            place = itemView.findViewById(R.id.place);

            itemView.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Accept Request");
                    builder.setMessage("Are you Sure you want to accept the request");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                          Intent intent = new Intent(getContext(), Login.class);
//                          startActivity(intent);
//                          getActivity().finish();
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
        }

    }
}