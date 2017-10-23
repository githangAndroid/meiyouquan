package com.mayinews.g.album.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31 0031.
 * 私照模块里面ViewPager的适配器
 */

public class MyAlbumItemAdapter  extends FragmentPagerAdapter{



    private LayoutInflater inflater;
    private List<Fragment> fragments=new ArrayList<>();
    private List<String> titles;

    public MyAlbumItemAdapter(FragmentManager fm, Context context,List<String> titles,List<Fragment> fragments) {
        super(fm);

        inflater=LayoutInflater.from(context);
        this.titles=titles;
        this.fragments=fragments;
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
