package com.mayinews.g.home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 排行榜Activity的Adapter
 */

public class RankAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> fragments;
    private List<String> titles;

    public RankAdapter(FragmentManager fm, Context context, List<Fragment> fragments,List<String> titles) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.titles=titles;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {

        return titles.get(position);
    }
}
