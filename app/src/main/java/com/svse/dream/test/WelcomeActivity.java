package com.svse.dream.test;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.svse.dream.utils.Globel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initData();
    }
    boolean isFirstIn;
    public void initData(){
        isFirstIn = Globel.getSharedPreferences(this).getBoolean("isFirstIn", false);
        //如果是第一次进入，则加载数据库
        if (!isFirstIn){
            loadDB();
        }
        //如果是第一次则跳转到题库选择界面，否则跳转到主界面
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFirstIn){
                    startActivity(new Intent(WelcomeActivity.this,LibImprotActivity.class));
                }else{
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                }
                finish();
            }
        },2000);
    }

    /**
     * 加载数据库文件到指定目录
     */
    public void loadDB(){
        File folder=new File(Globel.DB_PATH);
        if (!folder.exists()){
            folder.mkdir();
        }
        try {
            InputStream is=getApplicationContext().getResources().openRawResource(R.raw.question);
            OutputStream os=new FileOutputStream(Globel.DB_PATH+ Globel.DB_NAME);
            byte[] buffer=new byte[1024];
            int length;
            while ((length=is.read(buffer))>0){
                os.write(buffer,0,length);
            }
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
