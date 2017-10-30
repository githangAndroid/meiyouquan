package com.mayinews;

import com.mayinews.g.app.MyApplication;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class TestMyApplication extends MyApplication {
    @Override protected RefWatcher setupLeakCanary() {
        // No leakcanary in unit tests.
        return RefWatcher.DISABLED;
    }
}
