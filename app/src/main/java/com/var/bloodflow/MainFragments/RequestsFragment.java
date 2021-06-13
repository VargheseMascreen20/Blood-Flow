package com.var.bloodflow.MainFragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.var.bloodflow.Adapters.RequestAdapter;
import com.var.bloodflow.MakeRequest;
import com.var.bloodflow.Map;
import com.var.bloodflow.ModelClasses.MakeRequestModel;
import com.var.bloodflow.MyRequests;
import com.var.bloodflow.R;

import java.util.ArrayList;

public class RequestsFragment extends Fragment {

    private DatabaseReference reference;
    private ArrayList<MakeRequestModel> list;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView findBloodDrive;

    private FloatingActionButton fabAdd;
    private FloatingActionButton fab_my_requests;

    public RequestsFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        findBloodDrive = view.findViewById(R.id.findBloodDrives);
        reference = FirebaseDatabase.getInstance().getReference().child("request");

        recyclerView = view.findViewById(R.id.req_list);
        searchView = view.findViewById(R.id.searchView);
        searchView.onActionViewExpanded();


        fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MakeRequest.class);
                startActivity(i);
            }
        });
        fab_my_requests = view.findViewById(R.id.fab_my_requests);
        fab_my_requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MyRequests.class);
                startActivity(i);
            }
        });

        findBloodDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Map.class);
                int flag = 3;
                intent.putExtra("FLAG", flag);
                startActivity(intent);
            }
        });
        return view;
    }

    public void onStart() {
        super.onStart();
        if (reference != null) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds.getValue(MakeRequestModel.class));
                        }
                        RequestAdapter adapterClass = new RequestAdapter(list, getContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapterClass);
                    }
                    if (searchView != null) {
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {
                                search(s);
                                return true;
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private void search(String str) {
        ArrayList<MakeRequestModel> myList = new ArrayList<>();
        for (MakeRequestModel object : list) {
            if (object.getBlood_group().toLowerCase().contains(str.toLowerCase()) ||
                    object.getHospitaltName().toLowerCase().contains(str.toLowerCase()) ||
                    object.getCity().toLowerCase().contains(str.toLowerCase()) ||
                    object.getPatientName().toLowerCase().contains(str.toLowerCase())) {
                myList.add(object);
            }
        }
        RequestAdapter adapterClass = new RequestAdapter(myList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterClass);

    }


}
