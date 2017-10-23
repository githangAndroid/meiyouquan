package com.mayinews.g.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2017/9/25.
 */

public class VersionUtils {

    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo( "com.mayinews", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {

        }

        return verCode;
    }

    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    "com.mayinews", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {


        }
        return verName;
    }


}
