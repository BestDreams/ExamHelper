package com.svse.dream.test;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(Color.parseColor("#F6927B"));
    }
}
