package com.svse.dream.test;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.svse.dream.bean.Question;
import com.svse.dream.fragment.MainFragment;
import com.svse.dream.utils.DBHelper;
import com.svse.dream.utils.Globel;

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
    }

    public static SlidingMenu slidingMenu;
    private LinearLayout slideMylib;
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
        slideMylib.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.slide_mylib:
                startActivity(new Intent(this,LibImprotActivity.class));
                finish();
                break;
            case R.id.main_exam:
                startActivity(new Intent(this,PerpareActivity.class));
                break;
        }
    }
}
