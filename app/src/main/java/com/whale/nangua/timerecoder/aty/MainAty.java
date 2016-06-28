package com.whale.nangua.timerecoder.aty;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.whale.nangua.timerecoder.R;

/**
 * Created by nangua on 2016/6/27.
 */
public class MainAty extends Activity {

    Toolbar toolbar;
    ActionBar actionBar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;
   /* Frag1 frag1;
    Frag2 frag2;*/


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
           /* //实例化
            frag1 = new Frag1();
            frag2 = new Frag2();
            frag3 = new Frag3();
            frag4 = new Frag4();
            //获得Fm
            //add
            //remove
            //replace
            //hide
            //show
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout, frag1)
                    .add(R.id.framelayout, frag2)
                    .add(R.id.framelayout, frag3)
                    .add(R.id.framelayout, frag4)
                    .show(frag1)
                    .hide(frag2).hide(frag3).hide(frag4).commit();*/
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
                     //   getSupportFragmentManager().beginTransaction().show(frag1).hide(frag2).hide(frag3).hide(frag4).commit();
                      //  Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_b:
                   //     getSupportFragmentManager().beginTransaction().show(frag2).hide(frag1).hide(frag3).hide(frag4).commit();
                      //  Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
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


}
