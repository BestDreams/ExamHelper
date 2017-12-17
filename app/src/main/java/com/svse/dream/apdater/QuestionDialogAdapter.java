package com.svse.dream.apdater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.svse.dream.test.R;

/**
 * Created by Dream on 2016/12/9.
 */
public class QuestionDialogAdapter extends BaseAdapter {

    private Context context;
    private int[] questionNum;

    public QuestionDialogAdapter(Context context, int[] questionNum) {
        this.context = context;
        this.questionNum=questionNum;
    }

    @Override
    public int getCount() {
        return questionNum.length;
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
        View view;
        if (convertView==null){
            view= View.inflate(context, R.layout.study_dialog_item,null);
        }else {
            view=convertView;
        }
        TextView question_dialog_item_num = (TextView) view.findViewById(R.id.question_dialog_item_num);
        question_dialog_item_num.setText((questionNum[position]+1)+"");
        return view;
    }
}
