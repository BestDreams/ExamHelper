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

import com.google.gson.Gson;
import com.svse.dream.apdater.QuestionDialogAdapter;
import com.svse.dream.apdater.ViewPagerAdapter;
import com.svse.dream.bean.Question;
import com.svse.dream.utils.Globel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private TextView studyTitle;
    private RelativeLayout studyRemoveCurr;
    private RelativeLayout studyRemoveAll;
    private TextView studyCountTrue;
    private TextView studyCountFalse;
    private TextView studyCount;
    private LinearLayout studyFavorites;
    private ViewPager viewPager;
    private List<View> viewList;
    private View question_dialog_view;
    private GridView question_dialog_gridview;
    private int[] question_num_arr;
    private Dialog dialog;

    public void initView(){
        //初始化控件
        studyTitle = (TextView) findViewById(R.id.study_title);
        studyRemoveCurr = (RelativeLayout) findViewById(R.id.study_remove_curr);
        studyRemoveAll = (RelativeLayout) findViewById(R.id.study_remove_all);
        studyQuestionList = (LinearLayout) findViewById(R.id.study_question_list);
        studyCountTrue = (TextView) findViewById(R.id.study_count_true);
        studyCountFalse = (TextView) findViewById(R.id.study_count_false);
        studyCount = (TextView) findViewById(R.id.study_count);
        studyFavorites = (LinearLayout) findViewById(R.id.study_favorites);
        studyQuestionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyQuestionList();
            }
        });

        //初始化ViewPager
        viewList=new ArrayList<>();
        for (int i = 0; i < Globel.studyQuestionList.size(); i++) {
            initIndexView(i);
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(this,viewList));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Globel.questionIndex=position;
                studyCount.setText((position+1)+"/"+ Globel.studyQuestionList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        studyFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.insertQuestionToMylib(StudyActivity.this, Globel.studyQuestionList.get(Globel.questionIndex), Globel.QUESTION_TYPE_FAVORITE);
            }
        });

        //初始化数据
        setStudyCountTrue();
        setStudyCountFalse();
        if (Globel.questionIndex!=0){
            viewPager.setCurrentItem(Globel.questionIndex);
        }
        studyTitle.setText("练习模式");
        studyCount.setText((Globel.questionIndex+1)+"/"+ Globel.studyQuestionList.size());
    }


    public void studyQuestionList(){
        question_dialog_view = View.inflate(getApplication(), R.layout.study_dialog, null);
        question_dialog_gridview = (GridView) question_dialog_view.findViewById(R.id.question_dialog_gridview);
        question_num_arr = new int[Globel.studyQuestionList.size()];
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
                    .setMessage("错题已添加至错题本，确定退出吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //保存当前练习数据
                            Map<String,String> currInfoMap=new HashMap<String, String>();
                            Question question = Globel.studyQuestionList.get(Globel.questionIndex);
                            currInfoMap.put(Globel.MODEL_CONTINUE_OSNAME,question.getOsName());
                            currInfoMap.put(Globel.MODEL_CONTINUE_CORRECT, Globel.questionTrue+"");
                            currInfoMap.put(Globel.MODEL_CONTINUE_ERROR, Globel.questionFalse+"");
                            currInfoMap.put(Globel.MODEL_CONTINUE_INDEX, Globel.questionIndex+"");
                            Globel.getSharedPreferencesEditor(StudyActivity.this).putString(Globel.MODEL_CONTINUE,new Gson().toJson(currInfoMap)).commit();
                            finish();
                        }
                    })
                    .setNegativeButton("取消",null)
                    .setNeutralButton("重新开始", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(StudyActivity.this,StudyActivity.class));
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
        final Question question= Globel.studyQuestionList.get(index);
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
        studyQuestion.setText("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+question.getQuestion_content());
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

                        if (!Globel.isSubmitStudyAnswer[index]){
                            Globel.isSubmitStudyAnswer[index]=true;
                            if (Globel.isTrueAnswerByQuestion(question,question_answer)){
                                Globel.questionTrue++;
                                setStudyCountTrue();
                                viewPager.setCurrentItem(index+1);
                            }else {
                                Globel.questionFalse++;
                                setStudyCountFalse();
                                question_answer[finalI].setButtonDrawable(R.mipmap.study_result_false);
                                question_answer[question.getQuestion_answer1()].setButtonDrawable(R.mipmap.study_result_true);
                                MainActivity.insertQuestionToMylib(null,question, Globel.QUESTION_TYPE_ERROR);
                            }
                            Globel.setCheckEnableFalse(question_answer);
                            studyAnswer.setVisibility(View.VISIBLE);
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
                    if (!Globel.isSubmitStudyAnswer[index]){
                        Globel.isSubmitStudyAnswer[index] = true;
                        if (Globel.isTrueAnswerByQuestion(question,question_answer)){
                            Globel.questionTrue++;
                            setStudyCountTrue();
                            viewPager.setCurrentItem(index+1);
                        }else {
                            Globel.questionFalse++;
                            setStudyCountFalse();
                            for (int i = 0; i <4 ; i++) {
                                if (question_answer[i].isChecked()){
                                    question_answer[i].setButtonDrawable(R.mipmap.study_result_false);
                                };
                            }
                            question_answer[Globel.studyQuestionList.get(index).getQuestion_answer1()].setButtonDrawable(R.mipmap.study_result_true);
                            question_answer[Globel.studyQuestionList.get(index).getQuestion_answer2()].setButtonDrawable(R.mipmap.study_result_true);
                            if (Globel.studyQuestionList.get(index).getQuestion_answer3()!=-1){
                                question_answer[Globel.studyQuestionList.get(index).getQuestion_answer3()].setButtonDrawable(R.mipmap.study_result_true);
                            }
                            if (Globel.studyQuestionList.get(index).getQuestion_answer4()!=-1){
                                question_answer[Globel.studyQuestionList.get(index).getQuestion_answer4()].setButtonDrawable(R.mipmap.study_result_true);
                            }
                            MainActivity.insertQuestionToMylib(null,question, Globel.QUESTION_TYPE_ERROR);
                        }
                        Globel.setCheckEnableFalse(question_answer);
                        studyMulitple.setVisibility(View.GONE);
                        studyAnswer.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        viewList.add(view);
    }

    @Override
    protected void onDestroy() {
        //还原数据
        Globel.questionIndex=0;
        Globel.questionTrue=0;
        Globel.questionFalse=0;
        super.onDestroy();
    }

    public boolean isSingleAnswer(int index){
        return Globel.studyQuestionList.get(index).getQuestion_answer2()==-1?true:false;
    }

    public void setStudyCountTrue(){
        studyCountTrue.setText(Globel.questionTrue+"");
    }

    public void setStudyCountFalse(){
        studyCountFalse.setText(Globel.questionFalse+"");
    }

}
