package com.mayinews.g.user.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.mayinews.g.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_actitity);
        ButterKnife.bind(this);
        title.setText("我的订单");
    }

    @OnClick(R.id.iv_back)
    public void OnClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;
        }

    }
}
