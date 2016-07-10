package com.whale.nangua.timerecoder.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.whale.nangua.timerecoder.R;
import com.whale.nangua.timerecoder.adapter.MainRvAdapter;
import com.whale.nangua.timerecoder.bean.Communitybean;
import com.whale.nangua.timerecoder.handler.ImageCarouseHandler;
import com.whale.nangua.timerecoder.imgload.NGImageloadHelper;

import java.lang.ref.WeakReference;
/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class HomeFrag extends Fragment {
    private RecyclerView rv;
    private Communitybean communitybean;
    private MainRvAdapter mainRvAdapter;
    //弱引用
    protected WeakReference<HomeFrag> weakReference ;
    public  static ImageCarouseHandler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_home_frag, container, false);
        initView(v);


        return v;
    }

    public    void ChanggeViewPagerCurrentItem(int currentItem) {
        mainRvAdapter.setViewPagerCurrentItem(currentItem);
    }

    private void initView(View v) {
        rv = (RecyclerView) v.findViewById(R.id.rv_main);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true); //使RecyclerView保持固定的大小,这样会提高RecyclerView的性能。
        weakReference = new WeakReference<HomeFrag>(HomeFrag.this);
        handler =  new ImageCarouseHandler(weakReference);
        mainRvAdapter = new MainRvAdapter(getContext(),communitybean ,handler);
        rv.setAdapter(mainRvAdapter );
    }





}
