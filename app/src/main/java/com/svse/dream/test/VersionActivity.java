package com.svse.dream.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.svse.dream.apdater.VersionApdater;
import com.svse.dream.bean.Version;
import com.svse.dream.dao.DataDaoImpl;

import java.util.List;

public class VersionActivity extends AppCompatActivity {
    private ListView history_listview;
    private List<Version> versionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(Color.parseColor("#F6927B"));
        history_listview = (ListView) findViewById(R.id.history_listview);
        versionList=new DataDaoImpl().getUpdateLog();
        history_listview.setAdapter(new VersionApdater(versionList,this));
    }
}
