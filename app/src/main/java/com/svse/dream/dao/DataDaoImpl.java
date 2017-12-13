package com.svse.dream.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.svse.dream.bean.HistoryLog;
import com.svse.dream.bean.Question;
import com.svse.dream.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dream on 2016/12/4.
 */
public class DataDaoImpl {
    private SQLiteDatabase db;

    public DataDaoImpl() {
        db=new DBHelper().getHelper();
    }

    //获取所有表名
    public List<String> getAllTableName(){
        List<String> os=null;
        Cursor cursor=db.rawQuery("select name from sqlite_master where type='table'",null);
        if (cursor!=null){
            os=new ArrayList<>();
            cursor.moveToFirst();
            os.add(cursor.getString(cursor.getColumnIndex("name")));
            while (cursor.moveToNext()){
                if (!(cursor.getString(cursor.getColumnIndex("name")).equals("UpdateLog")||cursor.getString(cursor.getColumnIndex("name")).equals("android_metadata")||cursor.getString(cursor.getColumnIndex("name")).equals("sqlite_sequence")))
                os.add(cursor.getString(cursor.getColumnIndex("name")));
            }
        }
        return os;
    }

    //获取更新记录
    public List<HistoryLog> getUpdateLog(){
        List<HistoryLog> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from UpdateLog order by id  desc",null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                int id=cursor.getInt(cursor.getColumnIndex("ID"));
                String version=cursor.getString(cursor.getColumnIndex("version"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String content=cursor.getString(cursor.getColumnIndex("content"));
                String bug=cursor.getString(cursor.getColumnIndex("bug"));
                list.add(new HistoryLog(id,version,date,content,bug));
            }
        }
        return list;
    };

    //获取当前版本
    public String getVersion(){
        String version=null;
        Cursor cursor=db.rawQuery("select * from UpdateLog order by id desc limit 0,1",null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                int id=cursor.getInt(cursor.getColumnIndex("ID"));
                version=cursor.getString(cursor.getColumnIndex("version"));
            }
        }
        return version;
    };

    //根据OSNAME和ID查询某题
    public Question getQuestionsByOsNameAndId(String osName,String osId){
        Question question=null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+osName+" WHERE ID="+osId, null);
        if (cursor!=null){
                cursor.moveToNext();
                int id=cursor.getInt(cursor.getColumnIndex("ID"));
                String question_content=cursor.getString(cursor.getColumnIndex("question"));
                String question_answerA=cursor.getString(cursor.getColumnIndex("answerA"));
                String question_answerB=cursor.getString(cursor.getColumnIndex("answerB"));
                String question_answerC=cursor.getString(cursor.getColumnIndex("answerC"));
                String question_answerD=cursor.getString(cursor.getColumnIndex("answerD"));
                String question_explain=cursor.getString(cursor.getColumnIndex("Explain"));
                int question_answer1=cursor.getInt(cursor.getColumnIndex("answer1"));
                int question_answer2=cursor.getInt(cursor.getColumnIndex("answer2"));
                int question_answer3=cursor.getInt(cursor.getColumnIndex("answer3"));
                int question_answer4=cursor.getInt(cursor.getColumnIndex("answer4"));
                question=new Question(id,osName,question_content,question_answerA,question_answerB,question_answerC,question_answerD,question_explain,question_answer1,question_answer2,question_answer3,question_answer4);
        }
        return question;
    }

    //根据osName获取练习题库
    public List<Question> getStudyQuestionsByOsName(String osName){
        List<Question> studyQuestions=null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+osName, null);
        if (cursor!=null){
            studyQuestions=new ArrayList<>();
            while (cursor.moveToNext()){
                int id=cursor.getInt(cursor.getColumnIndex("ID"));
                String question_content=cursor.getString(cursor.getColumnIndex("question"));
                String question_answerA=cursor.getString(cursor.getColumnIndex("answerA"));
                String question_answerB=cursor.getString(cursor.getColumnIndex("answerB"));
                String question_answerC=cursor.getString(cursor.getColumnIndex("answerC"));
                String question_answerD=cursor.getString(cursor.getColumnIndex("answerD"));
                String question_explain=cursor.getString(cursor.getColumnIndex("Explain"));
                int question_answer1=cursor.getInt(cursor.getColumnIndex("answer1"));
                int question_answer2=cursor.getInt(cursor.getColumnIndex("answer2"));
                int question_answer3=cursor.getInt(cursor.getColumnIndex("answer3"));
                int question_answer4=cursor.getInt(cursor.getColumnIndex("answer4"));
                studyQuestions.add(new Question(id,osName,question_content,question_answerA,question_answerB,question_answerC,question_answerD,question_explain,question_answer1,question_answer2,question_answer3,question_answer4));
            }
        }
        return studyQuestions;
    }

    //根据questionType和questionNum集合获取考试题库
    public List<Question> getExamQuestionsByOsName(String[] questionType, int[] questionNum){
        List<Question> ExamQuestions=new ArrayList<>();;
        for (int i=0;i<questionType.length;i++){
            Cursor cursor = db.rawQuery("SELECT * FROM "+questionType[i]+" ORDER BY RANDOM() limit "+questionNum[i], null);
            if (cursor!=null){
                while (cursor.moveToNext()){
                    int id=cursor.getInt(cursor.getColumnIndex("ID"));
                    String question_content=cursor.getString(cursor.getColumnIndex("question"));
                    String question_answerA=cursor.getString(cursor.getColumnIndex("answerA"));
                    String question_answerB=cursor.getString(cursor.getColumnIndex("answerB"));
                    String question_answerC=cursor.getString(cursor.getColumnIndex("answerC"));
                    String question_answerD=cursor.getString(cursor.getColumnIndex("answerD"));
                    String question_explain=cursor.getString(cursor.getColumnIndex("Explain"));
                    int question_answer1=cursor.getInt(cursor.getColumnIndex("answer1"));
                    int question_answer2=cursor.getInt(cursor.getColumnIndex("answer2"));
                    int question_answer3=cursor.getInt(cursor.getColumnIndex("answer3"));
                    int question_answer4=cursor.getInt(cursor.getColumnIndex("answer4"));
                    ExamQuestions.add(new Question(id,questionType[i],question_content,question_answerA,question_answerB,question_answerC,question_answerD,question_explain,question_answer1,question_answer2,question_answer3,question_answer4));
                }
            }
        }
        return ExamQuestions;
    }

    //获取表的行数
    public int getTableNum(String osName){
        Cursor cursor=db.rawQuery("select count(*) from "+osName,null);
        cursor.moveToNext();
        return cursor.getInt(cursor.getColumnIndex("count(*)"));
    }

    //关闭连接
    public void closeDB(){
        db.close();
    }
}