package com.sz.jjj.thread.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sz.jjj.R
import com.sz.jjj.recyclerview.RecyclerViewUtils
import kotlinx.android.synthetic.main.main_activity.*

/**
 *@author:jjj
 *@data:2018/8/15
 *@description:
 */
class ThreadActivity : AppCompatActivity() {
    private val map = mapOf<String, Class<*>>(
            "AsyncTask" to AsyncTaskActivity::class.java,
            "蓝牙扫描--内存泄漏" to LeakBlueScanActivity::class.java,
            "IntentService" to IntentServiceActivity::class.java,
            "线程池" to ExecutorPoolActivity::class.java,
            "多个线程" to ThreadMoreActivity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        RecyclerViewUtils.setGridLayoutManager(this, recyclerView, map)
    }
}