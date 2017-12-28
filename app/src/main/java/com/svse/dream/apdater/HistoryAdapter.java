package com.svse.dream.apdater;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svse.dream.bean.MyGrade;
import com.svse.dream.bean.OSInfo;
import com.svse.dream.test.R;
import com.svse.dream.utils.Globel;

import java.util.List;

/**
 * Created by Hello on 2017/6/11.
 */
public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private List<MyGrade> myGradeList;

    public HistoryAdapter(Context context, List<MyGrade> myGradeList) {
        this.context = context;
        this.myGradeList = myGradeList;
    }

    @Override
    public int getCount() {
        return myGradeList.size();
    }

    @Override
    public Object getItem(int i) {
        return myGradeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder=new ViewHolder();
            view=View.inflate(context, R.layout.perpare_listview_history_item,null);
            viewHolder.historyIndex = (TextView) view.findViewById(R.id.history_index);
            viewHolder.historyOsName = (TextView) view.findViewById(R.id.history_osName);
            viewHolder.historyCount = (TextView) view.findViewById(R.id.history_count);
            viewHolder.historyGrade = (TextView) view.findViewById(R.id.history_grade);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }

        MyGrade myGrade=myGradeList.get(i);
        viewHolder.historyIndex.setText((i+1)+"");
        viewHolder.historyOsName.setText(myGrade.getStartTime());
        viewHolder.historyCount.setText(myGrade.getCorrectProcent());
        viewHolder.historyGrade.setText(myGrade.getGrade()+"");
        return view;
    }


    class ViewHolder{
        TextView historyIndex;
        TextView historyOsName;
        TextView historyCount;
        TextView historyGrade;
    }

}
