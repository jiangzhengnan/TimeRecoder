package com.whale.nangua.timerecoder.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.whale.nangua.timerecoder.R;
import com.whale.nangua.timerecoder.adapter.Frag2Adapter;
import com.whale.nangua.timerecoder.aty.BookShowAty;
import com.whale.nangua.timerecoder.bean.BookInfo;
import com.whale.nangua.timerecoder.db.DBUtils;
import com.whale.nangua.timerecoder.imgload.NGDownloadImage;
import com.whale.nangua.timerecoder.imgload.NGImageloadHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by nangua on 2016/5/26.
 */
public class ProgressFrag extends Fragment implements SetUpFrag.refreshbookes {

    public ProgressFrag progressFrag = this;
    ListView progressfrag_lv;
    private static Frag2Adapter frag2Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag2_layout, container, false);
        progressFrag = this;
        initView(v);


        return v;
    }

    private static ArrayList<BookInfo> bookInfos;

    private void initView(View v) {
        bookInfos = DBUtils.getInstance(getContext()).queryBooks();

        progressfrag_lv = (ListView) v.findViewById(R.id.progressfrag_lv);
        frag2Adapter = new Frag2Adapter(getContext(), bookInfos);
        progressfrag_lv.setAdapter(frag2Adapter);
        progressfrag_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), BookShowAty.class);
                i.putExtra("bookinfo", bookInfos.get(position));
                startActivity(i);
            }
        });
    }

    @Override
    public void refreshbookes() {
        DBUtils db =  DBUtils.getInstance(getContext()) ;
        bookInfos = db.queryBooks();

        frag2Adapter.notifydate(bookInfos);
        progressfrag_lv.setAdapter(frag2Adapter);
        progressfrag_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), BookShowAty.class);
                i.putExtra("bookinfo", bookInfos.get(position));
                startActivity(i);
            }
        });

        fragRefreshCallback.refreshfrag(ProgressFrag.this);
    }

    public void setRefreshCallback(FragRefresh fragRefreshCallback) {
        this.fragRefreshCallback = fragRefreshCallback;
    }

    public FragRefresh fragRefreshCallback;

    public interface FragRefresh{
        public void refreshfrag(ProgressFrag fragment );
    }

}
