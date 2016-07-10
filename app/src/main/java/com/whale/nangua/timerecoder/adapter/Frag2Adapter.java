package com.whale.nangua.timerecoder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whale.nangua.timerecoder.R;
import com.whale.nangua.timerecoder.bean.BookInfo;
import com.whale.nangua.timerecoder.imgload.NGImageloadHelper;

import java.util.ArrayList;

/**
 * Created by nangua on 2016/7/10.
 */
public class Frag2Adapter extends BaseAdapter {
    Context context;
    ArrayList<BookInfo> date;

    public Frag2Adapter(Context context,ArrayList<BookInfo> date) {
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
        BookInfo bookInfo = date.get(position);
        View v = LayoutInflater.from(context).inflate(R.layout.frag2adapter_layout, null);
        TextView title = (TextView) v.findViewById(R.id.frag2_title);
        TextView autor = (TextView) v.findViewById(R.id.frag2_autor);
        TextView jindu = (TextView) v.findViewById(R.id.frag2_jindu);
        ImageView frag2_imgurl = (ImageView) v.findViewById(R.id.frag2_imgurl);
        ProgressBar frag2_jprogressBar = (ProgressBar) v.findViewById(R.id.frag2_jprogressBar);

        jindu.setText("0/" + bookInfo.getMax() + "页");
        title.setText(bookInfo.getTitle());
        autor.setText(bookInfo.getAuthor());
        frag2_jprogressBar.setMax(Integer.parseInt(bookInfo.getMax()));
        frag2_jprogressBar.setProgress(Integer.parseInt(bookInfo.getMax()));
        NGImageloadHelper.displayImage(frag2_imgurl,bookInfo.getImage());

        return v;
    }
}
