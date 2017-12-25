package com.svse.dream.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svse.dream.apdater.PerpareAttrsAdapter;
import com.svse.dream.bean.OSInfo;
import com.svse.dream.utils.Globel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerpareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perpare);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(Color.parseColor("#F6927B"));
        initView();
    }

    private TextView perpareAttrTip;
    private TextView perpareHistoryTip;
    private ListView perpareAttrListview;
    private ListView perpareHistoryListview;
    private RelativeLayout perpareStart;
    private TextView perpareMaxgrade;

    public void initView(){
        //初始化控件
        perpareAttrListview = (ListView) findViewById(R.id.perpare_attr_listview);
        perpareHistoryListview = (ListView) findViewById(R.id.perpare_history_listview);
        perpareAttrTip = (TextView) findViewById(R.id.perpare_attr_tip);
        perpareHistoryTip = (TextView) findViewById(R.id.perpare_history_tip);
        perpareStart = (RelativeLayout) findViewById(R.id.perpare_start);
        perpareMaxgrade = (TextView) findViewById(R.id.perpare_maxgrade);
        perpareMaxgrade.setText(Globel.getSharedPreferences(this).getInt(Globel.EXAM_GRADE_MAX,0)+"");

        //初始化考试参数
        Globel.examAttrsMap=new HashMap<>();
        List<OSInfo> osInfoList = Globel.getOsInfoList(this);
        if (osInfoList==null){
            perpareAttrListview.setVisibility(View.GONE);
            perpareAttrTip.setVisibility(View.VISIBLE);
        }else{
            perpareAttrListview.setVisibility(View.VISIBLE);
            perpareAttrTip.setVisibility(View.GONE);
            perpareAttrListview.setAdapter(new PerpareAttrsAdapter(this,osInfoList));
        }

        //开始考试
        perpareStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAttrs()){
                    Globel.setQuestionList(Globel.QUESTION_TYPE_EXAM, null,Globel.LOADING_LIB_EXAM, PerpareActivity.this, new Globel.GlobelInterface() {
                        @Override
                        public void questionLoadFinsh() {
                            View view=View.inflate(PerpareActivity.this,R.layout.perpare_confirm_dialog,null);
                            initExamData(view);
                            new AlertDialog.Builder(PerpareActivity.this)
                                    .setTitle("试卷详情")
                                    .setView(view)
                                    .setCancelable(false)
                                    .setPositiveButton("开始", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(PerpareActivity.this,ExamActivity.class));
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("放弃",null)
                                    .show();
                        }
                    });
                }
            }
        });

    }

    private int total;
    private String examinfoStr;
    //参数合法性验证
    public boolean checkAttrs(){
        total=0;
        examinfoStr="";
        if (Globel.examAttrsMap.size()==0){
            Toast.makeText(this,"请先分配考试题目",Toast.LENGTH_SHORT).show();
            return false;
        }
        int count=0;
        for (Map.Entry<String,Integer> entry:Globel.examAttrsMap.entrySet()){
            total+=entry.getValue();
            if (count==Globel.examAttrsMap.size()-1){
                examinfoStr+="科目："+entry.getKey()+"\t\t\t"+"题量："+entry.getValue();
            }else{
                examinfoStr+="科目："+entry.getKey()+"\t\t\t"+"题量："+entry.getValue()+"\n";
            }
            count++;
        }
        if (total>Globel.exam_count_max||total<Globel.exam_count_min){
            Toast.makeText(this,"MIN="+Globel.exam_count_min+" MAX="+Globel.exam_count_max,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void initExamData(View view){
        String[] dateArray = Globel.dateFormatUtil("MM/dd HH:mm",new Date()).split(" ");
        Globel.exam_total_score=100;
        Globel.exam_total_num=total;
        Globel.exam_avg_score=Globel.doubleToDoubleBit(Globel.exam_total_score,Globel.exam_total_num,1);
        Globel.exam_start_date=dateArray[0];
        Globel.exam_start_time=dateArray[1];

        TextView examTotalScore = (TextView) view.findViewById(R.id.exam_total_score);
        TextView examTotalNum = (TextView) view.findViewById(R.id.exam_total_num);
        TextView examAvgScore = (TextView) view.findViewById(R.id.exam_avg_score);
        TextView examStartDate = (TextView) view.findViewById(R.id.exam_start_date);
        TextView examStartTime = (TextView) view.findViewById(R.id.exam_start_time);
        TextView examInfo = (TextView) view.findViewById(R.id.exam_info);

        examTotalScore.setText("试卷满分："+Globel.exam_total_score+"分");
        examTotalNum.setText("题量总数："+Globel.exam_total_num+"题");
        examAvgScore.setText("平均分值："+Globel.exam_avg_score+"分");
        examStartDate.setText("开始日期："+Globel.exam_start_date);
        examStartTime.setText("开始时间："+Globel.exam_start_time);
        examInfo.setText(examinfoStr);
    }

}
