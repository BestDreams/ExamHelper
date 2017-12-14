package com.svse.dream.apdater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.svse.dream.bean.OSInfo;
import com.svse.dream.bean.Question;
import com.svse.dream.dao.DataDaoImpl;
import com.svse.dream.test.R;

import java.util.List;

/**
 * Created by Hello on 2017/6/11.
 */
public class OSInfoAdapter extends BaseAdapter {
    private Context context;
    private List<OSInfo> osInfoList;
    private DataDaoImpl dataDao;

    public OSInfoAdapter(Context context, List<OSInfo> osInfoList) {
        dataDao=new DataDaoImpl();
        this.context = context;
        this.osInfoList = osInfoList;
    }

    @Override
    public int getCount() {
        return osInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return osInfoList.get(i);
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
            view=View.inflate(context, R.layout.listview_osinfo_item,null);
            viewHolder.index= (TextView) view.findViewById(R.id.osinfo_index);
            viewHolder.osName= (TextView) view.findViewById(R.id.osinfo_osName);
            viewHolder.osCount= (TextView) view.findViewById(R.id.osinfo_count);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }

        final OSInfo osInfo=osInfoList.get(i);
        viewHolder.index.setText((i+1)+"");
        viewHolder.osName.setText(osInfo.getOsName());
        viewHolder.osCount.setText(osInfo.getCount()+"");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*List<Question> studyQuestionsByOsName = dataDao.getStudyQuestionsByOsName(osInfo.getOsName());
                context.startActivity(new Intent(context, ExamActivity.class));
                Activity activity= (Activity) context;
                activity.finish();*/
            }
        });

        return view;
    }

    class ViewHolder{
        TextView index;
        TextView osName;
        TextView osCount;
    }

}
