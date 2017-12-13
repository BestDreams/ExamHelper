package com.svse.dream.utils;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dream on 2016/12/2.
 */
public class DBHelper {

    private static SQLiteDatabase db;
    //连接数据库
    public static SQLiteDatabase getHelper() {
        db= SQLiteDatabase.openDatabase(GlobelVar.DB_PATH+ GlobelVar.DB_NAME,null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

}
