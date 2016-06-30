package com.whale.nangua.timerecoder.aty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.whale.nangua.timerecoder.R;
import com.whale.nangua.timerecoder.frag.Frag1;
import com.whale.nangua.timerecoder.frag.Frag2;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nangua on 2016/6/27.
 */
public class MainAty extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;
   Frag1 frag1;
    Frag2 frag2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.navView);

     //  setSupportActionBar(toolbar);
     //   actionBar = getSupportActionBar();
     //   actionBar.setSubtitle("应用");

        //创建抽屉开关（把手）
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        );

        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        // 设置抽屉中那些菜单项的监听器
        navigationView.setNavigationItemSelectedListener(new NaviListner());

        if (savedInstanceState == null) {
          //实例化
            frag1 = new Frag1();
            frag2 = new Frag2();
            //获得Fm
            //add
            //remove
            //replace
            //hide
            //show
             getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout, frag1)
                    .add(R.id.framelayout, frag2)
                    .show(frag1)
                    .hide(frag2).commit();
        }
    }

    /**
     * 抽屉菜单
     */
    class NaviListner implements NavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //分好的group中的选项
            if (item.getGroupId() == R.id.group_items) {
                switch (item.getItemId()) {
                    case R.id.nav_a:
                        getSupportFragmentManager().beginTransaction().show(frag1).hide(frag2).commit();
                        Toast.makeText(MainAty.this,"1",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_b:
                        getSupportFragmentManager().beginTransaction().show(frag2).hide(frag1).commit();
                        Toast.makeText(MainAty.this, "2", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_c:
                        Intent i = new Intent(MainAty.this,QrAty.class);
                         startActivityForResult(i, 1);
                        break;
                }
                //否则就是menu下面的m其他点击按钮
            } else {
                switch (item.getItemId()) {
                    case R.id.action_about:

                        break;
                    case R.id.action_setting:

                        break;
                }
            }
            drawerLayout.closeDrawers();

            return true;
        }
    }


    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null) {
            String result = data.getStringExtra("result");

            final String path = "https://api.douban.com/v2/book/isbn/:" + result;

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
                            byte[] data = new StreamTool().readInputStream(in);
                            final String json = new String(data);

                            //Gson解析
                            pareseJSON(json);


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //解析json数据
    private void pareseJSON (String text) {
        try {
            JSONObject jsonObject = new JSONObject(text);
            Log.d("whaleaa", "SB:");
            String subtitle = jsonObject.getString("subtitle");
            String author = jsonObject.getString("author");
            Log.d("whaleaa", "subtitle:" + subtitle);
            Log.d("whaleaa", "author:" + author);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
