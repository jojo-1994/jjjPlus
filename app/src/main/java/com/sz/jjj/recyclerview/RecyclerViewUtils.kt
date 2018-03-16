package com.sz.jjj.recyclerview

import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.sz.jjj.MainActivity
import com.sz.jjj.R

/**
 * Created by jjj on 2018/3/16.
@description:
 */
object RecyclerViewUtils {

    fun setGridLayoutManager(context: Context, recyclerView: RecyclerView, map: Map<String, Class<*>>) {
        var title = ArrayList<String>()
        var activites = ArrayList<Class<*>>()
        for (a in map) {
            title.add(a.key)
            activites.add(a.value)
        }
        recyclerView.setLayoutManager(GridLayoutManager(context, 2))
        val homeAdapter = MainActivity.HomeAdapter(R.layout.main_item_view, title)
        recyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val intent = Intent(context, activites[position])
                context.startActivity(intent)
            }
        })
        recyclerView.setAdapter(homeAdapter)
    }

}