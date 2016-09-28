package com.wlazly.customview;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Wlazly on 2016/9/6 0006.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "900051409", true);
    }
}
