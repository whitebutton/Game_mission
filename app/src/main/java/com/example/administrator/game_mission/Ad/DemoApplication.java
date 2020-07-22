package com.example.administrator.game_mission.Ad;

import android.app.Application;

/**
 * Create by hanweiwei on 11/07/2018
 */
@SuppressWarnings("unused")
public class DemoApplication extends Application {

    public static String PROCESS_NAME_XXXX = "process_name_xxxx";

    @Override
    public void onCreate() {
        super.onCreate();


        //穿山甲SDK初始化
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdManagerHolder.init(this);
        //如果明确某个进程不会使用到广告SDK，可以只针对特定进程初始化广告SDK的content
        //if (PROCESS_NAME_XXXX.equals(processName)) {
        //   TTAdManagerHolder.init(this)
        //}
    }

}
