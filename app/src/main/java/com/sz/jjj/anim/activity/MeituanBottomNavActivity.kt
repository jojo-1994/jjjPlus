package com.sz.jjj.anim.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.sz.jjj.R
import com.sz.jjj.baselibrary.widget.reveallayout.CircularRevealButton
import kotlinx.android.synthetic.main.anim_bottomnav_activity.*

/**
 * Created by jjj on 2017/7/19.
@description:
 */
class MeituanBottomNavActivity : AppCompatActivity(), View.OnClickListener {

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
        setContentView(R.layout.anim_bottomnav_activity)
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

