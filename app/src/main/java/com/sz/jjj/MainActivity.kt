package com.sz.jjj

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.WindowManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.sz.jjj.activity.*
import com.sz.jjj.adapter.HomeAdapter
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    private val ACTIVITY = arrayOf<Class<*>>(
            MeituanBottomNavActivity::class.java, EmptyRecyclerviewActivity::class.java,
            VideoViewActivity::class.java, WebViewActivity::class.java,
            AdaptiveActivity::class.java)
    private val TITLE = arrayOf(
            "仿美图底部导航", "Recyclerview空布局",
            "VideoView小试牛刀", "WebView进度条")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val width = wm.defaultDisplay.width
        val height = wm.defaultDisplay.height
        recyclerView.setLayoutManager(GridLayoutManager(this, 2))

        val homeAdapter = HomeAdapter(R.layout.item_home_view, TITLE.toList())

        recyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val intent = Intent(this@MainActivity, ACTIVITY[position])
                startActivity(intent)
            }
        })

        recyclerView.setAdapter(homeAdapter)
    }

}
