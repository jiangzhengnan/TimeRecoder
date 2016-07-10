package com.whale.nangua.timerecoder.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.whale.nangua.timerecoder.bean.BookInfo;

import java.util.List;

/**
 * Created by nangua on 2016/7/10.
 */
public class ProgressfragLvAdapter extends BaseAdapter {
    List<BookInfo> bookdate;
    Context context;
    public ProgressfragLvAdapter(Context context,List<BookInfo> bookdate) {
        this.context = context;
        this.bookdate = bookdate;
    }

    @Override
    public int getCount() {
        return bookdate.size();
    }

    @Override
    public Object getItem(int position) {
        return bookdate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}
