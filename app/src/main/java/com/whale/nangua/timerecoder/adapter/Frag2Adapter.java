package com.whale.nangua.timerecoder.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.whale.nangua.timerecoder.R;
import com.whale.nangua.timerecoder.bean.BookInfo;
import com.whale.nangua.timerecoder.db.DBUtils;
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
        final BookInfo bookInfo = date.get(position);
        View v = LayoutInflater.from(context).inflate(R.layout.frag2adapter_layout, null);
        TextView title = (TextView) v.findViewById(R.id.frag2_title);
        TextView autor = (TextView) v.findViewById(R.id.frag2_autor);
        TextView jindu = (TextView) v.findViewById(R.id.frag2_jindu);
        ImageView frag2_imgurl = (ImageView) v.findViewById(R.id.frag2_imgurl);
        ProgressBar frag2_jprogressBar = (ProgressBar) v.findViewById(R.id.frag2_jprogressBar);
        ImageView frag2_setupbtn = (ImageView) v.findViewById(R.id.frag2_setupbtn);

        jindu.setText("0/" + bookInfo.getMax() + "页");
        title.setText(bookInfo.getTitle());
        autor.setText(bookInfo.getAuthor());
        frag2_jprogressBar.setMax(Integer.parseInt(bookInfo.getMax()));

        bookInfo.setNowpages(DBUtils.getInstance(context).queryPages(bookInfo.getTitle()));
        if ((bookInfo.getNowpages() != null)&&!(bookInfo.getNowpages().equals(""))) {
            String p = bookInfo.getNowpages();
            Log.d("nng",p);
            frag2_jprogressBar.setProgress(Integer.parseInt(p));//得到当前页数
        }

        NGImageloadHelper.displayImage(frag2_imgurl, bookInfo.getImage());

        //updatePage
        frag2_setupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动画
                ObjectAnimator animator = ObjectAnimator.ofFloat(v,"rotation",0,359);
                animator.setDuration(300);
                animator.start();
                //选择
                View view = LayoutInflater.from(context).inflate(R.layout.frag2_dialog,null);

                final EditText editText = (EditText)view.findViewById(R.id.frag2_dialog_et);

                final AlertDialog mydialog= new AlertDialog.Builder(context).setView(view).create();
                mydialog.show();
                mydialog.getWindow().setContentView(R.layout.frag2_dialog);
                mydialog.getWindow()
                        .findViewById(R.id.frag2_dialog_btn1)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (DBUtils.getInstance(context).updatePage(bookInfo.getTitle(),editText.getText().toString())!=-1) {
                                    Snackbar.make(v,"更新成功",Snackbar.LENGTH_SHORT).show();
                                } else {
                                    Snackbar.make(v,"更新失败",Snackbar.LENGTH_SHORT).show();
                                }

                            }
                        });
                mydialog.getWindow()
                        .findViewById(R.id.frag2_dialog_btn2)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               mydialog.dismiss();
                            }
                        });
            }
        });

        return v;
    }
}
