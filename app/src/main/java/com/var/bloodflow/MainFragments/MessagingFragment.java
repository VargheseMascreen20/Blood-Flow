package com.var.bloodflow.MainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.var.bloodflow.Fragments.ChatFragment;
import com.var.bloodflow.Fragments.UsersFragment;
import com.var.bloodflow.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MessagingFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messaging, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new ChatFragment(), "Chats");
        viewPagerAdapter.addFragment(new UsersFragment(), "Users");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);

        }
    }
////    private void status(String status){
////        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
////        reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
////        HashMap <String , Object> hashMap = new HashMap<>();
////        hashMap.put("status",status);
////        reference.updateChildren(hashMap);
////    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        status("online");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        status("offline");
//    }
}