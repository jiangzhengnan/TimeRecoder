package com.whale.nangua.timerecoder.aty;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.whale.nangua.timerecoder.R;
import com.whale.nangua.timerecoder.bean.BookInfo;
import com.whale.nangua.timerecoder.imgload.NGImageloadHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nangua on 2016/7/11.
 */
public class BookShowAty extends Activity {
    @Bind(R.id.bookshow_title)
    TextView bookshow_title; //书名
    @Bind(R.id.bookshow_autor)
    TextView bookshow_author; //作者
    @Bind(R.id.bookshow_price)
    TextView bookshow_price; //价格
    @Bind(R.id.bookshow_pages)
    TextView bookshow_pages; //页数
    @Bind(R.id.bookshow_img)
    ImageView bookshow_img; //图片
    @Bind(R.id.bookshow_summary)
    TextView bookshow_summary; //简介

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookshow_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        BookInfo bookInfo = (BookInfo) getIntent().getSerializableExtra("bookinfo");
        bookshow_title.setText(bookInfo.getTitle());
        bookshow_author.setText(bookInfo.getAuthor());
      //  bookshow_price.setText(bookInfo.getPrice()); //价格
        bookshow_pages.setText(bookInfo.getMax() + "页");
        bookshow_summary.setText(bookInfo.getSummary());

        NGImageloadHelper.displayImage(bookshow_img,bookInfo.getImage());
    }
}
