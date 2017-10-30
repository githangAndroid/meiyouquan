package com.mayinews.g.app;

import android.app.Application;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.mayinews.g.app.bean.UserInfoBean;
import com.mayinews.g.utils.Constant;
import com.mayinews.g.utils.SPUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Console;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class MyApplication extends Application {


    public static final String PHONENUMBER = "com.mayinews.phonenumber";
    public static final String TOKEN = "com.mayinews.token";
    public static final String USERID = "com.mayinews.g.userid";
    public static final String USERAVATAR = "com.mayinews.g.useravater";
    public static final String USERNICKNAME = "com.mayinews.g.usernickname";
    public static final String USERSEX = "com.mayinews.g.usersex";
    public static final String LOGINSTATUES = "com.mayinews.loginstatues";


    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();

    }

    protected RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }
}

