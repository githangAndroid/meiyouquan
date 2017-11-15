package com.mayinews.g.album.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gxz.PagerSlidingTabStrip;
import com.mayinews.g.R;
import com.mayinews.g.album.adapter.MyAlbumItemAdapter;
import com.mayinews.g.home.bean.AppTags;
import com.mayinews.g.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by 盖航_ on 2017/8/29.
 * 私照
 */

public class AlbumFragment extends Fragment {


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tab)
    PagerSlidingTabStrip tab;
    Unbinder unbinder;
    private ImageView image;
    private TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.album_fragment, container,false);
        image = (ImageView) getActivity().findViewById(R.id.top_logo);
         title = (TextView) getActivity().findViewById(R.id.title);
        if(image!=null){
            image.setVisibility(View.GONE);
        }
        if(title!=null){
            title.setVisibility(View.VISIBLE);
            title.setText("私照");
        }
        unbinder = ButterKnife.bind(this, view);


        Intent intent = getActivity().getIntent();
        setViewPager();//设置ViewPager
//      setTab();//设置Tab导航栏
        return view;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            if(image!=null){
                image.setVisibility(View.GONE);
            }
            if(title!=null){
                title.setVisibility(View.VISIBLE);
                title.setText("私照");
            }
        }

    }
    private void setViewPager() {
        final List<String> titles=new ArrayList<>();
        final List<Fragment> fragments=new ArrayList<>();
        //请求分类
        OkHttpUtils.get().url(Constant.TAGURL).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        AppTags appTags = JSON.parseObject(response, AppTags.class);
                        List<AppTags.ResultBean> data = appTags.getResult();
  
                        for(int i=0;i<data.size();i++){
                             titles.add(data.get(i).getTitle());

                         }
                        setAdapter(data,titles, fragments);
                        setTab();
                    }
                });



    }

    private void setAdapter(List<AppTags.ResultBean> data,List<String> titles, List<Fragment> fragments) {

        for(int i=0;i<data.size();i++){
            String id = data.get(i).getId();
            fragments.add(new NewestFragment(id));
         }
//        fragments.add(new NewestFragment());
//        fragments.add(new NewestFragment());
//        fragments.add(new NewestFragment());
//        fragments.add(new NewestFragment());
//        fragments.add(new NewestFragment());
//        fragments.add(new NewestFragment());


//        fragments.add(new NewestFragment());
//        fragments.add(new NewestFragment());
        MyAlbumItemAdapter adapter = new MyAlbumItemAdapter(getFragmentManager(), getActivity(), titles, fragments);
        viewPager.setAdapter(adapter);
        Intent intent = getActivity().getIntent();
        if(intent!=null){
            String title = intent.getStringExtra("title");
            for(int i=0;i<titles.size();i++){
                if(titles.get(i).equals(title)){
                    viewPager.setCurrentItem(i);
                    Log.i("TAG","执行了"+i);
                }
            }
        }
    }

    private void setTab() {
         tab.setViewPager(viewPager); //需要在最前面设置
        // 设置Tab是自动填充满屏幕的
        tab.setShouldExpand(true);
        tab.setFadeEnabled(true);  //动画渐变
        //这是当点击tab时内容区域Viewpager是否是左右滑动,默认是true
        tab.setSmoothScrollWhenClickTab(false);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
