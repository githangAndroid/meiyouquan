package com.mayinews.g.app;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


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
    public static List<String> aids=new ArrayList<>();  //用来保存关注过的


    @Override
    public void onCreate() {
        super.onCreate();
//        setupLeakCanary();
        String appInfo = getAppInfo();
        Log.e("TAG","包名"+appInfo);


    }

//    protected RefWatcher setupLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return RefWatcher.DISABLED;
//        }
//        return LeakCanary.install(this);
//    }



    private String getAppInfo() {
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = this.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return pkName ;
        } catch (Exception e) {
        }
        return null;
    }
}

