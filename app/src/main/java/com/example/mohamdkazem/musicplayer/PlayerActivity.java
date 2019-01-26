package com.example.mohamdkazem.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class PlayerActivity extends SingleFragmentActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabTitles={"Tab1","Tab2","Tab3","Tab4"};

    @Override
    public Fragment createFragment() {
        return PlayerFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return PlayerFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }
        });
    }
}
