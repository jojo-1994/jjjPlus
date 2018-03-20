package com.sz.jjj.inputmanager.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sz.jjj.R
import com.sz.jjj.inputmanager.InputManagerHelper
import kotlinx.android.synthetic.main.inputmanager_activity.*

/**
 * Created by jjj on 2018/3/20.
@description:
 */
class InputManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inputmanager_activity)
    }

    override fun onResume() {
        super.onResume()
        InputManagerHelper.attachToActivity(this).bind(keyboard_view, tv_login).offset(20)
        // 更多使用：https://www.jianshu.com/p/50c060edeaa8
    }

    override fun onStop() {
        super.onStop()
        InputManagerHelper.attachToActivity(this).unbind()
    }
}