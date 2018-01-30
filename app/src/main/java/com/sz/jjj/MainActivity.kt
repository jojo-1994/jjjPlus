package com.sz.jjj

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.sz.jjj.activity.*
import com.sz.jjj.adapter.HomeAdapter
import com.sz.jjj.keyboard.SoftKeyboardActivity
import com.sz.jjj.lockscreen.activity.LockScreenActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val ACTIVITY = arrayOf<Class<*>>(
            MeituanBottomNavActivity::class.java, EmptyRecyclerviewActivity::class.java,
            VideoViewActivity::class.java, WebViewActivity::class.java,
            ListernNotificationActivity::class.java, DayNightModeActivity::class.java,
            DataBaseActivity::class.java, Rotate3dAnimActivity::class.java,
            XmlParseActivity::class.java, UpdateApkActivity::class.java,
            LockScreenActivity::class.java, SoftKeyboardActivity::class.java,
            SlidingTabActivity::class.java, FiveStarReViewActivity::class.java,
            BlueToothActivity::class.java, MusicButtonActivity::class.java)
    private val TITLE = arrayOf(
            "仿美图底部导航", "Recyclerview空布局",
            "VideoView小试牛刀", "WebView进度条",
            "监听通知消息", "白天/夜晚切换",
            "数据库操作", "3d翻转动画",
            "xml解析", "APK的更新",
            "锁屏设置", "自定义软键盘",
            "SlidingTabLayout", "五星点评",
            "蓝牙demo", "旋转动画")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

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
