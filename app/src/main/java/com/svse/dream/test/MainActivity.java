package com.svse.dream.test;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.FrameLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.svse.dream.fragment.MainFragment;
import com.svse.dream.utils.DBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(Color.parseColor("#F6927B"));
        initSlideMenu();
        initView();
    }

    public static SlidingMenu slidingMenu;
    public void initSlideMenu(){
        Display display = getWindowManager().getDefaultDisplay();
        slidingMenu=new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset((int)(display.getWidth()*0.45));
        slidingMenu.setFadeDegree(0.3f);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slidemenu_main);
    }

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainFragment mainFragment;
    private Fragment[] fragments;
    public void initView(){
        FrameLayout framelayout = (FrameLayout) findViewById(R.id.framelayout);
        mainFragment=new MainFragment();
        fragments=new Fragment[]{mainFragment};
        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            fragmentTransaction.add(R.id.framelayout,fragments[i]);
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        DBHelper.closeDB();
        super.onDestroy();
    }
}
