package com.mayinews.g.home.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.mayinews.g.R;
import com.mayinews.g.home.adapter.RankAdapter;
import com.mayinews.g.home.fragment.RankFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreRankActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tab)
    PagerSlidingTabStrip tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
   List<String> titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_rank);
        ButterKnife.bind(this);
        title.setText("排行榜");
        titles=new ArrayList<>();
        titles.add("热销榜");
        titles.add("经典榜");
        titles.add("新人榜");
        titles.add("红人榜");
        titles.add("飙升榜");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setViewPager();

    }


    //设置适配器
    private void setViewPager() {
           List<Fragment>  fragments = new ArrayList<>();
           //添加Fragment
            for (int i=0;i<5;i++){
                fragments.add(new RankFragment(this));

               }

                 RankAdapter adapter = new RankAdapter(getSupportFragmentManager(), this, fragments,titles);
                 viewPager.setAdapter(adapter);
                  setTab();
                  }



    private void setTab() {
        tab.setViewPager(viewPager); //需要在最前面设置
        // 设置Tab是自动填充满屏幕的
        tab.setShouldExpand(true);
        tab.setFadeEnabled(true);  //动画渐变
        //这是当点击tab时内容区域Viewpager是否是左右滑动,默认是true
        tab.setSmoothScrollWhenClickTab(false);


    }
}
