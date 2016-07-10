package com.whale.nangua.timerecoder.aty;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.whale.nangua.timerecoder.R;
import com.whale.nangua.timerecoder.imgload.NGImageloadHelper;

/**
 * Created by nangua on 2016/7/10.
 */
public class TestAty extends Activity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttttttttttttt);
        imageView = (ImageView) findViewById(R.id.tttttt);
        NGImageloadHelper.displayImage(imageView,"https://img3.doubanio.com//mpic//s28351121.jpg");
    }
}
