package com.svse.dream.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.svse.dream.utils.Globel;

public class HelpActivity extends AppCompatActivity {

    private Button help_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(Color.parseColor("#F6927B"));
        help_btn= (Button) findViewById(R.id.help_btn);
        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globel.getSharedPreferencesEditor(HelpActivity.this).putBoolean("isFirstHelp",true).commit();
                startActivity(new Intent(HelpActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}
