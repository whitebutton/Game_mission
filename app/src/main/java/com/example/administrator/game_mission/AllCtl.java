package com.example.administrator.game_mission;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class AllCtl {
    public static int id = 0;
    public static String content = ""; // 内容
    public static SharedPreferences currType;//全局轻量化存储
    public static SharedPreferences.Editor currTypeeditor;
    public static AllBean allBean;//所有集合
    public static ArrayList<AllBean.DataBean> alllQuestion = new ArrayList<>();//混合
    public static ArrayList<AllBean.DataBean> trueWords = new ArrayList<>();//真心话
    public static ArrayList<AllBean.DataBean> bigAdventure = new ArrayList<>();//大冒险
    public static Boolean sound = true;//声音是否开启
    public static Boolean shock = true;//震动是否开启
    public static Boolean continueGame = false;//是否连续玩真心话大冒险
}
