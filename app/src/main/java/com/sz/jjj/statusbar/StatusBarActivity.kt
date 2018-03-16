package com.sz.jjj.statusbar

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import com.sz.jjj.R
import com.sz.jjj.util.ScreenUtils
import kotlinx.android.synthetic.main.status_bar_activity.*


/**
 * Created by jjj on 2017/8/1.
@description:
 */
class StatusBarActivity : AppCompatActivity() {

    lateinit var content: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.status_bar_activity)

        var mHeight = 0
        content = findViewById(android.R.id.content)
        content.getViewTreeObserver().addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
            Log.e("eeeee", "界面有调整")
            if (content.height != mHeight) {
                hideNavigationBar() // 隐藏导航栏
                Log.e("eeeeee", content.height.toString())
                if (mHeight == 0) {
                    mHeight = content.height
                }
            }
        })

        var screenWidth = ScreenUtils.getScreenWidth(this).toString()
        var screenHeight = ScreenUtils.getScreenHeight(this).toString()
        var statusBarHeight = getStatusBarHeight().toString()
        var navigationBarHeight = getNavigationBarHeight().toString()
        describe.setText("屏幕高*宽：$screenHeight*$screenWidth\n" +
                "状态栏高度：$statusBarHeight\n" +
                "底部导航栏高度:$navigationBarHeight")

        translucent.setOnClickListener(View.OnClickListener {
            setTranslucentStatus()
            translucent.setText(translucent.text)
        })

        translucent2.setOnClickListener(View.OnClickListener {
            setTranslucentStatus2()
            translucent.setText(translucent.text)
        })

        paddingtop.setOnClickListener(View.OnClickListener {
            setTranslucentStatus2()
            val linear_bar = ll_title // 所需要设置控件
            linear_bar.post { linear_bar.setPadding(0, getStatusBarHeight().toInt(), 0, 0) }
            translucent.setText(translucent.text)
        })

        hiddenNav.setOnClickListener(View.OnClickListener {
            hideNavigationBar()
        })
    }

    protected fun hideNavigationBar() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    //                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE

            decorView.systemUiVisibility = uiOptions
        }
    }

    fun setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val attributes = window.attributes
            attributes.flags = attributes.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            window.attributes = attributes
        }
    }

    fun setTranslucentStatus2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT)
        }
    }

    fun getStatusBarHeight(): Float {
        var result = 0
        val resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId)
        }
        return result.toFloat()
    }

    private fun getNavigationBarHeight(): Int {
        val resources = getResources()
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        val height = resources.getDimensionPixelSize(resourceId)
        Log.v("dbw", "Navi height:" + height)
        return height
    }

    fun dip2px(dpValue: Float): Int? {
        val scale = getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
//        content.getViewTreeObserver().removeOnGlobalLayoutListener(this)
    }
}