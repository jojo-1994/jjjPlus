package com.sz.jjj.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.sz.jjj.R
import kotlinx.android.synthetic.main.activity_day_night_mode.*





/**
 * Created by jjj on 2017/8/1.
@description:
 */
class DayNightModeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_night_mode)

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
            val linear_bar = findViewById(R.id.ll_title) as ViewGroup // 所需要设置控件
            linear_bar.post { linear_bar.setPadding(0, getStatusBarHeight(), 0, 0) }
            translucent.setText(translucent.text)
        })

        day.setOnClickListener(View.OnClickListener {
            setTheme(R.style.MarioTheme_Day)
            image.setBackgroundResource(R.color.colorPrimary)
        })
        night.setOnClickListener(View.OnClickListener {
            setTheme(R.style.MarioTheme_Night)
            image.setBackgroundResource(R.color.background_material_dark)
        })
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

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId)
        }
        return result
    }
}