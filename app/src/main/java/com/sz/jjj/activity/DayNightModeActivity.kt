package com.sz.jjj.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
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

        day.setOnClickListener(View.OnClickListener { setTheme(R.style.MarioTheme_Day) })
        night.setOnClickListener(View.OnClickListener { setTheme(R.style.MarioTheme_Night) })
    }
}