package com.svse.dream.apdater;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svse.dream.bean.OSInfo;
import com.svse.dream.test.R;
import com.svse.dream.utils.Globel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hello on 2017/6/11.
 */
public class PerpareAttrsAdapter extends BaseAdapter {
    private Context context;
    private List<OSInfo> osInfoList;

    public PerpareAttrsAdapter(Context context, List<OSInfo> osInfoList) {
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
            view=View.inflate(context, R.layout.perpare_listview_attrs_item,null);
            viewHolder.index= (TextView) view.findViewById(R.id.osinfo_index);
            viewHolder.osName= (TextView) view.findViewById(R.id.osinfo_osName);
            viewHolder.osCount= (TextView) view.findViewById(R.id.osinfo_count);
            viewHolder.allot= (RelativeLayout) view.findViewById(R.id.perpare_allot);
            viewHolder.allotCount= (TextView) view.findViewById(R.id.perpare_allot_count);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }

        final OSInfo osInfo=osInfoList.get(i);
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.index.setText((i+1)+"");
        viewHolder.osName.setText(osInfo.getOsName());
        viewHolder.osCount.setText(osInfo.getCount()+"");
        viewHolder.allot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView=View.inflate(context,R.layout.perpare_listview_attrs_item_allot_dialog,null);
                TextView perpareAllotDialogOsname = (TextView) dialogView.findViewById(R.id.perpare_allot_dialog_osname);
                final EditText perpareAllotDialogCount = (EditText) dialogView.findViewById(R.id.perpare_allot_dialog_count);
                perpareAllotDialogOsname.setText(osInfo.getOsName());
                new AlertDialog.Builder(context)
                        .setTitle("题量分配")
                        .setView(dialogView)
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkAttrs(perpareAllotDialogCount,osInfo,finalViewHolder);
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                Globel.showInputWindow(context);
            }
        });
        return view;
    }

    public void checkAttrs(EditText perpareAllotDialogCount,OSInfo osInfo,ViewHolder viewHolder){
        if (perpareAllotDialogCount.getText().toString().equals("")){
            Toast.makeText(context,"不要为空，谢谢",Toast.LENGTH_SHORT).show();
        }else {
            int allotCount = Integer.parseInt(perpareAllotDialogCount.getText().toString());
            if (allotCount<=osInfo.getCount()){
                if (allotCount>=0&&allotCount<=Globel.exam_count_max){
                    viewHolder.allotCount.setText(allotCount+"");
                    Globel.examAttrsMap.put(osInfo.getOsName(),allotCount);
                }else{
                    Toast.makeText(context,"最大分配数量："+Globel.exam_count_max,Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(context,"分配数量已超过当前科目总题量",Toast.LENGTH_SHORT).show();
            }
        }
    }

    class ViewHolder{
        TextView index;
        TextView osName;
        TextView osCount;
        RelativeLayout allot;
        TextView allotCount;
    }

}
