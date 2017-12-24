package com.svse.dream.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svse.dream.apdater.QuestionDialogAdapter;
import com.svse.dream.apdater.ViewPagerAdapter;
import com.svse.dream.bean.QuestionMyLib;
import com.svse.dream.utils.Globel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

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
    private List<Integer> removeIndexList;

    public void initView(){
        removeIndexList=new ArrayList<>();
        //初始化控件
        studyTitle = (TextView) findViewById(R.id.study_title);
        studyRemoveCurr = (RelativeLayout) findViewById(R.id.study_remove_curr);
        studyRemoveAll = (RelativeLayout) findViewById(R.id.study_remove_all);
        studyQuestionList = (LinearLayout) findViewById(R.id.study_question_list);
        studyCountTrue = (TextView) findViewById(R.id.study_count_true);
        studyCountFalse = (TextView) findViewById(R.id.study_count_false);
        studyCount = (TextView) findViewById(R.id.study_count);
        studyFavorites = (LinearLayout) findViewById(R.id.study_favorites);
        studyFavorites.setVisibility(View.GONE);
        studyRemoveCurr.setVisibility(View.VISIBLE);
        studyRemoveAll.setVisibility(View.VISIBLE);
        studyQuestionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyQuestionList();
            }
        });

        //初始化ViewPager
        viewList=new ArrayList<>();
        for (int i = 0; i < Globel.FavoriteList.size(); i++) {
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
                Globel.favoriteIndex=position;
                studyCount.setText((position+1)+"/"+ Globel.FavoriteList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        studyRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FavoriteActivity.this)
                        .setTitle("提示")
                        .setMessage("操作将在退出时完成，确定要清空收藏夹吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeIndexList.clear();
                                for (int i = 0; i < Globel.FavoriteList.size(); i++) {
                                    removeIndexList.add(Globel.FavoriteList.get(i).getId());
                                }
                                finish();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });
        studyRemoveCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FavoriteActivity.this)
                        .setTitle("提示")
                        .setMessage("操作将在退出时完成，确定要移除当前题吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeIndexList.add(Globel.FavoriteList.get(Globel.favoriteIndex).getId());
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });

        //初始化数据
        setStudyCountTrue();
        setStudyCountFalse();
        studyTitle.setText("收藏夹");
        studyCount.setText((Globel.favoriteIndex+1)+"/"+ Globel.FavoriteList.size());
    }

    @Override
    protected void onDestroy() {
        //还原数据
        Globel.favoriteIndex=0;
        Globel.favoriteTrue=0;
        Globel.favoriteFalse=0;
        if (removeIndexList!=null&&removeIndexList.size()>0){
            for (int i = 0; i < removeIndexList.size(); i++) {
                MainActivity.removeQuestionFromMylib(removeIndexList.get(i));
            }
        }
        super.onDestroy();
    }

    public void studyQuestionList(){
        question_dialog_view = View.inflate(getApplication(), R.layout.study_dialog, null);
        question_dialog_gridview = (GridView) question_dialog_view.findViewById(R.id.question_dialog_gridview);
        question_num_arr = new int[Globel.FavoriteList.size()];
        for (int i = 0; i < question_num_arr.length; i++) {
            question_num_arr[i] = i;
        }
        question_dialog_gridview.setAdapter(new QuestionDialogAdapter(FavoriteActivity.this, question_num_arr));
        question_dialog_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int question_num = position;
                viewPager.setCurrentItem(question_num);
                dialog.dismiss();
            }
        });
        AlertDialog.Builder question_dialog=new AlertDialog.Builder(FavoriteActivity.this);
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
        final QuestionMyLib question= Globel.FavoriteList.get(index);
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
        studyMulitple.setVisibility(View.GONE);
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

        viewList.add(view);
    }

    public void setStudyCountTrue(){
        studyCountTrue.setText(Globel.favoriteTrue+"");
    }

    public void setStudyCountFalse(){
        studyCountFalse.setText(Globel.favoriteFalse+"");
    }

}
