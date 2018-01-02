package com.svse.dream.test;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.svse.dream.bean.MyGrade;
import com.svse.dream.bean.Question;
import com.svse.dream.dao.DataDaoImpl;
import com.svse.dream.fragment.MainFragment;
import com.svse.dream.utils.DBHelper;
import com.svse.dream.utils.Globel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(Color.parseColor("#F6927B"));
        initSlideMenu();
        initView();
        initDatabase();
        updateVersion();
    }

    /**
     * 检查版本
     */
    public void updateVersion(){
        BDAutoUpdateSDK.uiUpdateAction(MainActivity.this, new UICheckUpdateCallback() {
            @Override
            public void onNoUpdateFound() {
                if (Globel.isCheckUpdate){
                    Toast.makeText(MainActivity.this, "未找到更新版本", Toast.LENGTH_SHORT).show();
                }
                Globel.isCheckUpdate=false;
            }

            @Override
            public void onCheckComplete() {
                Globel.isCheckUpdate=false;
            }
        });
    }

    /**
     * 侧滑菜单
     */
    public static SlidingMenu slidingMenu;
    private LinearLayout slideMylib;
    private LinearLayout slideUpdate;
    private LinearLayout slideHelp;
    private LinearLayout slideVersion;
    private LinearLayout slideAbout;
    private LinearLayout slideShare;
    private LinearLayout slideExit;
    private TextView currentVersion;

    public void initSlideMenu(){
        Display display = getWindowManager().getDefaultDisplay();
        slidingMenu=new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset((int)(display.getWidth()*0.45));
        slidingMenu.setFadeDegree(0.3f);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slidemenu_main);

        //实例化菜单View
        slideMylib = (LinearLayout) findViewById(R.id.slide_mylib);
        slideUpdate = (LinearLayout) findViewById(R.id.slide_update);
        slideHelp = (LinearLayout) findViewById(R.id.slide_help);
        slideVersion = (LinearLayout) findViewById(R.id.slide_version);
        slideAbout = (LinearLayout) findViewById(R.id.slide_about);
        slideShare = (LinearLayout) findViewById(R.id.slide_share);
        slideExit = (LinearLayout) findViewById(R.id.slide_exit);
        currentVersion = (TextView) findViewById(R.id.currentVersion);
        currentVersion.setText("Version_"+new DataDaoImpl().getVersion());
        slideMylib.setOnClickListener(this);
        slideUpdate.setOnClickListener(this);
        slideHelp.setOnClickListener(this);
        slideVersion.setOnClickListener(this);
        slideAbout.setOnClickListener(this);
        slideShare.setOnClickListener(this);
        slideExit.setOnClickListener(this);
    }

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainFragment mainFragment;
    private Fragment[] fragments;
    private LinearLayout mainExam;



    public void initView(){
        mainExam = (LinearLayout) findViewById(R.id.main_exam);
        mainExam.setOnClickListener(this);
        FrameLayout framelayout = (FrameLayout) findViewById(R.id.framelayout);
        mainFragment=new MainFragment();
        fragments=new Fragment[]{mainFragment};
        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            fragmentTransaction.add(R.id.framelayout,fragments[i]);
        }
        fragmentTransaction.commit();
    }

    private DBHelper dbHelper;
    public static SQLiteDatabase dbReader,dbWriter;
    public void initDatabase(){
        dbHelper = new DBHelper(getApplicationContext());
        dbReader=dbHelper.getReadableDatabase();
        dbWriter=dbHelper.getWritableDatabase();
    }

    public static void insertQuestionToMylib(Context context,Question question, int type){
        String isExists="select * from my_lib where question_ID="+question.getQuestion_ID()+" and osName=\""+question.getOsName()+"\" and my_type="+type;
        Cursor cursor = dbReader.rawQuery(isExists, null);
        if (cursor==null||cursor.getCount()==0) {
            String sql = "insert into my_lib values(null," + question.getQuestion_ID() + "," + type + ",\"" + question.getOsName() + "\",\"" +
                    stringConvert(question.getQuestion_content()) + "\",\"" +
                    stringConvert(question.getQuestion_answerA()) + "\",\"" +
                    stringConvert(question.getQuestion_answerB()) + "\",\"" +
                    stringConvert(question.getQuestion_answerC()) + "\",\"" +
                    stringConvert(question.getQuestion_answerD()) + "\",\"" +
                    stringConvert(question.getQuestion_explain()) + "\"," +
                    question.getQuestion_answer1() + "," +
                    question.getQuestion_answer2() + "," +
                    question.getQuestion_answer3() + "," +
                    question.getQuestion_answer4() + ")";
            dbWriter.execSQL(sql);
            if (type== Globel.QUESTION_TYPE_FAVORITE){
                Toast.makeText(context,"记得来收藏夹看我哟~~",Toast.LENGTH_SHORT).show();
            }
        }else{
            if (type== Globel.QUESTION_TYPE_FAVORITE){
                Toast.makeText(context,"你已经收藏过啦~~",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void insertGradeToMyGrade(MyGrade myGrade){
        String sql = "insert into my_grade values(null,\"" +
                myGrade.getOsNames() + "\"," +
                myGrade.getGrade() + "," +
                myGrade.getSubmitNo() + "," +
                myGrade.getSubmitYes() + "," +
                myGrade.getSubmitError() + "," +
                myGrade.getSubmitCorrect() + ",\"" +
                myGrade.getStartTime() + "\",\"" +
                myGrade.getEndTime() + "\",\"" +
                myGrade.getTotalTime() + "\",\"" +
                myGrade.getCorrectProcent() + "\"," +
                myGrade.getTotalNum() + ")";
        dbWriter.execSQL(sql);
    }

    public static List<MyGrade> getMyGradeList(String sql){
        List<MyGrade> list=new ArrayList<>();
        Cursor cursor = dbReader.rawQuery(sql, null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                Integer id = cursor.getInt(cursor.getColumnIndex("_id"));
                String osNames = cursor.getString(cursor.getColumnIndex("osNames"));
                Integer grade = cursor.getInt(cursor.getColumnIndex("grade"));
                Integer submitNo = cursor.getInt(cursor.getColumnIndex("submitNo"));
                Integer submitYes = cursor.getInt(cursor.getColumnIndex("submitYes"));
                Integer submitError = cursor.getInt(cursor.getColumnIndex("submitError"));
                Integer submitCorrect = cursor.getInt(cursor.getColumnIndex("submitCorrect"));
                String startTime = cursor.getString(cursor.getColumnIndex("startTime"));
                String endTime = cursor.getString(cursor.getColumnIndex("endTime"));
                String totalTime = cursor.getString(cursor.getColumnIndex("totalTime"));
                String correctProcent = cursor.getString(cursor.getColumnIndex("correctProcent"));
                Integer totalNum = cursor.getInt(cursor.getColumnIndex("totalNum"));
                list.add(new MyGrade(id,osNames,grade,submitNo,submitYes,submitError,submitCorrect,startTime,endTime,totalTime,correctProcent,totalNum));
            }
        }
        return list;
    }


    public static void removeQuestionFromMylib(Integer id){
            String sql="delete from my_lib where _id="+id;
            dbWriter.execSQL(sql);
    }

    private static String stringConvert(String str){
        return str.replaceAll(",","，").replaceAll("\"","'");
    };

    @Override
    protected void onDestroy() {
        DBHelper.closeDB();
        if (dbWriter!=null){
            dbWriter.close();
        }
        if (dbReader!=null){
            dbReader.close();
        }
        if (dbHelper!=null){
            dbHelper.close();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("确定退出吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消",null)
                    .show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.slide_mylib:
                startActivity(new Intent(this,LibImprotActivity.class));
                finish();
                break;
            case R.id.slide_update:
                Globel.isCheckUpdate=true;
                updateVersion();
                break;
            case R.id.slide_help:
                Globel.HELP_STATE=true;
                startActivity(new Intent(this,HelpActivity.class));
                break;
            case R.id.slide_version:
                startActivity(new Intent(this,VersionActivity.class));
                break;
            case R.id.slide_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.slide_share:
                startActivity(new Intent(this,OpenSourceActivity.class));
                break;
            case R.id.slide_exit:
                finish();
                break;
            case R.id.main_exam:
                startActivity(new Intent(this,PerpareActivity.class));
                break;
        }
    }

}
