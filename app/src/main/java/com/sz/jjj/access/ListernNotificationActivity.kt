package com.sz.jjj.access

import android.content.*
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.sz.jjj.Config
import com.sz.jjj.R
import kotlinx.android.synthetic.main.access_listern_notification_activity.*


/**
 * Created by jjj on 2017/7/31.
 * 辅助功能
@description: https://github.com/lendylongli/qianghongbao
http://www.jianshu.com/p/d83b2caa5249
 */
class ListernNotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.access_listern_notification_activity)

        monitor.setOnClickListener(View.OnClickListener {
            if (!isEnabled()) {
                monitor.setText("点我打开网络监听器")
                startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                val toast = Toast.makeText(applicationContext, "监控器开关已关闭", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                monitor.setText("监控器开关已打开")
                val toast = Toast.makeText(applicationContext, "监控器开关已打开", Toast.LENGTH_SHORT)
                toast.show()
            }
        })

        setting.setOnClickListener(View.OnClickListener {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        })

        access.setOnClickListener(View.OnClickListener {
            openAccessibilityServiceSettings()
        })

        val filter = IntentFilter()
        filter.addAction(Config.ACTION_MyACCESSITITLITY_SERVICE_CONNECT)
        registerReceiver(receiver, filter)
    }

    /** 打开辅助服务的设置 */
    private fun openAccessibilityServiceSettings() {
        try {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
            Toast.makeText(this, "找[AutoClick],然后开启服务", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    override fun onResume() {
        super.onResume()
        if (!isEnabled()) {
            monitor.setText("点我打开网络监听器")
            val toast = Toast.makeText(applicationContext, "监控器开关已关闭", Toast.LENGTH_SHORT)
            toast.show()
        } else {
            monitor.setText("监控器开关已打开")
            val toast = Toast.makeText(applicationContext, "监控器开关已打开", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun isEnabled(): Boolean {
        val pkgName = packageName
        val flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":")
            for (name in names) {
                val cn = ComponentName.unflattenFromString(name);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (isFinishing) {
                return
            }
            val action = intent.action
            Log.d(Config.TAG, "receive-->" + action!!)

        }
    }
}