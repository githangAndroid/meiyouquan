package com.mayinews.g.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

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
/**
 * 初始化common库
 * 参数1:上下文，不能为空
 * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
 * 参数3:Push推送业务的secret
 */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(true);
        UMConfigure.setEncryptEnabled(true);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        /**
         * 在需要的地方调用上述方法
         */
        String channelNumber = getAppMetaData(getBaseContext(), "UMENG_CHANNEL");//获取app当前的渠道号
        Log.e("TAG","渠道"+channelNumber);
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
        } catch (Exception ignored) {
        }
        return null;
    }


    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String channelNumber = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
    }
}

