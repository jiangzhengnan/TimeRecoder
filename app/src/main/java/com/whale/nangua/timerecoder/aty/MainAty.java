package com.whale.nangua.timerecoder.aty;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.whale.nangua.timerecoder.R;

/**
 * Created by nangua on 2016/6/27.
 */
public class MainAty extends Activity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);

    }
}
