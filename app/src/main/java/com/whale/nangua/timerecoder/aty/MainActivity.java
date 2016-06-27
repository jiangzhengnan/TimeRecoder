package com.whale.nangua.timerecoder.aty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whale.nangua.timerecoder.R;
import com.whale.nangua.timerecoder.bean.BookInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.textbtn);

        tv = (TextView) findViewById(R.id.resulttv);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(i, 1);
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
                    conn.setConnectTimeout(5 * 1000);
                    //设置请求方法
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);


                    int code = conn.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {
                        InputStream in = conn.getInputStream();
                        //调用数据流处理方法
                        byte[] data = new  StreamTool().readInputStream(in);
                        final String json = new String(data);

                        //Gson解析
                        pareseJSON(json);



                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(json);
                                Toast.makeText(MainActivity.this,json,Toast.LENGTH_LONG).show();
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

    //解析json数据
    private void pareseJSON (String text) {
        try {
            JSONObject jsonObject = new JSONObject(text);
                Log.d("whaleaa","SB:" );
                String subtitle = jsonObject.getString("subtitle");
                String author = jsonObject.getString("author");
                Log.d("whaleaa","subtitle:" +subtitle);
                Log.d("whaleaa","author:" + author);

        } catch (Exception e) {
            e.printStackTrace();
        }

     /*   Gson gson = new Gson();
        List<BookInfo> bookInfoList = gson.fromJson(text,new TypeToken<List<BookInfo>>(){}.getType());
        for (BookInfo bookInfo:bookInfoList) {
            Log.d("whaleaa","subtitle:" + bookInfo.getSubtitle());
            Log.d("whaleaa","author:" + bookInfo.getAuthor());
        }*/
    }


    public static <T> T getBookName(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }

    public class StreamTool
    {
        /**
         * 从输入流中获取数据
         * @param inStream 输入流
         * @return
         * @throws Exception
         */
        public   byte[] readInputStream(InputStream inStream) throws Exception{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while( (len=inStream.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            inStream.close();
            return outStream.toByteArray();
        }
    }
}
