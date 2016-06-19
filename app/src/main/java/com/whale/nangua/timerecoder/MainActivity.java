package com.whale.nangua.timerecoder;

import android.content.Intent;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whale.nangua.timerecoder.qr.activity.CaptureActivity;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.textbtn);
        tv = (TextView) findViewById(R.id.resulttv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(i,1);
            }
        });
    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getStringExtra("result");

        tv.setText(result);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
