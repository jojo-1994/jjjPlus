package com.sz.jjj

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.sz.jjj.activity.MeituanBottomNavActivity
import com.sz.jjj.adapter.HomeAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val ACTIVITY = arrayOf<Class<*>>(MeituanBottomNavActivity::class.java)
    private val TITLE = arrayOf("仿美图底部导航")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.setLayoutManager(GridLayoutManager(this, 2))

        val homeAdapter = HomeAdapter(R.layout.item_home_view, TITLE.toList())
        homeAdapter.openLoadAnimation()

        recyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val intent = Intent(this@MainActivity, ACTIVITY[position])
                startActivity(intent)
            }
        })

        recyclerView.setAdapter(homeAdapter)
    }

}
