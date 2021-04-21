package com.var.bloodflow.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.var.bloodflow.Login;
import com.var.bloodflow.R;
import com.var.bloodflow.Register;

import java.util.Objects;

public class ProfileFragment extends PreferenceFragmentCompat {
    private Preference report, profile, delete, logout;
    private String name,phone;
    private int id;
    FirebaseAuth firebaseAuth;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        report = (Preference) findPreference("report_key");
        profile = findPreference("change_pro_key");
        delete = findPreference("delete_key");
        logout = findPreference("log_out_key");
        SharedPreferences sp = getContext().getSharedPreferences("Credentials", Context.MODE_PRIVATE);
        id=sp.getInt("User ID", 0);
        name=sp.getString("Name", "");
        phone=sp.getString("Phone Number", "");
        SharedPreferences.Editor editor = sp.edit();
        profile.setTitle(name);
        report.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.envyandroid.com"));
//                startActivity(intent);
//                return false;

                Intent i = new Intent(Intent.ACTION_SEND);
                //i.setType("text/plain"); //use this line for testing in the emulator
                i.setType("message/rfc822"); // use from live device
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"test@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject goes here");
                i.putExtra(Intent.EXTRA_TEXT, "body goes here");
                startActivity(Intent.createChooser(i, "Select email application."));
                return false;
            }
        });

        delete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                                Intent intent = new Intent(getContext(), com.var.bloodflow.Login.class);
                                 startActivity(intent);

                return false;
            }
        });
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Log Out");
                builder.setMessage("Are you Sure to Log Out?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();

                        editor.remove("phone");
                        editor.putInt("Log in Status", 1);
                        editor.apply();
                        Intent intent = new Intent(getContext(), Login.class);
                        startActivity(intent);
                        getActivity().finish();
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

                return false;
            }
        });
    }
}











//package com.harvin.placementapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.harvin.placementapp.Model.AdsDatabase;
//
//import java.util.ArrayList;
//
//public class ViewProfileActivity extends AppCompatActivity {
//    TextView fullName,email,phone;
//    Button editProf;
//    FirebaseAuth fAuth;
//    FirebaseFirestore fStore;
//    String userId;
//    FirebaseUser user;
//    DocumentReference documentReference;
//    DatabaseReference databaseAds;
//    ArrayList<AdsDatabase> adsArrayList;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_profile);
//
//        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//        userId = fAuth.getCurrentUser().getUid();
//        user = fAuth.getCurrentUser();
//        databaseAds= (DatabaseReference) FirebaseDatabase.getInstance().getReference("users").child(userId);
//        Query query = FirebaseDatabase.getInstance().getReference("id")
//                .orderByChild("email")
//                .equalTo(userId);
//        databaseAds.addValueEventListener(valueEventListener);
//
//        phone = findViewById(R.id.editphone);
//        fullName = findViewById(R.id.editname);
//        email = findViewById(R.id.editmail);
//        editProf=findViewById(R.id.editprofile1);
//
//
//        /*
//        documentReference = fStore.collection("users").document(userId);
//        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()){
//                    phone.setText("  "+documentSnapshot.getString("phone"));
//                    fullName.setText("  "+documentSnapshot.getString("name"));
//                    email.setText("  "+documentSnapshot.getString("email"));
//
//                }else {
//                    Log.d("tag", "onEvent: Document do not exists");
//                }
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("tag", "onEvent: Failed to fetch Data");
//
//                    }
//                });
//         */
//        editProf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ViewProfileActivity.this,EditProfileActivity.class));
//            }
//        });
//    }
//
//    ValueEventListener valueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            if (dataSnapshot.getValue(AdsDatabase.class) !=null) {
//                String key=dataSnapshot.getKey();
//                if(key.equals(userId))
//                {
//                    phone.setText("  "+dataSnapshot.child("phone").getValue().toString());
//                    fullName.setText("  "+dataSnapshot.child("name").getValue().toString());
//                    email.setText("  "+dataSnapshot.child("email").getValue().toString());
//                }
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    };
//}