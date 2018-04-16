package com.sz.jjj.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sz.jjj.R
import com.sz.jjj.recyclerview.RecyclerViewUtils
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by jjj on 2018/3/16.
@description: 控件相关
 */
class CustomViewActivity : AppCompatActivity() {

    private val map = mapOf<String, Class<*>>(
            "VideoView小试牛刀" to VideoViewActivity::class.java,
            "WebView进度条" to WebViewActivity::class.java,
            "自定义软键盘" to SoftKeyboardActivity::class.java,
            "SlidingTabLayout" to SlidingTabActivity::class.java,
            "五星点评" to FiveStarsViewActivity::class.java,
            "半圆" to SemicircleActivity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        RecyclerViewUtils.setGridLayoutManager(this, recyclerView, map)
    }

}