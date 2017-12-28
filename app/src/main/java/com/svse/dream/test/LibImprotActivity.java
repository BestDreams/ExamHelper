package com.svse.dream.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.svse.dream.apdater.OSListViewAdapter;
import com.svse.dream.dao.DataDaoImpl;
import com.svse.dream.utils.Globel;

import java.util.List;
import java.util.Set;

public class LibImprotActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences.Editor editor;
    private ListView OSlist;
    private DataDaoImpl dataDao;
    private OSListViewAdapter osListViewAdapter;
    private boolean isFirstHelp=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libimport);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(Color.parseColor("#F6927B"));
        init();
    }

    public void init() {
        isFirstHelp = Globel.getSharedPreferences(this).getBoolean("isFirstHelp", false);
        dataDao=new DataDaoImpl();
        editor= Globel.getSharedPreferencesEditor(this);
        OSlist = (ListView) findViewById(R.id.OSlist);
        List<String> allTableName = dataDao.getAllTableName();
        osListViewAdapter = new OSListViewAdapter(this, allTableName);
        OSlist.setAdapter(osListViewAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (!isFirstHelp){
                startActivity(new Intent(LibImprotActivity.this,HelpActivity.class));
            }else {
                startActivity(new Intent(LibImprotActivity.this,MainActivity.class));
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_classOk:
                Set<String> myOSlist = osListViewAdapter.getMyOSlist();
                editor.putBoolean("isFirstIn",true);
                editor.putStringSet("myOSlist",myOSlist);
                editor.commit();
                if (!isFirstHelp){
                    startActivity(new Intent(LibImprotActivity.this,HelpActivity.class));
                }else{
                    startActivity(new Intent(LibImprotActivity.this,MainActivity.class));
                }
                finish();
                break;
        }
    }
}
