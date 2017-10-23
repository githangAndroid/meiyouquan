package com.mayinews.g.user.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.mayinews.g.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutMe extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);
        title.setText("关于我们");
    }


    public void OnClick(View v){
        switch (v.getId()) {
            case  R.id.iv_back:
                finish();
                break;

        }

    }
}
