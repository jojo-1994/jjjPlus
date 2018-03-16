package com.sz.jjj.anim.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sz.jjj.R
import com.sz.jjj.recyclerview.RecyclerViewUtils
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by jjj on 2018/3/16.
@description:
 */
class AnimActivity : AppCompatActivity() {

    private val map = mapOf<String, Class<*>>(
            "仿美图底部导航" to MeituanBottomNavActivity::class.java,
            "3d翻转动画" to Rotate3dAnimActivity::class.java,
            "旋转动画" to MusicButtonActivity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        RecyclerViewUtils.setGridLayoutManager(this, recyclerView, map)
    }
}