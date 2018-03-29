package com.sz.jjj.recyclerview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sz.jjj.R;
import com.sz.jjj.recyclerview.adapter.MultiItemAdapter;
import com.sz.jjj.recyclerview.bean.MultiItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jjj on 2018/3/27.
 *
 * @description:
 */

public class MultiItemRecyclerViewActivity extends AppCompatActivity{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        List<MultiItemBean> list=new ArrayList<>();
        list.add(new MultiItemBean("aaa", MultiItemBean.TEXT));
        list.add(new MultiItemBean("bbb", MultiItemBean.TEXT));
        list.add(new MultiItemBean("ccc", MultiItemBean.TEXT));
        list.add(new MultiItemBean(R.drawable.anim_music_iv, MultiItemBean.IMAGE));
        list.add(new MultiItemBean("fff", MultiItemBean.TEXT));
        MultiItemAdapter mAdapter = new MultiItemAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }
}
