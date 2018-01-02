package com.svse.dream.test;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.svse.dream.utils.Globel;

public class OpenSourceActivity extends AppCompatActivity {

    private Button codeMayun;
    private Button codeGithub;
    private ClipboardManager clipboardManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opensource);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(Color.parseColor("#F6927B"));
        clipboardManager= (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        codeMayun = (Button) findViewById(R.id.code_mayun);
        codeGithub = (Button) findViewById(R.id.code_github);
        codeMayun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData =ClipData.newPlainText("mayun",Globel.URL_OPEN_SOURCE_MAYUN);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(OpenSourceActivity.this,"码云项目地址已复制到剪贴板",Toast.LENGTH_SHORT).show();
            }
        });
        codeGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData =ClipData.newPlainText("github",Globel.URL_OPEN_SOURCE_GITHUB);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(OpenSourceActivity.this,"Github项目地址已复制到剪贴板",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
