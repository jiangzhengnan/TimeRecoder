package com.whale.nangua.timerecoder.frag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whale.nangua.timerecoder.R;
/**
 * Created by nangua on 2016/5/26.
 */
public class Frag1 extends Fragment {
    SwipeRefreshLayout refreshlayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag1_layout,container,false);
        //设置颜色
        refreshlayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshlayout);
    //    refreshlayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);
        refreshlayout.setColorSchemeColors(Color.WHITE);
        refreshlayout.setProgressBackgroundColorSchemeColor(Color.RED);

        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO网络操作
                new Thread() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //子线程不能刷新UI
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshlayout.setRefreshing(false);
                            }
                        });
                    }
                }.start();
            }
        });
        return v ;
    }
}
