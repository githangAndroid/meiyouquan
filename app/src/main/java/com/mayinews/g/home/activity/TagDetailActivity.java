package com.mayinews.g.home.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


import com.mayinews.g.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 点开分类的展开类
 */
public class TagDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void OnClick(View view){
     switch (view.getId()){

         case R.id.iv_back:
            finish();

             break;

     }

    }
}
