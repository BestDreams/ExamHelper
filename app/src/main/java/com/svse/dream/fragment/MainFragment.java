package com.svse.dream.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.svse.dream.apdater.OSInfoAdapter;
import com.svse.dream.bean.OSInfo;
import com.svse.dream.dao.DataDaoImpl;
import com.svse.dream.test.ErrorBookActivity;
import com.svse.dream.test.FavoriteActivity;
import com.svse.dream.test.MainActivity;
import com.svse.dream.test.R;
import com.svse.dream.test.StudyActivity;
import com.svse.dream.utils.Globel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        initData();
        return view;
    }
    private TextView mainLibTip;
    private ListView mainMylib;
    private ImageView mainSlide;
    private TextView mainCurrInfo;
    private LinearLayout continueStudy;
    private LinearLayout errorBook;
    private LinearLayout mainFavorite;
    private Map<String,String> historyInfoMap;
    private String coutinue_info_str;
    public void initView(View view){
        mainLibTip = (TextView) view.findViewById(R.id.main_lib_tip);
        mainMylib = (ListView) view.findViewById(R.id.main_mylib);
        mainSlide = (ImageView) view.findViewById(R.id.main_slide);
        mainCurrInfo = (TextView) view.findViewById(R.id.main_curr_info);
        continueStudy = (LinearLayout) view.findViewById(R.id.main_continueStudy);
        errorBook = (LinearLayout) view.findViewById(R.id.main_errorBook);
        mainFavorite = (LinearLayout) view.findViewById(R.id.main_favorite);

        mainSlide.setOnClickListener(new MyOnClickListener());
        continueStudy.setOnClickListener(new MyOnClickListener());
        errorBook.setOnClickListener(new MyOnClickListener());
        mainFavorite.setOnClickListener(new MyOnClickListener());

        coutinue_info_str = Globel.getSharedPreferences(getActivity()).getString(Globel.MODEL_CONTINUE, "");
        if (coutinue_info_str.equals("")){
            mainCurrInfo.setText("无");
        }else{
            historyInfoMap = new Gson().fromJson(coutinue_info_str, HashMap.class);
            mainCurrInfo.setText(historyInfoMap.get(Globel.MODEL_CONTINUE_OSNAME));
        }
    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.main_slide:
                    MainActivity.slidingMenu.toggle();
                    break;
                case R.id.main_continueStudy:
                    //恢复历史数据
                    if (coutinue_info_str.equals("")){
                        Toast.makeText(getActivity(),Globel.EMPTY_TIP,Toast.LENGTH_SHORT).show();
                    }else{
                        String osName=historyInfoMap.get(Globel.MODEL_CONTINUE_OSNAME);
                        Globel.questionIndex=Integer.parseInt(historyInfoMap.get(Globel.MODEL_CONTINUE_INDEX));
                        Globel.questionTrue= Integer.parseInt(historyInfoMap.get(Globel.MODEL_CONTINUE_CORRECT));
                        Globel.questionFalse= Integer.parseInt(historyInfoMap.get(Globel.MODEL_CONTINUE_ERROR));
                        Globel.setQuestionList(Globel.QUESTION_TYPE_ALL, osName ,Globel.LOADING_LIB_CONTINUE, getActivity(), new Globel.GlobelInterface() {
                            @Override
                            public void questionLoadFinsh() {
                                if (Globel.studyQuestionList==null|| Globel.studyQuestionList.size()==0){
                                    Toast.makeText(getActivity(),Globel.EMPTY_TIP,Toast.LENGTH_SHORT).show();
                                }else{
                                    startActivity(new Intent(getActivity(), StudyActivity.class));
                                }
                            }
                        });
                    }
                    break;
                case R.id.main_errorBook:
                    Globel.setQuestionList(Globel.QUESTION_TYPE_ERROR, null,Globel.LOADING_LIB_ERRORBOOK, getActivity(), new Globel.GlobelInterface() {
                        @Override
                        public void questionLoadFinsh() {
                            if (Globel.ErrorBookList==null|| Globel.ErrorBookList.size()==0){
                                Toast.makeText(getActivity(),Globel.EMPTY_TIP,Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(new Intent(getActivity(), ErrorBookActivity.class));
                            }
                        }
                    });
                    break;
                case R.id.main_favorite:
                    Globel.setQuestionList(Globel.QUESTION_TYPE_FAVORITE, null,Globel.LOADING_LIB_FAVORITE, getActivity(), new Globel.GlobelInterface() {
                        @Override
                        public void questionLoadFinsh() {
                            if (Globel.FavoriteList==null|| Globel.FavoriteList.size()==0){
                                Toast.makeText(getActivity(),Globel.EMPTY_TIP,Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(new Intent(getActivity(), FavoriteActivity.class));
                            }
                        }
                    });
                    break;
            }
        }
    }

    /**
     * 获取我的题库列表
     */
    public void initData(){
        //如果获取到数据显示题库列表，否则显示提示信息
        List<OSInfo> osInfoList = Globel.getOsInfoList(getActivity());
        if (osInfoList==null){
            mainLibTip.setVisibility(View.VISIBLE);
            mainMylib.setVisibility(View.GONE);
        }else{
            mainLibTip.setVisibility(View.GONE);
            mainMylib.setVisibility(View.VISIBLE);
            mainMylib.setAdapter(new OSInfoAdapter(getActivity(),osInfoList));
        }
    }

}
