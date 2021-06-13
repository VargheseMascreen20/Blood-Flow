package com.var.bloodflow.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.var.bloodflow.MessageActivity;
import com.var.bloodflow.ModelClasses.AcceptedRequestsModel;
import com.var.bloodflow.ModelClasses.MakeRequestModel;
import com.var.bloodflow.R;

import java.util.ArrayList;
import java.util.List;

public class AcceptedRequestsAdapter extends RecyclerView.Adapter<AcceptedRequestsAdapter.MyViewHolder> {
    List<AcceptedRequestsModel> list;
    Context context;

    public AcceptedRequestsAdapter(Context context, List<AcceptedRequestsModel> list) {
        this.list = list;
        this.context = context;
    }

    public void AcceptedRequestsAdapter(ArrayList<AcceptedRequestsModel> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public AcceptedRequestsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_row, viewGroup, false);
        return new AcceptedRequestsAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AcceptedRequestsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText("Name : " + list.get(i).getPatientName());
        myViewHolder.blodGrp.setText("Blood Group : " + list.get(i).getBlood_group());
        myViewHolder.hospt.setText("Hospital Name : " + list.get(i).getHospitaltName());
        myViewHolder.units.setText("Units : " + list.get(i).getRequired_units());
        myViewHolder.place.setText("Place : " + list.get(i).getCity());
        myViewHolder.reqDate.setText("Date : " + list.get(i).getDate());
        myViewHolder.msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(i);
            }
        });

        myViewHolder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });
    }

    private void sendMessage(int i) {
        Intent messageDonor = new Intent(context, MessageActivity.class);
        String userID = list.get(i).getRecieverID();
        messageDonor.putExtra("userid", userID);
        messageDonor.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(messageDonor);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, blodGrp, hospt, units, place, reqDate;
        Button complete;
        ImageButton msgBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            blodGrp = itemView.findViewById(R.id.blood_group);
            hospt = itemView.findViewById(R.id.hospital);
            units = itemView.findViewById(R.id.units);
            place = itemView.findViewById(R.id.place);
            reqDate = itemView.findViewById(R.id.reqDate);
            complete = itemView.findViewById(R.id.acceptBtn);
            complete.setText("COMPLETED");
            msgBtn = itemView.findViewById(R.id.msgBtn);


//            itemView.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
//                    builder.setTitle("Accept Request");
//                    builder.setIcon(R.drawable.ic_handshake);
//                    builder.setMessage("Are you Sure you want to accept this request ?");
//                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                          Intent intent = new Intent(getContext(), Login.class);
//                          startActivity(intent);
//                          getActivity().finish();
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
