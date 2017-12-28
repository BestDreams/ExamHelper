package com.svse.dream.test;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svse.dream.apdater.ExamInfoAdapter;
import com.svse.dream.bean.ExamInfo;
import com.svse.dream.bean.MyGrade;
import com.svse.dream.dao.DataDaoImpl;
import com.svse.dream.utils.Globel;

import java.util.ArrayList;
import java.util.List;

public class ExamGradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(Color.parseColor("#F6927B"));
        initView();
    }

    private TextView examMygrade;
    private TextView examSubmitNo;
    private TextView examSubmitYes;
    private TextView examSubmitError;
    private TextView examSubmitCorrect;
    private ListView examListviewInfo;
    private RelativeLayout examAgain;
    private RelativeLayout examToErrorbook;
    private RelativeLayout examToMain;

    public void initView(){
        examMygrade = (TextView) findViewById(R.id.exam_mygrade);
        examSubmitNo = (TextView) findViewById(R.id.exam_submit_no);
        examSubmitYes = (TextView) findViewById(R.id.exam_submit_yes);
        examSubmitError = (TextView) findViewById(R.id.exam_submit_error);
        examSubmitCorrect = (TextView) findViewById(R.id.exam_submit_correct);
        examListviewInfo = (ListView) findViewById(R.id.exam_listview_info);
        examAgain = (RelativeLayout) findViewById(R.id.exam_again);
        examToErrorbook = (RelativeLayout) findViewById(R.id.exam_to_errorbook);
        examToMain = (RelativeLayout) findViewById(R.id.exam_to_main);
        examAgain.setOnClickListener(new MyOnClickListener());
        examToErrorbook.setOnClickListener(new MyOnClickListener());
        examToMain.setOnClickListener(new MyOnClickListener());

        //绑定数据
        List<MyGrade> myGradeList = MainActivity.getMyGradeList(Globel.SQL_GET_CURRENT_GRADE);
        if (myGradeList!=null&&myGradeList.size()>0){
            MyGrade myGrade=myGradeList.get(0);

            examMygrade.setText(myGrade.getGrade()+"");
            examSubmitNo.setText(myGrade.getSubmitNo()+"");
            examSubmitYes.setText(myGrade.getSubmitYes()+"");
            examSubmitError.setText(myGrade.getSubmitError()+"");
            examSubmitCorrect.setText(myGrade.getSubmitCorrect()+"");

            List<ExamInfo> list=new ArrayList<>();
            list.add(new ExamInfo(R.mipmap.exam_start_time,"开始时间", myGrade.getStartTime()));
            list.add(new ExamInfo(R.mipmap.exam_end_time,"结束时间", myGrade.getEndTime()));
            list.add(new ExamInfo(R.mipmap.exam_total_time,"用时",myGrade.getTotalTime()));
            list.add(new ExamInfo(R.mipmap.exam_correct_procent,"正确率",myGrade.getCorrectProcent()));
            list.add(new ExamInfo(R.mipmap.exam_total_num,"总题量",myGrade.getTotalNum()+""));
            list.add(new ExamInfo(R.mipmap.exam_osname,"科目",myGrade.getOsNames()));

            examListviewInfo.setAdapter(new ExamInfoAdapter(this,list));
        }
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

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.exam_again:
                    startActivity(new Intent(ExamGradeActivity.this,PerpareActivity.class));
                    finish();
                    break;
                case R.id.exam_to_errorbook:
                    bindErrorBookDataAndToActivity();
                    break;
                case R.id.exam_to_main:
                    startActivity(new Intent(ExamGradeActivity.this,MainActivity.class));
                    finish();
                    break;
            }
        }
    }

    public void bindErrorBookDataAndToActivity(){
        Globel.ErrorBookList = new DataDaoImpl().getErrorBookQuestions();
        if (Globel.ErrorBookList==null||Globel.ErrorBookList.size()==0){
            Toast.makeText(ExamGradeActivity.this,Globel.EMPTY_TIP,Toast.LENGTH_SHORT).show();
        }else{
            Globel.isSubmitErrorBookStudyAnswer=new Boolean[Globel.ErrorBookList.size()];
            for (int j = 0; j < Globel.isSubmitErrorBookStudyAnswer.length; j++) {
                Globel.isSubmitErrorBookStudyAnswer[j]=false;
            }
            startActivity(new Intent(this,ErrorBookActivity.class));
            finish();
        }
    }
}
