package com.svse.dream.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.svse.dream.apdater.OSInfoAdapter;
import com.svse.dream.bean.OSInfo;
import com.svse.dream.dao.DataDaoImpl;
import com.svse.dream.test.MainActivity;
import com.svse.dream.test.R;
import com.svse.dream.utils.GlobelVar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private DataDaoImpl dataDao;
    public MainFragment() {
        dataDao=new DataDaoImpl();
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

    public void initView(View view){
        mainLibTip = (TextView) view.findViewById(R.id.main_lib_tip);
        mainMylib = (ListView) view.findViewById(R.id.main_mylib);
        mainSlide = (ImageView) view.findViewById(R.id.main_slide);
        mainSlide.setOnClickListener(new MyOnClickListener());
    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.main_slide:
                    MainActivity.slidingMenu.toggle();
                    break;
            }
        }
    }

    /**
     * 获取我的题库列表
     */
    private List<OSInfo> osInfoList;
    public void initData(){
        Set<String> myOSlist = GlobelVar.getSharedPreferences(getActivity()).getStringSet("myOSlist", new TreeSet<String>());
        //如果获取到数据显示题库列表，否则显示提示信息
        if (myOSlist!=null&&myOSlist.size()>0){
            mainLibTip.setVisibility(View.GONE);
            mainMylib.setVisibility(View.VISIBLE);
            Object[] myOSlists = myOSlist.toArray();
            osInfoList=new ArrayList<>();
            for (int i = 0; i < myOSlists.length; i++) {
                osInfoList.add(new OSInfo(myOSlists[i].toString(),dataDao.getTableNum(myOSlists[i].toString())));
            }
            mainMylib.setAdapter(new OSInfoAdapter(getActivity(),osInfoList));
        }else{
            mainLibTip.setVisibility(View.VISIBLE);
            mainMylib.setVisibility(View.GONE);
        }
    }

}
