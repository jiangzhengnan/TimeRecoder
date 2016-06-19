package com.whale.nangua.timerecoder.aty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.whale.nangua.timerecoder.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QrAty extends AppCompatActivity {
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
                Intent i = new Intent(QrAty.this, CaptureActivity.class);
                startActivityForResult(i,1);
            }
        });
    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getStringExtra("result");
        final String path = "https://api.douban.com/v2/book/isbn/:" + result;
        tv.setText(path);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);

                    //获得HTTP连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setConnectTimeout(5*1000);
                    //设置请求方法
                    conn.setRequestMethod("GET");

                    int code = conn.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {
                        InputStream in = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        char[] buf = new char[4096];
                        StringBuffer buffer = new StringBuffer();
                        int size;
                        while (-1!=(size = reader.read(buf))) {
                            buffer.append(buf,0,size);
                        }
                        final String text = buffer.toString();
                        Log.d("whalea", text);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(text);
                                Toast.makeText(QrAty.this,text,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
