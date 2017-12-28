package com.svse.dream.apdater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.svse.dream.bean.Version;
import com.svse.dream.test.R;

import java.util.List;

/**
 * Created by Dream on 2016/12/2.
 * 创建自定义适配器_更新日志
 */
public class VersionApdater extends BaseAdapter {
    //上下文
    private Context context;
    //所有更新日志集合
    private List<Version> list;
    //得到上下文和所有更新日志集合
    public VersionApdater(List<Version> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
        //得到每个更新日志实体
        Version updateLog=list.get(position);
        View view=null;
        if (convertView==null){
            view= View.inflate(context, R.layout.activity_version_item,null);
        }else {
            view=convertView;
        }
        //填充数据
        TextView version= (TextView) view.findViewById(R.id.version);
        TextView date= (TextView) view.findViewById(R.id.date);
        TextView content= (TextView) view.findViewById(R.id.content);
        TextView bug= (TextView) view.findViewById(R.id.bug);
        if (position==0){
            version.setText("当前版本："+updateLog.getVersion());
        }else {
            version.setText("历史版本："+updateLog.getVersion());
        }
        date.setText("更新时间："+updateLog.getDate());
        content.setText("更新内容:"+"\n"+updateLog.getContent());
        bug.setText("已知BUG:"+"\n"+updateLog.getBug());
        return view;
    }
}
