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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.var.bloodflow.MessageActivity;
import com.var.bloodflow.ModelClasses.AcceptedRequestsModel;
import com.var.bloodflow.ModelClasses.MakeRequestModel;
import com.var.bloodflow.R;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    ArrayList<MakeRequestModel> list;
    ArrayList<String> user;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Context context;
    long maxid = 0;

    public RequestAdapter(ArrayList<MakeRequestModel> list, Context context) {
        this.list = list;
        this.context = context;
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
        myViewHolder.reqDate.setText("Date : " + list.get(i).getDate());

        myViewHolder.msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(i);
            }
        });

        myViewHolder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("AcceptedRequests");
                final String opNumber = list.get(i).getOp_number().trim();
                final String donorID = firebaseUser.getUid().trim();
                final String recieverID = list.get(i).getUser_id().trim();
                final String patientName = list.get(i).getPatientName().trim();
                final String hosptName = list.get(i).getHospitaltName().trim();
                final String bloodGroup = list.get(i).getBlood_group().trim();
                final String units = list.get(i).getRequired_units().trim();
                final String place = list.get(i).getCity().trim();
                final String reqDate = list.get(i).getDate().trim();

                reference = FirebaseDatabase.getInstance().getReference("AcceptedRequests");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                            maxid = (snapshot.getChildrenCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Exists(opNumber, donorID, recieverID, patientName, hosptName, bloodGroup, units, place, reqDate);
            }

            public void Exists(String opNumber, String donorID, String recieverID, String patientName,
                               String hosptName, String bloodGroup, String units, String place, String reqDate) {
                reference = FirebaseDatabase.getInstance().getReference("AcceptedRequests");
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                Query query = reference.orderByChild("donorID").equalTo(firebaseUser.getUid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            AcceptedRequestsModel acceptedRequestsModel1 = new AcceptedRequestsModel(opNumber, donorID, recieverID,
                                    patientName, hosptName, bloodGroup, units, reqDate, place);
                            reference.child(String.valueOf(maxid + 1)).setValue(acceptedRequestsModel1);
                            myViewHolder.toast.show();
                        } else {
                            myViewHolder.toast2.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });
    }

    private void sendMessage(int i) {
        Intent messageDonor = new Intent(context, MessageActivity.class);
        String userID = list.get(i).getUser_id();
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
        Button acceptBtn;
        Toast toast, toast2;
        FirebaseUser firebaseUser;
        ImageButton msgBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            blodGrp = itemView.findViewById(R.id.blood_group);
            hospt = itemView.findViewById(R.id.hospital);
            units = itemView.findViewById(R.id.units);
            place = itemView.findViewById(R.id.place);
            reqDate = itemView.findViewById(R.id.reqDate);
            acceptBtn = itemView.findViewById(R.id.acceptBtn);
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            msgBtn = itemView.findViewById(R.id.msgBtn);
            toast = Toast.makeText(context, "ADDED TO ACCEPTED REQUEST !!!", Toast.LENGTH_SHORT);
            toast2 = Toast.makeText(context, "YOU HAVE ALREADY ACCEPTED A REQUEST !!!", Toast.LENGTH_SHORT);
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