package com.sz.jjj.view.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import com.sz.jjj.R
import com.sz.jjj.view.fragment.TestFragment
import kotlinx.android.synthetic.main.view_custom_tablayout_activity.*

/**
 * Created by jjj on 2018/4/18.
@description:
 */
class CustomTabLayoutActivity : AppCompatActivity() {

    var tabFragments: ArrayList<Fragment> = ArrayList()
    var title = arrayListOf<String>("龙王三太子", "哪吒")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_custom_tablayout_activity)
        for (i in 0..title.size - 1) {
            tabFragments.add(TestFragment.getInstance(title[i]))
        }
        viewpager.setAdapter(MyViewPageAdapter(getSupportFragmentManager()))
        tablayout.setupWithViewPager(viewpager)
        for (i in 0..title.size - 1) {
            var tab = tablayout.getTabAt(i)
            tab!!.setCustomView(getTabView(i))
        }
        tablayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
//                tablayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                var lp = tablayout.layoutParams
//                lp.height = getTabView(0).height
//                tablayout.setLayoutParams(lp)
                tablayout.height
                Log.e("dddddd", getTabView(0).height.toString())
            }
        })
    }

    fun getTabView(position: Int): View {
        var view = LayoutInflater.from(this).inflate(R.layout.view_custom_tab, null)
        var ll_tab = view.findViewById<LinearLayout>(R.id.ll_tab)
        Log.e("dddddd", getTabView(0).height.toString())
        return view
    }

    inner class MyViewPageAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {


        override fun getCount(): Int {
            return tabFragments.size
        }

        override fun getItem(position: Int): Fragment {
            return tabFragments.get(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return title[position]
        }

    }
}
