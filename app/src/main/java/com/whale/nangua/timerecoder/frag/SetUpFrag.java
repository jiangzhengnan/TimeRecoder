package com.whale.nangua.timerecoder.frag;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.whale.nangua.timerecoder.R;
import com.whale.nangua.timerecoder.db.DBUtils;

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
                  DBUtils.getInstance(getContext()).clearBooks();
                Snackbar.make(textfrag_clearbtn,"清空数据",Snackbar.LENGTH_SHORT).show();
                new ProgressFrag().progressFrag.clearbookes();
            }
        });
        return v;
    }

    public interface  clearBookes
    {
        void clearbookes();
    }
}
