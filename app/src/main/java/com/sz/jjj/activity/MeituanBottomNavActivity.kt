package com.sz.jjj.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.sz.jjj.R
import com.sz.jjj.baselibrary.widget.reveallayout.CircularRevealButton
import kotlinx.android.synthetic.main.activity_meituan_bottomnav.*

/**
 * Created by jjj on 2017/7/19.
@description:
 */
class MeituanBottomNavActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var aa: String
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.crbtn_chats -> {
                onSelect(crbtn_chats)
            }
            R.id.crbtn_contacts -> {
                onSelect(crbtn_contacts)
            }
            R.id.crbtn_discover -> {
                onSelect(crbtn_discover)
            }
            R.id.crbtn_about_me -> {
                onSelect(crbtn_about_me)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meituan_bottomnav)
        crbtn_chats.setOnClickListener(this)
        crbtn_contacts.setOnClickListener(this)
        crbtn_discover.setOnClickListener(this)
        crbtn_about_me.setOnClickListener(this)

        for (i in 0..3) {
            var textView = TextView(this)
            textView.setText("英雄联盟")
            textView.setTextColor(Color.WHITE)
            textView.setTextSize(22f)
            textView.setGravity(Gravity.CENTER)
            textView.setBackgroundColor(Color.BLUE)
            flowLayout.addView(textView)
        }

        val ints: Array<Float> = arrayOf(1f, 2f, 3f)
        val any = Array<Number>(4) { 3 }
        copy(ints, any)

        Log.e("ints", ints.get(0).toString())
        Log.e("any", any.get(0).toString())

        fill(any, 3f)
        Log.e("any", any.get(0).toString())

        var fun0 = Fun0<Int, Number>()
        var list: Array<Int> = arrayOf(2, 3, 4)
        var list2: Array<Number> = arrayOf(2, 3, 4)
        fun0.print(list, list2)
        var funf: Fun0<*, *> = fun0

        list.sort()
    }


    open class Fun0<T, U>() {
        lateinit var fromm: Array<T>
        lateinit var too: Array<U>
        fun print(from: Array<T>, to: Array<U>) {
            fromm = from
            too = to
        }

        fun getT(): Array<in T> {
            return fromm
        }
    }

    interface Source<out T> {
        fun nextT(): T
    }

    fun test(strs: Source<String>) {
        var x: Source<Any> = strs
    }


    // 类型投影
    fun <T> copy(from: Array<out T>, to: Array<T>) {
        assert(from.size == to.size)
        for (i in to.indices) {
            to[i] = from[i]
        }
    }

    fun <T> fill(dest: Array<in T>, value: T) {
        dest.set(0, value)
    }


    fun onSelect(selectBtn: CircularRevealButton) {
        var buttons = arrayOf<CircularRevealButton>(crbtn_chats, crbtn_contacts, crbtn_discover, crbtn_about_me)

        for (btn in buttons) {
            if (btn == selectBtn) {
                btn.setonSelected(true)
            } else {
                btn.setonSelected(false)
            }
        }
    }


}

