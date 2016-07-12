package com.whale.nangua.timerecoder.aty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;

import com.whale.nangua.timerecoder.R;


/**
 * Created by nangua on 2016/4/13 0013.
 */

public class InitActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_layout);

        new CountDownTimer(1000,10) {

            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                Intent intent = new Intent();
                intent.setClass(InitActivity.this, MainAty.class);
                startActivity(intent);
                //获得sdk版本
                int VERSION=Integer.parseInt(android.os.Build.VERSION.SDK);
                if(VERSION >= 5){
                    InitActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                }
                finish();
            }
        }.start();
    }
}


