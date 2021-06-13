package com.var.bloodflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.var.bloodflow.Adapters.AcceptedDonorsAdapter;
import com.var.bloodflow.Adapters.AcceptedRequestsAdapter;
import com.var.bloodflow.Adapters.UserAdapter;
import com.var.bloodflow.ModelClasses.AcceptedRequestsModel;
import com.var.bloodflow.ModelClasses.Users;

import java.util.ArrayList;
import java.util.List;

public class AcceptedDonorsList extends AppCompatActivity {
    FirebaseUser fuser;
    DatabaseReference reference;
    private RecyclerView recyclerView;
    private AcceptedDonorsAdapter myAcceptedDonorAdapter;
    private List<Users> mAccepted;
    private List<String> myAcceptedDonorsList;
    private String opNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_donors_list);
        recyclerView = findViewById(R.id.myAcceptedDonors);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        myAcceptedDonorsList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("AcceptedRequests");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myAcceptedDonorsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    opNumber = getIntent().getExtras().getString("OP NUMBER");
                    AcceptedRequestsModel acceptedRequestsModel = snapshot.getValue(AcceptedRequestsModel.class);

                    if (acceptedRequestsModel.getOpNumber().equals(opNumber)) {
                        myAcceptedDonorsList.add(acceptedRequestsModel.getDonorID());
                    }
                }
                showMyDonors();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showMyDonors() {
        mAccepted = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAccepted.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Users user = snapshot.getValue(Users.class);
                    for (String id : myAcceptedDonorsList) {
                        assert user != null;
                        if (user.getUser_id().equals(id)) {
                            if (mAccepted.size() != 0) {
                                int flag = 0;
                                for (Users u : mAccepted) {
                                    if (user.getUser_id().equals(u.getUser_id())) {
                                        flag = 1;
                                        break;
                                    }
                                }
                                if (flag == 0)
                                    mAccepted.add(user);
                            } else {

                                mAccepted.add(user);
                            }
                        }
                    }
                }

                AcceptedDonorsAdapter myAcceptedDonorAdapter = new AcceptedDonorsAdapter(getApplicationContext(), mAccepted);
                recyclerView.setAdapter(myAcceptedDonorAdapter);
                myAcceptedDonorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}