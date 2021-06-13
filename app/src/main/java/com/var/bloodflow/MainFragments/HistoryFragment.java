package com.var.bloodflow.MainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;
import com.var.bloodflow.HistoryFragments.AcceptedRequestsFragment;
import com.var.bloodflow.HistoryFragments.CompletedRequestsFragment;
import com.var.bloodflow.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    Button abc;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TabLayout tabLayout;
        ViewPager viewPager;

        @Nullable
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        HistoryFragment.ViewPagerAdapter viewPagerAdapter = new HistoryFragment.ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new AcceptedRequestsFragment(), "Accepted");
        viewPagerAdapter.addFragment(new CompletedRequestsFragment(), "Completed");

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
}
