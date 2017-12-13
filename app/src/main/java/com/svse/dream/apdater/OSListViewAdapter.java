package com.svse.dream.apdater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import com.svse.dream.test.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hello on 2017/6/11.
 */
public class OSListViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> osStr;
    private Set<String> myOSlist;
    private Map<Integer,Boolean> stateMap;

    public OSListViewAdapter(Context context, List<String> osStr) {
        stateMap=new HashMap<>();
        myOSlist=new HashSet<>();
        this.context = context;
        this.osStr = osStr;
    }

    @Override
    public int getCount() {
        return osStr.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder=new ViewHolder();
            view=View.inflate(context, R.layout.listview_oslist_item,null);
            viewHolder.osName= (CheckBox) view.findViewById(R.id.cb_select);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.osName.setText(osStr.get(i));
        viewHolder.osName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    stateMap.put(i,true);
                    if (!myOSlist.contains(osStr.get(i))){
                        myOSlist.add(osStr.get(i));
                    }
                }else {
                    stateMap.remove(i);
                    if (myOSlist.contains(osStr.get(i))){
                        myOSlist.remove(osStr.get(i));
                    }
                }
            }
        });

        if (stateMap!=null&&stateMap.containsKey(i)){
            viewHolder.osName.setChecked(true);
        }else {
            viewHolder.osName.setChecked(false);
        }

        return view;
    }

    class ViewHolder{
        CheckBox osName;
    }

    public Set<String> getMyOSlist() {
        return myOSlist;
    }
}
