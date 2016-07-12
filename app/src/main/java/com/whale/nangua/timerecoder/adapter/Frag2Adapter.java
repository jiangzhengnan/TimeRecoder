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

    public void notifydate(ArrayList<BookInfo> date) {
        this.date = date;
        this.notifyDataSetChanged();
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
        final TextView jindu = (TextView) v.findViewById(R.id.frag2_jindu);
        ImageView frag2_imgurl = (ImageView) v.findViewById(R.id.frag2_imgurl);
        final ProgressBar frag2_jprogressBar = (ProgressBar) v.findViewById(R.id.frag2_jprogressBar);
        ImageView frag2_setupbtn = (ImageView) v.findViewById(R.id.frag2_setupbtn);

        title.setText(bookInfo.getTitle());
        autor.setText(bookInfo.getAuthor());
        frag2_jprogressBar.setMax(Integer.parseInt(bookInfo.getMax()));
        jindu.setText(bookInfo.getNowpages() + "/" + bookInfo.getMax() + "页");
        bookInfo.setNowpages(DBUtils.getInstance(context).queryPages(bookInfo.getTitle()));
        if ((bookInfo.getNowpages() != null)&&!(bookInfo.getNowpages().equals(""))) {
            String p = bookInfo.getNowpages();
            frag2_jprogressBar.setProgress(Integer.parseInt(p));//得到当前页数
        }

        NGImageloadHelper.displayImage(frag2_imgurl, bookInfo.getImage());
        Log.d("xiaojingyu", "数据库：" +bookInfo.getNowpages());
        //updatePage
        frag2_setupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动画
                ObjectAnimator animator = ObjectAnimator.ofFloat(v,"rotation",0,359);
                animator.setDuration(300);
                animator.start();
                //选择
                final View view = LayoutInflater.from(context).inflate(R.layout.frag2_dialog,null);


                final AlertDialog mydialog= new AlertDialog.Builder(context).setView(view).create();
                mydialog.show();
                mydialog.getWindow().setContentView(R.layout.frag2_dialog);
                mydialog.getWindow()
                        .findViewById(R.id.frag2_dialog_btn1)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText editText = (EditText) mydialog.getWindow().findViewById(R.id.frag2_dialog_et);
                                        //(EditText)view.findViewById(R.id.frag2_dialog_et);
                                Log.d("xiaojingyu","输入的数字："+editText.getText().toString());
                               Log.d("xiaojingyu", "输入的id：" + bookInfo.getId().toString());



                                DBUtils.getInstance(context).updatePage(bookInfo.getId(), editText.getText().toString());
                                    Snackbar.make(v, "更新成功", Snackbar.LENGTH_SHORT).show();
                                frag2_jprogressBar.setProgress(Integer.parseInt(editText.getText().toString()));
                                jindu.setText(bookInfo.getNowpages() + "/" + bookInfo.getMax() + "页");
                                notifyDataSetChanged();
                                mydialog.dismiss();
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
