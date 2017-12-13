package com.svse.dream.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/12/13.
 */

public class GlobelVar {
    //题库数据库路径
    public static final String DB_PATH="/data/data/com.svse.dream.test/databases/";
    //题库数据库名称
    public static final String DB_NAME="question.db";
    //sharedPreferences存储名称
    public static final String sharedPreferencesName="info";

    //获取sharedPreferences对象
    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE);
    }

    //获取sharedPreferences.editor对象
    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context){
        return context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE).edit();
    }
}
