package com.whale.nangua.timerecoder.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.whale.nangua.timerecoder.R;
import com.whale.nangua.timerecoder.db.DBUtils;
import com.whale.nangua.timerecoder.frag.ProgressFrag;
import com.whale.nangua.timerecoder.frag.HomeFrag;
import com.whale.nangua.timerecoder.frag.SetUpFrag;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.ButterKnife;

/**
 * Created by nangua on 2016/6/27.
 */
public class MainAty extends AppCompatActivity {
    Toolbar toolbar;
    ActionBar actionBar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;


    ProgressFrag progressFrag;
    HomeFrag homeFrag;
    SetUpFrag setUpFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);

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

            //实例化
            progressFrag = new ProgressFrag();

            homeFrag = new HomeFrag();
        setUpFrag = new SetUpFrag();

            //获得Fm
            //add
            //remove
            //replace
            //hide
            //show
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout, homeFrag)
                    .add(R.id.framelayout, progressFrag)
                    .add(R.id.framelayout,setUpFrag)
                    .show(homeFrag)
                    .hide(progressFrag)
                    .hide(setUpFrag)
                    .commit();


    }

    /**
     * 抽屉菜单
     */
    class NaviListner implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //分好的group中的选项
            if (item.getGroupId() == R.id.group_items) {
                switch (item.getItemId()) {
                    case R.id.nav_a:
                        getSupportFragmentManager().beginTransaction().show(homeFrag).hide(progressFrag).hide(setUpFrag).commit();
                        break;
                    case R.id.nav_b:
                        getSupportFragmentManager().beginTransaction().show(progressFrag).hide(homeFrag).hide(setUpFrag).commit();
                        break;
                    case R.id.nav_c:
                        Intent i = new Intent(MainAty.this, CaptureActivity.class);
                        startActivityForResult(i, 1);
                        break;
                }
                //否则就是menu下面的m其他点击按钮
            } else {
                switch (item.getItemId()) {
                    case R.id.action_about:

                        break;
                    case R.id.action_setting:
                        getSupportFragmentManager().beginTransaction().show(setUpFrag).hide(homeFrag).hide(progressFrag).commit();
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
        if (data != null) {
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
    private void pareseJSON(String text) {
        Log.d("xiaojingyu" , "开始解析" );
        try {
            JSONObject jsonObject = new JSONObject(text);
            String title = jsonObject.getString("title");
            Log.d("xiaojingyu" , "title:" + title );
             String summary = jsonObject.getString("summary");
            Log.d("xiaojingyu" , "summary:" + summary );
            String author = jsonObject.getString("author");
            Log.d("xiaojingyu" , "author:" + author );
            String image = jsonObject.getString("image");
            Log.d("xiaojingyu" , "image:" + image );

          String max = jsonObject.getString("pages");
            Log.d("xiaojingyu" , "max:" + max );

             //String catalog = jsonObject.getString("catalog");

            Log.d("xiaojingyu" , "解析完辣！");
            DBUtils dbUtils =   DBUtils.getInstance(MainAty.this);
            Log.d("xiaojingyu" , title + summary + author + image + max + "catalog");

            dbUtils.insertBooks(title, summary, author, image, max, "catalog");
                Snackbar.make(drawerLayout, "保存" + title + "成功！", Snackbar.LENGTH_SHORT).show();
                Log.d("xiaojingyu", "保存成功辣");
            //刷新数据
            new ProgressFrag().progressFrag.clearbookes();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class StreamTool {
        /**
         * 从输入流中获取数据
         * @param inStream 输入流
         * @return
         * @throws Exception
         */
        public byte[] readInputStream(InputStream inStream) throws Exception {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            inStream.close();
            return outStream.toByteArray();
        }
    }
}
