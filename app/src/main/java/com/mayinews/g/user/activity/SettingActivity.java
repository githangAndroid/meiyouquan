package com.mayinews.g.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mayinews.g.R;
import com.mayinews.g.app.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.mayinews.g.utils.CacheUtil;
import com.mayinews.g.utils.SPUtils;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.clearCache)
    RelativeLayout clearCache;
    @BindView(R.id.checkVersion)
    RelativeLayout version;
    @BindView(R.id.rl_outLogin)
    RelativeLayout rlOutLogin;
    @BindView(R.id.aboutMe)
    RelativeLayout aboutMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        title.setText("系统设置");
        clearCache.setOnClickListener(this);
        version.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rlOutLogin.setOnClickListener(this);
        aboutMe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.clearCache:
                //清理缓存
                CacheUtil.clearAllCache(this);
                Toast.makeText(SettingActivity.this, "成功清理缓存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkVersion:
                Toast.makeText(SettingActivity.this, "当前版本为最新版", Toast.LENGTH_SHORT).show();

                break;

            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_outLogin:
                SPUtils.put(this, MyApplication.LOGINSTATUES,"0");
                Toast.makeText(SettingActivity.this, "成功清楚登录信息...", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.aboutMe:

                startActivity(new Intent(this,AboutMe.class));
                break;
        }

    }
}
