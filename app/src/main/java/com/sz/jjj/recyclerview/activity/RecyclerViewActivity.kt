package com.sz.jjj.recyclerview.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sz.jjj.R
import com.sz.jjj.recyclerview.RecyclerViewUtils
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by jjj on 2018/3/27.
@description:
 */
class RecyclerViewActivity: AppCompatActivity() {

    private val map = mapOf<String, Class<*>>(
            "空布局" to EmptyRecyclerviewActivity::class.java,
            "自定义" to CommonRecyclerViewActivity::class.java,
            "multipleItem" to MultiItemRecyclerViewActivity::class.java,
            "multipleItem" to MultiItemRecyclerViewActivity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        RecyclerViewUtils.setGridLayoutManager(this, recyclerView, map)
    }
}