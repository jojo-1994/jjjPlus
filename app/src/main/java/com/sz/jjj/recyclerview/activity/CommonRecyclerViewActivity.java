package com.sz.jjj.recyclerview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sz.jjj.R;
import com.sz.jjj.recyclerview.library.base.BaseViewHolder;
import com.sz.jjj.recyclerview.library.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jjj on 2018/3/27.
 *
 * @description:
 */

public class CommonRecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        List<String> list = new ArrayList<>();
        list.add("aaaa");
        list.add("bbbb");
        list.add("cccc");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseAdapter<String>(R.layout.recyclerview_my_item, list) {
            @Override
            public void convert(BaseViewHolder holder, String s) {
                holder.setText(R.id.textView, s);
            }
        });

    }
}
