package com.whale.nangua.timerecoder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whale.nangua.timerecoder.R;

import java.util.ArrayList;

/**
 * Created by nangua on 2016/7/10.
 */
public class Frag2Adapter extends BaseAdapter {
    Context context;
    ArrayList<String> date;

    public Frag2Adapter(Context context,ArrayList<String> date) {
        this.context = context;
        this.date = date;
    }

    @Override
    public int getCount() {
        return date.size();
    }

    @Override
    public Object getItem(int position) {
        return date.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.frag2adapter_layout,null);
        TextView tv = (TextView) v.findViewById(R.id.frag2_tv);
        tv.setText(date.get(position).toString());
        return v;
    }
}
