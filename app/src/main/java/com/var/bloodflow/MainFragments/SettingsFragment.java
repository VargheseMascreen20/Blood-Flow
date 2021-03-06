package com.var.bloodflow.MainFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.var.bloodflow.Login;
import com.var.bloodflow.ProfileActivity;
import com.var.bloodflow.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    private Preference report, profile, delete, logout, help_support;
    private int id;
    private String name, nameUser, phone;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        Query query = databaseReference.orderByChild("user_id").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String name = "" + ds.child("name").getValue();
                    nameUser = name;
                    profile.setTitle(nameUser);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        report = (Preference) findPreference("report_key");
        profile = findPreference("change_pro_key");
        delete = findPreference("delete_key");
        logout = findPreference("log_out_key");
        SharedPreferences sp = getContext().getSharedPreferences("Credentials", Context.MODE_PRIVATE);
        id = sp.getInt("User ID", 0);
        name = sp.getString("Name", "");
        phone = sp.getString("Phone Number", "");
        SharedPreferences.Editor editor = sp.edit();
        help_support = findPreference("help_key");

        help_support.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822"); // use from live device
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"bloodflow@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject goes here");
                i.putExtra(Intent.EXTRA_TEXT, "body goes here");
                startActivity(Intent.createChooser(i, "Select email application."));
                return false;
            }
        });

        report.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.envyandroid.com"));
//                startActivity(intent);
//                return false;

                Intent i = new Intent(Intent.ACTION_SEND);
                //i.setType("text/plain"); //use this line for testing in the emulator
                i.setType("message/rfc822"); // use from live device
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"bloodflow@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject goes here");
                i.putExtra(Intent.EXTRA_TEXT, "body goes here");
                startActivity(Intent.createChooser(i, "Select email application."));
                return false;
            }
        });

        profile.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
                return false;
            }
        });

        delete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(getContext(), Login.class);
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
                        Intent intent = new Intent(getContext(), Login.class);//.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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