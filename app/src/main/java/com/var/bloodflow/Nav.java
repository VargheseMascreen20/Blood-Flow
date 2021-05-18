package com.var.bloodflow;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.var.bloodflow.MainFragments.HistoryFragment;
import com.var.bloodflow.MainFragments.HomeFragment;
import com.var.bloodflow.MainFragments.MessagingFragment;
import com.var.bloodflow.MainFragments.RequestsFragment;
import com.var.bloodflow.MainFragments.SettingsFragment;

import static com.var.bloodflow.R.id.nav_history;
import static com.var.bloodflow.R.id.nav_home;
import static com.var.bloodflow.R.id.nav_profile;
import static com.var.bloodflow.R.id.nav_request;
import static com.var.bloodflow.R.id.nav_settings;

public class Nav extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case nav_home:
                    HomeFragment fragment1 = new HomeFragment();
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.fragment_container, fragment1, "");
                    ft1.commit();
                    return true;
                case nav_request:
                    RequestsFragment fragment2 = new RequestsFragment();
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    ft2.replace(R.id.fragment_container, fragment2, "");
                    ft2.commit();
                    return true;
                case nav_history:
                    HistoryFragment fragment3 = new HistoryFragment();
                    FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                    ft3.replace(R.id.fragment_container, fragment3, "");
                    ft3.commit();
                    return true;
                case nav_profile:
                    MessagingFragment fragment4 = new MessagingFragment();
                    FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                    ft4.replace(R.id.fragment_container, fragment4, "");
                    ft4.commit();
                    return true;

                case nav_settings:
                    SettingsFragment fragment5 = new SettingsFragment();
                    FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
                    ft5.replace(R.id.fragment_container, fragment5, "");
                    ft5.commit();
                    return true;
            }
            return false;
        }
    };
//    private void checkUserStatus(){
//        FirebaseUser user = fAuth.getCurrentUser();
//        if (user != null)
//        {
//
//        }
//        else{
//            startActivity(new Intent(Launch.this,Login.class));
//        }
//
//    }
//
//    @Override
//    protected void onStart() {
//        checkUserStatus();
//        super.onStart();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(nav_home, new HomeFragment()).commit();
        setUpLandingFragment();


//        fAuth = FirebaseAuth.getInstance();

    }

    private void setUpLandingFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();

        HomeFragment fragment1 = new HomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.fragment_container, fragment1, "");
        ft1.commit();
    }
}