package com.example.administrator.cookman;

import android.content.Context;

import com.example.administrator.cookman.constants.Constants;
import com.example.administrator.cookman.utils.Logger.LogLevel;
import com.example.administrator.cookman.utils.Logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2017/2/17.
 */

public class CookManApplication extends LitePalApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        //Logger设置
        if(BuildConfig.DEBUG) {
            Logger.init(Constants.Common_Tag).logLevel(LogLevel.FULL);
        }
        else{
            Logger.init(Constants.Common_Tag).logLevel(LogLevel.NONE);
        }

        //腾讯Bugly
        CrashReport.initCrashReport(getApplicationContext());

    }

    public static Context getContext() {
        return mContext;
    }

}
