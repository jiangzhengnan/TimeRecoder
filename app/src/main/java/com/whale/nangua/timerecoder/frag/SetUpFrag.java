package com.whale.nangua.timerecoder.frag;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.whale.nangua.timerecoder.R;
import com.whale.nangua.timerecoder.adapter.Frag2Adapter;
import com.whale.nangua.timerecoder.bean.BookInfo;
import com.whale.nangua.timerecoder.db.DBUtils;

import java.util.ArrayList;

/**
 * Created by nangua on 2016/7/10.
 */
public class SetUpFrag extends Fragment {

    Button textfrag_clearbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setup_layout, container, false);

        textfrag_clearbtn = (Button) v.findViewById(R.id.textfrag_clearbtn);
        textfrag_clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DBUtils db =  DBUtils.getInstance(getContext()) ;
                db.clearBooks();
                Snackbar.make(textfrag_clearbtn, "清空数据", Snackbar.LENGTH_SHORT).show();

                ArrayList<BookInfo> bookInfos;
                bookInfos = db.queryBooks();
                ListView lv = (ListView) getActivity().findViewById(R.id.progressfrag_lv);
                lv.setAdapter(new Frag2Adapter(getContext(),bookInfos));

            }
        });
        return v;
    }

    public interface  refreshbookes
    {
        void refreshbookes();
    }
}
