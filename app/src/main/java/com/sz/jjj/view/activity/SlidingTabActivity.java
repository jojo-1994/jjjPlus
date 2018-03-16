package com.sz.jjj.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sz.jjj.R;
import com.sz.jjj.view.fragment.SildingTabFragment;
import com.sz.jjj.view.widget.SlidingTabLayout;

import java.util.ArrayList;

/**
 * Created by jjj on 2017/10/30.
 *
 * @description:
 */

public class SlidingTabActivity extends AppCompatActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "iOS", "Android"
    };
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sliding_tab_activity);

        SlidingTabLayout tabLayout = (SlidingTabLayout) findViewById(R.id.tablayout);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);

        for (String title : mTitles) {
            mFragments.add(SildingTabFragment.getInstance(title));
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);

        tabLayout.setViewPager(vp);

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
