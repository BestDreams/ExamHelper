package com.svse.dream.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.svse.dream.apdater.QuestionDialogAdapter;
import com.svse.dream.apdater.ViewPagerAdapter;
import com.svse.dream.bean.MyGrade;
import com.svse.dream.bean.Question;
import com.svse.dream.utils.Globel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(Color.parseColor("#F6927B"));
        initView();
    }



    private LinearLayout studyQuestionList;
    private TextView studyTitle;
    private LinearLayout studyLayoutTrue;
    private LinearLayout studyLayoutFalse;
    private RelativeLayout studySubmitExam;
    private TextView studyCount;
    private LinearLayout studyFavorites;
    private ViewPager viewPager;
    private List<View> viewList;
    private View question_dialog_view;
    private GridView question_dialog_gridview;
    private int[] question_num_arr;
    private Dialog dialog;
    private long statr_ms=0;

    public void initView(){
        //初始化控件
        studyTitle = (TextView) findViewById(R.id.study_title);
        studyLayoutTrue = (LinearLayout) findViewById(R.id.study_layout_true);
        studyLayoutFalse = (LinearLayout) findViewById(R.id.study_layout_false);
        studySubmitExam = (RelativeLayout) findViewById(R.id.study_submit_exam);
        studyQuestionList = (LinearLayout) findViewById(R.id.study_question_list);
        studyCount = (TextView) findViewById(R.id.study_count);
        studyFavorites = (LinearLayout) findViewById(R.id.study_favorites);
        studyLayoutTrue.setVisibility(View.GONE);
        studyLayoutFalse.setVisibility(View.GONE);
        studySubmitExam.setVisibility(View.VISIBLE);
        studyQuestionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyQuestionList();
            }
        });

        studySubmitExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ExamActivity.this)
                        .setTitle("提示")
                        .setMessage("确定交卷吗？")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                savaGrade();
                                finish();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });

        //初始化ViewPager
        viewList=new ArrayList<>();
        for (int i = 0; i < Globel.examQuestionList.size(); i++) {
            initIndexView(i);
        }
        statr_ms= System.currentTimeMillis();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(this,viewList));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Globel.examIndex=position;
                studyCount.setText((position+1)+"/"+ Globel.examQuestionList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        studyFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.insertQuestionToMylib(ExamActivity.this, Globel.examQuestionList.get(Globel.examIndex), Globel.QUESTION_TYPE_FAVORITE);
            }
        });

        //初始化数据
        studyTitle.setText("考试模式");
        studyCount.setText((Globel.examIndex+1)+"/"+ Globel.examQuestionList.size());
    }

    /**
     * 保存考试成绩
     */
    public void savaGrade(){
        String osNames="";
        for(Map.Entry<String,Integer> entry:Globel.examAttrsMap.entrySet()){
            osNames+=entry.getKey()+"_"+entry.getValue()+"\t";
        }
        String[] dateArray = Globel.dateFormatUtil("MM/dd HH:mm",new Date()).split(" ");
        Globel.exam_end_date=dateArray[0];
        Globel.exam_end_time=dateArray[1];
        Globel.exam_grade=Globel.exam_avg_score*Globel.examTrue;
        Globel.exam_correct_procent=Globel.doubleToDoubleBit(Globel.examTrue,Globel.exam_total_num,4)*100;
        Integer submitNo=Globel.exam_total_num-Globel.examTrue-Globel.examFalse;
        Integer submitYes=Globel.examTrue+Globel.examFalse;
        String startTime=Globel.exam_start_date+" "+Globel.exam_start_time;
        String endTime=Globel.exam_end_date+" "+Globel.exam_end_time;
        statr_ms=System.currentTimeMillis()-statr_ms;
        MyGrade myGrade=new MyGrade(null,osNames,(int) Globel.exam_grade,submitNo,submitYes,Globel.examFalse,Globel.examTrue,startTime,endTime,Globel.msToTime(statr_ms),Globel.exam_correct_procent+"%",Globel.exam_total_num);
        MainActivity.insertGradeToMyGrade(myGrade);
        int maxGrade = Globel.getSharedPreferences(this).getInt(Globel.EXAM_GRADE_MAX, 0);
        if (Globel.exam_grade>maxGrade){
            Globel.getSharedPreferencesEditor(this).putInt(Globel.EXAM_GRADE_MAX, (int) Globel.exam_grade).commit();
        }
        startActivity(new Intent(ExamActivity.this,ExamGradeActivity.class));
    }

    public void studyQuestionList(){
        question_dialog_view = View.inflate(getApplication(), R.layout.study_dialog, null);
        question_dialog_gridview = (GridView) question_dialog_view.findViewById(R.id.question_dialog_gridview);
        question_num_arr = new int[Globel.examQuestionList.size()];
        for (int i = 0; i < question_num_arr.length; i++) {
            question_num_arr[i] = i;
        }
        question_dialog_gridview.setAdapter(new QuestionDialogAdapter(ExamActivity.this, question_num_arr));
        question_dialog_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int question_num = position;
                viewPager.setCurrentItem(question_num);
                dialog.dismiss();
            }
        });
        AlertDialog.Builder question_dialog=new AlertDialog.Builder(ExamActivity.this);
        question_dialog.setTitle("选择题目");
        question_dialog.setView(question_dialog_view);
        dialog=question_dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("错题已添加至错题本，确定退出吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消",null)
                    .setNeutralButton("交卷", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            savaGrade();
                            finish();
                        }
                    })
                    .show();
        }
        return super.onKeyDown(keyCode, event);
    }

    private TextView studyQuestion;
    private TextView studyOsname;
    private TextView studyType;
    private CheckBox studyAnswerA;
    private CheckBox studyAnswerB;
    private CheckBox studyAnswerC;
    private CheckBox studyAnswerD;
    private RelativeLayout studyAnswer;
    private TextView studyAnswerResult;
    private RelativeLayout studyMulitple;
    private CheckBox[] question_answer;

    public void initIndexView(final int index){
        final Question question= Globel.examQuestionList.get(index);
        View view=View.inflate(this,R.layout.study_viewpager,null);
        //实例化控件
        studyQuestion = (TextView) view.findViewById(R.id.study_question);
        studyOsname = (TextView) view.findViewById(R.id.study_osname);
        studyType = (TextView) view.findViewById(R.id.study_type);
        studyAnswerA = (CheckBox) view.findViewById(R.id.study_answer_a);
        studyAnswerB = (CheckBox) view.findViewById(R.id.study_answer_b);
        studyAnswerC = (CheckBox) view.findViewById(R.id.study_answer_c);
        studyAnswerD = (CheckBox) view.findViewById(R.id.study_answer_d);
        studyAnswer = (RelativeLayout) view.findViewById(R.id.study_answer);
        studyAnswerResult = (TextView) view.findViewById(R.id.study_answer_result);
        studyMulitple = (RelativeLayout) view.findViewById(R.id.study_mulitple);
        question_answer=new CheckBox[]{studyAnswerA,studyAnswerB,studyAnswerC,studyAnswerD};

        //绑定数据
        studyQuestion.setText("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+question.getQuestion_content());
        studyOsname.setText(question.getOsName());
        studyType.setText(question.getQuestion_answer2()==-1?"单选":"多选");
        studyAnswerA.setText(question.getQuestion_answerA());
        studyAnswerB.setText(question.getQuestion_answerB());
        studyAnswerC.setText(question.getQuestion_answerC());
        studyAnswerD.setText(question.getQuestion_answerD());
        studyAnswerResult.setText(question.getQuestion_explain());

        if (isSingleAnswer(index)){
            //如果是单选题
            studyMulitple.setVisibility(View.GONE);
            for (int i = 0; i < question_answer.length; i++) {
                final int finalI = i;
                question_answer[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        question_answer[0]= (CheckBox) viewList.get(index).findViewById(R.id.study_answer_a);
                        question_answer[1]= (CheckBox) viewList.get(index).findViewById(R.id.study_answer_b);
                        question_answer[2]= (CheckBox) viewList.get(index).findViewById(R.id.study_answer_c);
                        question_answer[3]= (CheckBox) viewList.get(index).findViewById(R.id.study_answer_d);
                        studyAnswer = (RelativeLayout) viewList.get(index).findViewById(R.id.study_answer);

                        if (!Globel.isSubmitExamAnswer[index]){
                            Globel.isSubmitExamAnswer[index]=true;
                            if (Globel.isTrueAnswerByQuestion(question,question_answer)){
                                Globel.examTrue++;
                            }else {
                                Globel.examFalse++;
                                MainActivity.insertQuestionToMylib(null,question, Globel.QUESTION_TYPE_ERROR);
                            }
                            Globel.setCheckEnableFalse(question_answer);
                            viewPager.setCurrentItem(index+1);
                        }
                    }
                });
            }
        }else {
            //如果是多选选题
            studyMulitple.setVisibility(View.VISIBLE);
            studyMulitple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    question_answer[0]= (CheckBox) viewList.get(index).findViewById(R.id.study_answer_a);
                    question_answer[1]= (CheckBox) viewList.get(index).findViewById(R.id.study_answer_b);
                    question_answer[2]= (CheckBox) viewList.get(index).findViewById(R.id.study_answer_c);
                    question_answer[3]= (CheckBox) viewList.get(index).findViewById(R.id.study_answer_d);
                    studyMulitple = (RelativeLayout) viewList.get(index).findViewById(R.id.study_mulitple);
                    studyAnswer = (RelativeLayout) viewList.get(index).findViewById(R.id.study_answer);
                    if (!Globel.isSubmitExamAnswer[index]){
                        Globel.isSubmitExamAnswer[index] = true;
                        if (Globel.isTrueAnswerByQuestion(question,question_answer)){
                            Globel.examTrue++;
                        }else {
                            Globel.examFalse++;
                            MainActivity.insertQuestionToMylib(null,question, Globel.QUESTION_TYPE_ERROR);
                        }
                        Globel.setCheckEnableFalse(question_answer);
                        viewPager.setCurrentItem(index+1);
                        studyMulitple.setVisibility(View.GONE);
                    }
                }
            });
        }
        viewList.add(view);
    }

    @Override
    protected void onDestroy() {
        resetExamData();
        super.onDestroy();
    }

    /**
     * 初始化考试模式数据
     */
    public void resetExamData(){
        //考试题库
        Globel.examQuestionList=null;
        //索引
        Globel.examIndex=0;
        //正确
        Globel.examTrue=0;
        //错误
        Globel.examFalse=0;
        //题量限制
        Globel.exam_count_min=1;
        Globel.exam_count_max=50;
        //考试参数
        Globel.examAttrsMap=null;
        //题目状态（是否已提交答案）
        Globel.isSubmitExamAnswer=null;
        //试卷总分
        Globel.exam_total_score=100;
        //试卷总题量
        Globel.exam_total_num=0;
        //平均分值
        Globel.exam_avg_score=0;
        //开始日期
        Globel.exam_start_date="01/01";
        //开始时间
        Globel.exam_start_time="00:00";
        //结束日期
        Globel.exam_end_date="01/01";
        //结束时间
        Globel.exam_end_time="00:00";
        //考试成绩
        Globel.exam_grade=0;
        //正确率
        Globel.exam_correct_procent=0;
    }

    public boolean isSingleAnswer(int index){
        return Globel.examQuestionList.get(index).getQuestion_answer2()==-1?true:false;
    }

}
