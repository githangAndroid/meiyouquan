package com.mayinews.g.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.mayinews.g.home.bean.ModelTagBean;
import com.mayinews.g.home.fragment.TagImageFragment;

import java.util.List;

/**
 * Created by 盖航_ on 2017/8/29.
 */

public class VAdapter extends FragmentPagerAdapter {


    List<ModelTagBean.ResultBean> datas;
    public VAdapter(FragmentManager fm,  List<ModelTagBean.ResultBean> datas) {
        super(fm);
        this.datas=datas;

    }




    @Override
    public Fragment getItem(int position) {
        ModelTagBean.ResultBean dataBean =null ;
           if(datas.size()>0) {
               dataBean = datas.get(position % (datas.size()));
           }
        Log.e("TAG","图片"+dataBean.toString());
        return new TagImageFragment(dataBean);
    }

    @Override
    public int getCount() {


        return datas.size()*40;
    }




}
