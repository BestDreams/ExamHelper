package com.svse.dream.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.svse.dream.bean.Question;

import java.util.ArrayList;
import java.util.List;

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
    /**
     * 练习模式变量
     */

    //题库
    public static List<Question> studyQuestionList=null;
    //索引
    public static int questionIndex=0;
    //正确
    public static int questionTrue=0;
    //错误
    public static int questionFalse=0;
    //错误题库
    public static List<Question> getStudyErrorQuestionList=new ArrayList<>();
    //题目状态（是否已提交答案）
    public static Boolean[] isSubmitStudyAnswer=null;

    //获取sharedPreferences对象
    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE);
    }

    //获取sharedPreferences.editor对象
    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context){
        return context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE).edit();
    }
}
