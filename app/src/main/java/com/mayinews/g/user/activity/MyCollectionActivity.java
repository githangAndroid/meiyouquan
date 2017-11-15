package com.mayinews.g.user.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.mayinews.g.R;
import com.mayinews.g.user.adapter.MyCollectionAdapter;
import com.mayinews.g.user.fragment.AlbumCollectionFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCollectionActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tab)
    PagerSlidingTabStrip tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.choose)
    TextView choose;


    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();  //分类标题
    private boolean isChooseClick=false;//标记选择按钮是否点击

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        title.setText("我的收藏");
        choose.setVisibility(View.VISIBLE);
        choose.setText("选择");
        //创建数据源
        AlbumCollectionFragment albumCollectionFragment = new AlbumCollectionFragment();
        fragments.add(albumCollectionFragment);
        viewPager.setAdapter(new MyCollectionAdapter(getSupportFragmentManager(), fragments));

    }


    @OnClick({R.id.iv_back})
    public void OnClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;
//            case R.id.choose:
//                if(isChooseClick){
//                    choose.setText("取消");
//                    choose.setVisibility(View.GONE);
//                }else{
//                    choose.setText("选择");
//                    choose.setVisibility(View.VISIBLE);
//                }
//                isChooseClick=!isChooseClick;
//                break;
        }
    }

}
