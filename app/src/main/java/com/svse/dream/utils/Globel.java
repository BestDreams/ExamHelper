package com.svse.dream.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.svse.dream.bean.OSInfo;
import com.svse.dream.bean.Question;
import com.svse.dream.bean.QuestionMyLib;
import com.svse.dream.dao.DataDaoImpl;
import com.svse.dream.test.MainActivity;
import com.svse.dream.test.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2017/12/13.
 */

public class Globel {

    //题库数据库路径
    public static final String DB_PATH="/data/data/com.svse.dream.test/databases/";
    //题库数据库名称
    public static final String DB_NAME="question.db";
    //sharedPreferences存储名称
    public static final String sharedPreferencesName="info";

    //全局字符串常量模板
    public static final int QUESTION_TYPE_ALL=0;
    public static final int QUESTION_TYPE_ERROR=1;
    public static final int QUESTION_TYPE_FAVORITE=2;
    public static final int QUESTION_TYPE_EXAM=3;
    public static final String MODEL_CONTINUE="MODEL_CONTINUE";
    public static final String MODEL_CONTINUE_OSNAME="MODEL_CONTINUE_OSNAME";
    public static final String MODEL_CONTINUE_CORRECT="MODEL_CONTINUE_CORRECT";
    public static final String MODEL_CONTINUE_ERROR="MODEL_CONTINUE_ERROR";
    public static final String MODEL_CONTINUE_INDEX="MODEL_CONTINUE_INDEX";
    public static final String EMPTY_TIP="哎呀，是空的耶~~~";
    public static final String LOADING_LIB_STUDY="正在加载练习题库...";
    public static final String LOADING_LIB_CONTINUE="时光机启动中...";
    public static final String LOADING_LIB_ERRORBOOK="正在加载错题本...";
    public static final String LOADING_LIB_FAVORITE="正在加载收藏夹...";
    public static final String LOADING_LIB_EXAM="正在生成考试试卷...";


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
    //题目状态（是否已提交答案）
    public static Boolean[] isSubmitStudyAnswer=null;

    /**
     * 考试模式变量
     */
    //考试题库
    public static List<Question> examQuestionList=null;
    //索引
    public static int examIndex=0;
    //正确
    public static int examTrue=0;
    //错误
    public static int examFalse=0;
    //题量限制
    public static int exam_count_min=1;
    public static int exam_count_max=50;
    //考试参数
    public static Map<String,Integer> examAttrsMap=null;
    //题目状态（是否已提交答案）
    public static Boolean[] isSubmitExamAnswer=null;
    //试卷总分
    public static int exam_total_score=100;
    //试卷总题量
    public static int exam_total_num=0;
    //平均分值
    public static float exam_avg_score=0;
    //开始日期
    public static String exam_start_date="01/01";
    //开始时间
    public static String exam_start_time="00:00";
    //结束日期
    public static String exam_end_date="01/01";
    //结束时间
    public static String exam_end_time="00:00";
    //考试成绩
    public static float exam_grade=0;
    //正确率
    public static float exam_correct_procent=0;

    /**
     * 错误本变量
     * @param context
     * @return
     */
    //题库
    public static List<QuestionMyLib> ErrorBookList=null;
    //索引
    public static int errorBookIndex=0;
    //正确
    public static int errorBookTrue=0;
    //错误
    public static int errorBookFalse=0;
    //题目状态（是否已提交答案）
    public static Boolean[] isSubmitErrorBookStudyAnswer=null;

    /**
     * 收藏夹变量
     * @param context
     * @return
     */
    //题库
    public static List<QuestionMyLib> FavoriteList=null;
    //索引
    public static int favoriteIndex=0;
    //正确
    public static int favoriteTrue=0;
    //错误
    public static int favoriteFalse=0;


    //获取sharedPreferences对象
    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE);
    }

    //获取sharedPreferences.editor对象
    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context){
        return context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE).edit();
    }

    /**
     * 获取我的题库列表
     * @param context
     * @return
     */
    public static List<OSInfo> getOsInfoList(Context context){
        Set<String> myOSlist = getSharedPreferences(context).getStringSet("myOSlist", new TreeSet<String>());
        List<OSInfo> osInfoList=null;
        if (myOSlist==null||myOSlist.size()==0){
            return null;
        }else{
            Object[] myOSlists = myOSlist.toArray();
            osInfoList=new ArrayList<>();
            for (int i = 0; i < myOSlists.length; i++) {
                osInfoList.add(new OSInfo(myOSlists[i].toString(),new DataDaoImpl().getTableNum(myOSlists[i].toString())));
            }
        }
        return osInfoList;
    }

    //设置题库By osName
    public static void setQuestionList(final int type,final String osName,String message, final Context context, final GlobelInterface globelInterface){
        final Handler handler=new Handler();
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (type){
                    //全部
                    case QUESTION_TYPE_ALL:
                        studyQuestionList = new DataDaoImpl().getStudyQuestionsByOsName(osName);
                        isSubmitStudyAnswer=new Boolean[studyQuestionList.size()];
                        for (int j = 0; j < isSubmitStudyAnswer.length; j++) {
                            isSubmitStudyAnswer[j]=false;
                        }
                        break;
                    //错题
                    case QUESTION_TYPE_ERROR:
                        ErrorBookList = new DataDaoImpl().getErrorBookQuestions();
                        isSubmitErrorBookStudyAnswer=new Boolean[ErrorBookList.size()];
                        for (int j = 0; j < isSubmitErrorBookStudyAnswer.length; j++) {
                            isSubmitErrorBookStudyAnswer[j]=false;
                        }
                        break;
                    //收藏
                    case QUESTION_TYPE_FAVORITE:
                        FavoriteList = new DataDaoImpl().getFavoriteQuestions();
                        break;
                    //考试
                    case QUESTION_TYPE_EXAM:
                        examQuestionList= new DataDaoImpl().getExamQuestionsByAttrs(examAttrsMap);
                        isSubmitExamAnswer=new Boolean[examQuestionList.size()];
                        for (int j = 0; j < isSubmitExamAnswer.length; j++) {
                            isSubmitExamAnswer[j]=false;
                        }
                        break;
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                        globelInterface.questionLoadFinsh();
                    }
                },1000);
            }
        },1000);
    }

    /**
     * CheckBox禁用
     */
    public static void setCheckEnableFalse(CheckBox[] checkBoxs){
        for (int i = 0; i < checkBoxs.length; i++) {
            checkBoxs[i].setEnabled(false);
        }
    }

    public static String dateFormatUtil(String format){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    };

    /**
     * 强制弹出输入法
     */
    public static void showInputWindow(final Context context){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        },100);
    }

    /**
     * 答题结果
     * @param question 题目数据
     * @param checkBoxes 答案复选框
     * @return true：正确 false:错误
     */
    public static boolean isTrueAnswerByQuestion(Question question, CheckBox[] checkBoxes) {

        question.setQuestion_select1(-1);
        question.setQuestion_select2(-1);
        question.setQuestion_select3(-1);
        question.setQuestion_select4(-1);

        if (checkBoxes[0].isChecked()) {
            question.setQuestion_select1(0);
            if (checkBoxes[1].isChecked()) {
                question.setQuestion_select2(1);
                if (checkBoxes[2].isChecked()) {
                    question.setQuestion_select3(2);
                    if (checkBoxes[3].isChecked()) {
                        question.setQuestion_select4(3);
                    }
                } else if (checkBoxes[3].isChecked()) {
                    question.setQuestion_select3(3);
                }
            } else if (checkBoxes[2].isChecked()) {
                question.setQuestion_select2(2);
                if (checkBoxes[3].isChecked()) {
                    question.setQuestion_select3(3);
                }
            } else if (checkBoxes[3].isChecked()) {
                question.setQuestion_select2(3);
            }
        } else if (checkBoxes[1].isChecked()) {
            question.setQuestion_select1(1);
            if (checkBoxes[2].isChecked()) {
                question.setQuestion_select2(2);
                if (checkBoxes[3].isChecked()) {
                    question.setQuestion_select3(3);
                }
            } else if (checkBoxes[3].isChecked()) {
                question.setQuestion_select2(3);
            }
        } else if (checkBoxes[2].isChecked()) {
            question.setQuestion_select1(2);
            if (checkBoxes[3].isChecked()) {
                question.setQuestion_select2(3);
            }
        } else if (checkBoxes[3].isChecked()) {
            question.setQuestion_select1(3);
        }
        if (question.getQuestion_answer1() != question.getQuestion_select1()
                || question.getQuestion_answer2() != question.getQuestion_select2()
                || question.getQuestion_answer3() != question.getQuestion_select3()
                || question.getQuestion_answer4() != question.getQuestion_select4()) {
            return false;

        }
        return true;
    }

    //全局接口
    public interface GlobelInterface{
        //题库加载完成时回调
        void questionLoadFinsh();
    }

}
