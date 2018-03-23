package com.sz.jjj.statusbar

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.sz.jjj.R
import com.sz.jjj.util.ScreenUtils
import kotlinx.android.synthetic.main.status_bar_activity.*


/**
 * Created by jjj on 2017/8/1.
@description:
 */
class StatusBarActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.status_bar_activity)

        //
//        var content = getWindow().getDecorView();
//        content.setOnSystemUiVisibilityChangeListener() {
//            hideNavigationBar()
//        }

        var screenWidth = ScreenUtils.getScreenWidth(this).toString()
        var screenHeight = ScreenUtils.getScreenHeight(this).toString()
        var statusBarHeight = getStatusBarHeight().toString()
        var navigationBarHeight = getNavigationBarHeight().toString()
        describe.setText("屏幕高*宽：$screenHeight*$screenWidth\n" +
                "状态栏高度：$statusBarHeight\n" +
                "底部导航栏高度:$navigationBarHeight")

        translucent1.setOnClickListener(View.OnClickListener {
            setTranslucentStatus1()
        })

        translucent2.setOnClickListener(View.OnClickListener {
            setTranslucentStatus2()
        })

        paddingtop.setOnClickListener(View.OnClickListener {
            setStatusBar()
        })

        hiddenNav.setOnClickListener(View.OnClickListener {
            hideNavigationBar()
        })

        lightMode.setOnClickListener(View.OnClickListener {
            Eyes.setStatusBarLightMode(this, Color.WHITE)
        })
    }

    /**
     * 隐藏虚拟按键并全屏
     */
    protected fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or // 亮色主题
//                    View.SYSTEM_UI_FLAG_FULLSCREEN or // 是否全屏
                    View.SYSTEM_UI_FLAG_IMMERSIVE

            decorView.systemUiVisibility = uiOptions
        }
    }

    /**
     * 状态栏灰色半透明，布局置顶系统窗口
     */
    fun setTranslucentStatus1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val attributes = window.attributes
            attributes.flags = attributes.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            window.attributes = attributes
        }
    }

    /**
     * 去除灰色蒙尘效果，布局仍然置顶系统窗口
     */
    fun setTranslucentStatus2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT)
        }
    }

    /**
     * 状态栏正常显示，最终效果
     */
    fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT)
            val linear_bar = ll_title // 所需要设置控件
            linear_bar.post { linear_bar.setPadding(0, getStatusBarHeight().toInt(), 0, 0) }
        }
    }

    /**
     * 获取顶部状态栏高度
     */
    fun getStatusBarHeight(): Float {
        var result = 0
        val resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId)
        }
        return result.toFloat()
    }

    /**
     * 获取底部导航栏高度
     */
    private fun getNavigationBarHeight(): Int {
        val resources = getResources()
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        val height = resources.getDimensionPixelSize(resourceId)
        Log.v("dbw", "Navi height:" + height)
        return height
    }


    /**
     * 设置小米手机StatusBar颜色为深色
     * @param activity
     * @param darkmode
     * @return
     */
    fun setMiuiStatusBarDarkMode(activity: Activity, darkmode: Boolean): Boolean {
        val clazz = activity.window.javaClass
        try {
            var darkModeFlag = 0
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            extraFlagField.invoke(activity.window, if (darkmode) darkModeFlag else 0, darkModeFlag)
            return true
        } catch (e: Exception) {
        }

        return false
    }

    /**
     * 设置魅族手机的StatusBar颜色为深色
     * @param activity
     * @param dark
     * @return
     */
    fun setMeizuStatusBarDarkIcon(activity: Activity, dark: Boolean): Boolean {
        var result = false
        try {
            val lp = activity.window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            if (dark) {
                value = value or bit
            } else {
                value = value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            activity.window.attributes = lp
            result = true
        } catch (e: Exception) {
        }

        return result
    }

}