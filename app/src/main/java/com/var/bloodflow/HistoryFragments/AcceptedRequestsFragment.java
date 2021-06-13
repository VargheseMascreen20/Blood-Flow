package com.var.bloodflow.HistoryFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.var.bloodflow.Adapters.AcceptedRequestsAdapter;
import com.var.bloodflow.Adapters.MyRequestsAdapter;
import com.var.bloodflow.ModelClasses.AcceptedRequestsModel;
import com.var.bloodflow.ModelClasses.MakeRequestModel;
import com.var.bloodflow.R;

import java.util.ArrayList;
import java.util.List;

public class AcceptedRequestsFragment extends Fragment {

    FirebaseUser fuser;
    DatabaseReference reference;
    private RecyclerView recyclerView;
    private AcceptedRequestsAdapter myAcceptedRequestsAdapter;
    private List<AcceptedRequestsModel> mAccepted;
    private List<String> myAcceptedList;

    public AcceptedRequestsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accepted_requests, container, false);
        recyclerView = view.findViewById(R.id.myAccepted);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        myAcceptedList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("AcceptedRequests");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myAcceptedList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AcceptedRequestsModel acceptedRequestsModel = snapshot.getValue(AcceptedRequestsModel.class);
                    if (acceptedRequestsModel.getDonorID().equals(fuser.getUid())) {
                        if (!myAcceptedList.contains(acceptedRequestsModel.getDonorID()))
                            myAcceptedList.add(acceptedRequestsModel.getDonorID());
                    }
                }
                showMyAcceptedRequests();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void showMyAcceptedRequests() {
        mAccepted = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("AcceptedRequests");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAccepted.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AcceptedRequestsModel request = snapshot.getValue(AcceptedRequestsModel.class);
                    for (String id : myAcceptedList) {
                        if (request.getDonorID().equals(id)) {
                            mAccepted.add(request);
                        } else {
                            break;
                        }
                    }

                }
                myAcceptedRequestsAdapter = new AcceptedRequestsAdapter(getContext(), mAccepted);
                recyclerView.setAdapter(myAcceptedRequestsAdapter);
                myAcceptedRequestsAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}