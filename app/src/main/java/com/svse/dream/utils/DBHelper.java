package com.svse.dream.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dream on 2016/12/2.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static SQLiteDatabase db;

    //连接题库数据库
    public static SQLiteDatabase getHelper() {
        db= SQLiteDatabase.openDatabase(Globel.DB_PATH+ Globel.DB_NAME,null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

    public static void closeDB(){
        if (db!=null){
            db.close();
        }
    }


    public DBHelper(Context context) {
        super(context, "MYDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 我的题库
         * my_type 题目类型
         * 1：错题
         * 2：收藏
         */
        db.execSQL("create table my_lib(" +
                "_id integer primary key autoincrement," +
                "question_ID integer not null," +
                "my_type number not null,"+
                "osName text not null," +
                "question_content text not null," +
                "question_answerA text not null," +
                "question_answerB text not null," +
                "question_answerC text not null," +
                "question_answerD text not null," +
                "question_explain text not null," +
                "question_answer1 number not null,"+
                "question_answer2 number not null,"+
                "question_answer3 number not null,"+
                "question_answer4 number not null"+
                " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
