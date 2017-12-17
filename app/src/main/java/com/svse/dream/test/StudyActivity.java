package com.svse.dream.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svse.dream.apdater.QuestionDialogAdapter;
import com.svse.dream.apdater.viewPagerAdapter;
import com.svse.dream.bean.Question;
import com.svse.dream.utils.GlobelVar;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(Color.parseColor("#F6927B"));
        initView();
    }

    private LinearLayout studyQuestionList;
    private TextView studyCountTrue;
    private TextView studyCountFalse;
    private TextView studyCount;
    private TextView studyFavorites;
    private ViewPager viewPager;
    private List<View> viewList;
    private View question_dialog_view;
    private GridView question_dialog_gridview;
    private int[] question_num_arr;
    private Dialog dialog;

    public void initView(){
        //初始化控件
        studyQuestionList = (LinearLayout) findViewById(R.id.study_question_list);
        studyCountTrue = (TextView) findViewById(R.id.study_count_true);
        studyCountFalse = (TextView) findViewById(R.id.study_count_false);
        studyCount = (TextView) findViewById(R.id.study_count);
        studyFavorites = (TextView) findViewById(R.id.study_favorites);
        studyQuestionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyQuestionList();
            }
        });

        //初始化数据
        GlobelVar.questionIndex=0;
        GlobelVar.questionTrue=0;
        GlobelVar.questionFalse=0;
        setStudyCountTrue();
        setStudyCountFalse();
        studyCount.setText((GlobelVar.questionIndex+1)+"/"+GlobelVar.studyQuestionList.size());

        //初始化ViewPager
        viewList=new ArrayList<>();
        for (int i = 0; i < GlobelVar.studyQuestionList.size(); i++) {
            initIndexView(i);
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new viewPagerAdapter(this,viewList));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                GlobelVar.questionIndex=position;
                studyCount.setText((position+1)+"/"+GlobelVar.studyQuestionList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void studyQuestionList(){
        question_dialog_view = View.inflate(getApplication(), R.layout.study_dialog, null);
        question_dialog_gridview = (GridView) question_dialog_view.findViewById(R.id.question_dialog_gridview);
        question_num_arr = new int[GlobelVar.studyQuestionList.size()];
        for (int i = 0; i < question_num_arr.length; i++) {
            question_num_arr[i] = i;
        }
        question_dialog_gridview.setAdapter(new QuestionDialogAdapter(StudyActivity.this, question_num_arr));
        question_dialog_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int question_num = position;
                viewPager.setCurrentItem(question_num);
                dialog.dismiss();
            }
        });
        android.app.AlertDialog.Builder question_dialog=new android.app.AlertDialog.Builder(StudyActivity.this);
        question_dialog.setTitle("选择题目");
        question_dialog.setView(question_dialog_view);
        dialog=question_dialog.show();
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
        final Question question=GlobelVar.studyQuestionList.get(index);
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
        studyQuestion.setText("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+question.question_content);
        studyOsname.setText(question.getOsName());
        studyType.setText(question.getQuestion_answer2()==-1?"单选":"多选");
        studyAnswerA.setText(question.getQuestion_answerA());
        studyAnswerB.setText(question.getQuestion_answerB());
        studyAnswerC.setText(question.getQuestion_answerC());
        studyAnswerD.setText(question.getQuestion_answerD());
        studyAnswerResult.setText(question.getQuestion_explain());

        if (isSingleAnswer(index)){
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
                        if (!GlobelVar.isSubmitStudyAnswer[index]){
                            GlobelVar.isSubmitStudyAnswer[index]=true;
                            if (isTrueAnswer(index)){
                                GlobelVar.questionTrue++;
                                setStudyCountTrue();
                                viewPager.setCurrentItem(index+1);
                            }else {
                                GlobelVar.questionFalse++;
                                setStudyCountFalse();
                                GlobelVar.getStudyErrorQuestionList.add(question);
                            }
                            studyAnswer.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }else {
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
                    if (!GlobelVar.isSubmitStudyAnswer[index]){
                        GlobelVar.isSubmitStudyAnswer[index] = true;
                        if (isTrueAnswer(index)){
                            GlobelVar.questionTrue++;
                            setStudyCountTrue();
                            viewPager.setCurrentItem(index+1);
                        }else {
                            GlobelVar.questionFalse++;
                            setStudyCountFalse();
                            GlobelVar.getStudyErrorQuestionList.add(question);
                        }
                        studyMulitple.setVisibility(View.GONE);
                        studyAnswer.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        viewList.add(view);
    }

    public boolean isTrueAnswer(int question_num) {
        GlobelVar.studyQuestionList.get(question_num).setQuestion_select1(-1);
        GlobelVar.studyQuestionList.get(question_num).setQuestion_select2(-1);
        GlobelVar.studyQuestionList.get(question_num).setQuestion_select3(-1);
        GlobelVar.studyQuestionList.get(question_num).setQuestion_select4(-1);

        question_answer[0]= (CheckBox) viewList.get(question_num).findViewById(R.id.study_answer_a);
        question_answer[1]= (CheckBox) viewList.get(question_num).findViewById(R.id.study_answer_b);
        question_answer[2]= (CheckBox) viewList.get(question_num).findViewById(R.id.study_answer_c);
        question_answer[3]= (CheckBox) viewList.get(question_num).findViewById(R.id.study_answer_d);

        if (question_answer[0].isChecked()) {
            GlobelVar.studyQuestionList.get(question_num).setQuestion_select1(0);
            if (question_answer[1].isChecked()) {
                GlobelVar.studyQuestionList.get(question_num).setQuestion_select2(1);
                if (question_answer[2].isChecked()) {
                    GlobelVar.studyQuestionList.get(question_num).setQuestion_select3(2);
                    if (question_answer[3].isChecked()) {
                        GlobelVar.studyQuestionList.get(question_num).setQuestion_select4(3);
                    }
                } else if (question_answer[3].isChecked()) {
                    GlobelVar.studyQuestionList.get(question_num).setQuestion_select3(3);
                }
            } else if (question_answer[2].isChecked()) {
                GlobelVar.studyQuestionList.get(question_num).setQuestion_select2(2);
                if (question_answer[3].isChecked()) {
                    GlobelVar.studyQuestionList.get(question_num).setQuestion_select3(3);
                }
            } else if (question_answer[3].isChecked()) {
                GlobelVar.studyQuestionList.get(question_num).setQuestion_select2(3);
            }
        } else if (question_answer[1].isChecked()) {
            GlobelVar.studyQuestionList.get(question_num).setQuestion_select1(1);
            if (question_answer[2].isChecked()) {
                GlobelVar.studyQuestionList.get(question_num).setQuestion_select2(2);
                if (question_answer[3].isChecked()) {
                    GlobelVar.studyQuestionList.get(question_num).setQuestion_select3(3);
                }
            } else if (question_answer[3].isChecked()) {
                GlobelVar.studyQuestionList.get(question_num).setQuestion_select2(3);
            }
        } else if (question_answer[2].isChecked()) {
            GlobelVar.studyQuestionList.get(question_num).setQuestion_select1(2);
            if (question_answer[3].isChecked()) {
                GlobelVar.studyQuestionList.get(question_num).setQuestion_select2(3);
            }
        } else if (question_answer[3].isChecked()) {
            GlobelVar.studyQuestionList.get(question_num).setQuestion_select1(3);
        }
        if (GlobelVar.studyQuestionList.get(question_num).getQuestion_answer1() != GlobelVar.studyQuestionList.get(question_num).getQuestion_select1()
                || GlobelVar.studyQuestionList.get(question_num).getQuestion_answer2() != GlobelVar.studyQuestionList.get(question_num).getQuestion_select2()
                || GlobelVar.studyQuestionList.get(question_num).getQuestion_answer3() != GlobelVar.studyQuestionList.get(question_num).getQuestion_select3()
                || GlobelVar.studyQuestionList.get(question_num).getQuestion_answer4() != GlobelVar.studyQuestionList.get(question_num).getQuestion_select4()) {
            return false;

        }
        return true;
    }

    public boolean isSingleAnswer(int index){
        return GlobelVar.studyQuestionList.get(index).getQuestion_answer2()==-1?true:false;
    }

    public void setStudyCountTrue(){
        studyCountTrue.setText(GlobelVar.questionTrue+"");
    }

    public void setStudyCountFalse(){
        studyCountFalse.setText(GlobelVar.questionFalse+"");
    }

}
