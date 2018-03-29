package com.sz.jjj.recyclerview.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sz.jjj.R
import com.sz.jjj.recyclerview.adapter.EmptyRecyclerviewAdapter
import kotlinx.android.synthetic.main.recyclerview_emptyview_activity.*

/**
 * Created by jjj on 2017/7/25.
@description:
 */
class EmptyRecyclerviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_emptyview_activity)

        val homeAdapter = EmptyRecyclerviewAdapter(this)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this));
        mRecyclerView.setAdapter(homeAdapter);
        mRecyclerView.setEmptyView(empty_view);

        btn_add.setOnClickListener(View.OnClickListener {
            homeAdapter.addData()
        })

        btn_delete.setOnClickListener(View.OnClickListener {
            homeAdapter.deleteData()
        })

    }
}