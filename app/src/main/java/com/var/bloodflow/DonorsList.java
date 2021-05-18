package com.var.bloodflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.var.bloodflow.Adapters.DonorsAdapter;
import com.var.bloodflow.ModelClasses.Users;

import java.util.ArrayList;

public class DonorsList extends AppCompatActivity {
    Button messageBtn;
    private RecyclerView donors_list;
    private ArrayList<Users> list;
    private RecyclerView recyclerView;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors_list);
        Button messageBtn = findViewById(R.id.message_Btn);
//        Intent messageActivity = new Intent(DonorsList.Message.class);
//        startActivity(Message.class);
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.keepSynced(true);

        donors_list = (RecyclerView) findViewById(R.id.donors_list);
        donors_list.setHasFixedSize(true);
        donors_list.setLayoutManager(new LinearLayoutManager(this));

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
                            list.add(ds.getValue(Users.class));
                        }
                        DonorsAdapter adapterClass = new DonorsAdapter(list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(DonorsList.this));
                        recyclerView.setAdapter(adapterClass);
                    }
//                    if (searchView != null) {
//                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                            @Override
//                            public boolean onQueryTextSubmit(String s) {
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onQueryTextChange(String s) {
//                                search(s);
//                                return true;
//                            }
//                        });
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DonorsList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}