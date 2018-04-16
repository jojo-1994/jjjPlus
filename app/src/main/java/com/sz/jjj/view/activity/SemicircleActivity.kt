package com.sz.jjj.view.activity

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import com.sz.jjj.R
import kotlinx.android.synthetic.main.view_semicircle_activity.*

/**
 * Created by jjj on 2018/4/16.
@description: 半圆背景
 */
class SemicircleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_semicircle_activity)
        des.setText("shape的半圆绘制在4.4.2及其以下版本会出现拉伸，这与Radius的大小有关，实现半圆效果需要将radius大小设置成高度的一半。")
        // 在视图绘制时调用该监听事件，会被调用多次，需要及时移除
        text.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                // 移除改监听
                text.viewTreeObserver.removeOnPreDrawListener(this)
                // 获取控件一半高度
                var height = text.height / 2
                // 获取控件背景并转为GradientDrawable对象
                var gd = text.background as GradientDrawable
                // 设置GradientDrawable的的corner, 1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
                gd.cornerRadii = floatArrayOf(0f, 0f,
                        height.toFloat(), height.toFloat(),
                        height.toFloat(), height.toFloat(),
                        0f, 0f)
                return true
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.e("onWindowFocusChanged", getMeasureHeight(text).toString())
    }

    fun getMeasureHeight(view: View): Int {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        return view.measuredHeight
    }
}