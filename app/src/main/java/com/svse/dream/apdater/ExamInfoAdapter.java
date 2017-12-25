package com.svse.dream.apdater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.svse.dream.bean.ExamInfo;
import com.svse.dream.test.R;

import java.util.List;

/**
 * Created by ZhangPing on 2017/12/25.
 */

public class ExamInfoAdapter extends BaseAdapter {

    private Context context;
    private List<ExamInfo> infoList;

    public ExamInfoAdapter(Context context, List<ExamInfo> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler=null;
        ExamInfo examInfo=infoList.get(position);
        if (convertView==null){
            convertView=View.inflate(context, R.layout.exam_listview_item,null);
            viewHodler=new ViewHodler();
            viewHodler.icon= (ImageView) convertView.findViewById(R.id.exam_listview_icon);
            viewHodler.title= (TextView) convertView.findViewById(R.id.exam_listview_title);
            viewHodler.text= (TextView) convertView.findViewById(R.id.exam_listview_text);
            convertView.setTag(viewHodler);
        }else{
            viewHodler= (ViewHodler) convertView.getTag();
        }

        viewHodler.icon.setImageResource(examInfo.getIconId());
        viewHodler.title.setText(examInfo.getTitle());
        viewHodler.text.setText(examInfo.getText());

        return convertView;
    }

    class ViewHodler{
        ImageView icon;
        TextView title;
        TextView text;
    }

}


