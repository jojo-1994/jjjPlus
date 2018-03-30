package com.sz.jjj

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sz.jjj.access.ListernNotificationActivity
import com.sz.jjj.anim.activity.AnimActivity
import com.sz.jjj.ble.activity.BlueToothActivity
import com.sz.jjj.database.DataBaseActivity
import com.sz.jjj.gson.GsonActivity
import com.sz.jjj.inputmanager.activity.InputManagerActivity
import com.sz.jjj.ipc.IPCActivity
import com.sz.jjj.lockscreen.activity.LockScreenStartActivity
import com.sz.jjj.recyclerview.RecyclerViewUtils
import com.sz.jjj.recyclerview.activity.RecyclerViewActivity
import com.sz.jjj.rxjava.RxjavaActivity
import com.sz.jjj.statusbar.StatusBarActivity
import com.sz.jjj.updateapk.UpdateApkActivity
import com.sz.jjj.view.activity.CustomViewActivity
import com.sz.jjj.xml.XmlParseActivity
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private val map = mapOf<String, Class<*>>(
            "动画" to AnimActivity::class.java,
            "Recyclerview" to RecyclerViewActivity::class.java,
            "控件" to CustomViewActivity::class.java,
            "状态栏" to StatusBarActivity::class.java,
            "辅助服务" to ListernNotificationActivity::class.java,
            "数据库" to DataBaseActivity::class.java,
            "xml解析" to XmlParseActivity::class.java,
            "APK的更新" to UpdateApkActivity::class.java,
            "锁屏设置" to LockScreenStartActivity::class.java,
            "蓝牙" to BlueToothActivity::class.java,
            "软键盘弹出" to InputManagerActivity::class.java,
            "gson" to GsonActivity::class.java,
            "rxjava" to RxjavaActivity::class.java,
            "进程" to IPCActivity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        RecyclerViewUtils.setGridLayoutManager(this, recyclerView, map)
    }

    class HomeAdapter(layoutResId: Int, data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

        override fun convert(helper: BaseViewHolder, item: String) {
            helper.setText(R.id.text, item)
        }
    }
}
