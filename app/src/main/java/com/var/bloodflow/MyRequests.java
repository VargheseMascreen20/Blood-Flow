package com.var.bloodflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.var.bloodflow.Adapters.MyRequestsAdapter;
import com.var.bloodflow.Adapters.UserAdapter;
import com.var.bloodflow.ModelClasses.MakeRequestModel;
import com.var.bloodflow.ModelClasses.Users;

import java.util.ArrayList;
import java.util.List;

public class MyRequests extends AppCompatActivity {
    ImageButton fab_add;
    FirebaseUser fuser;
    DatabaseReference reference;
    private RecyclerView recyclerView;
    private MyRequestsAdapter myRequestsAdapter;
    private List<MakeRequestModel> mRequests;
    private List<String> myReqLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);


        recyclerView = findViewById(R.id.myRequests);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        myReqLists = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("request");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myReqLists.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MakeRequestModel makeRequestModel = snapshot.getValue(MakeRequestModel.class);
                    if (makeRequestModel.getUser_id().equals(fuser.getUid())) {
                        if (!myReqLists.contains(makeRequestModel.getUser_id()))
                            myReqLists.add(makeRequestModel.getUser_id());
                    }
                }
                showMyRequests();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyRequests.this, MakeRequest.class);
                startActivity(i);
            }
        });
    }

    private void showMyRequests() {
        mRequests = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("request");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRequests.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MakeRequestModel request = snapshot.getValue(MakeRequestModel.class);
                    for (String id : myReqLists) {
                        if (request.getUser_id().equals(id)) {
                            mRequests.add(request);
                        } else {
                            break;
                        }
                    }

                }
                myRequestsAdapter = new MyRequestsAdapter(getApplicationContext(), mRequests);
                recyclerView.setAdapter(myRequestsAdapter);
                myRequestsAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}