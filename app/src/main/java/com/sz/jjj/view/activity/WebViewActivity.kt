package com.sz.jjj.view.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.sz.jjj.R
import com.sz.jjj.view.widget.WebProgressBarView
import kotlinx.android.synthetic.main.view_webview_activity.*


/**
 * Created by jjj on 2017/7/28.
@description:
 */
class WebViewActivity : AppCompatActivity() {

    var isContinue: Boolean = false
    lateinit var mProgress: WebProgressBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_webview_activity)

        mProgress = WebProgressBarView(this)
        mProgress.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 9))
        webView.addView(mProgress)

        val settings = webView.getSettings()
        settings.setJavaScriptEnabled(true)
        settings.setDomStorageEnabled(false)
        settings.setDefaultTextEncodingName("utf-8")
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                //如果进度条隐藏则让它显示
                if (View.GONE == mProgress.getVisibility()) {
                    mProgress.setVisibility(View.VISIBLE)
                }

                Log.e("newProgress", newProgress.toString())
                if (newProgress >= 85) {
                    if (isContinue) {
                        return
                    }
                    isContinue = true
                    mProgress.setCurProgress(1000, object : WebProgressBarView.EventEndListener {
                        override fun onEndEvent() {
                            isContinue = false
                            if (mProgress.getVisibility() == View.VISIBLE) {
                                hideProgress()
                            }
                        }
                    })
                } else {
                    mProgress.setNormalProgress(newProgress)
                }
            }
        }
        webView.loadUrl("https://www.baidu.com/")
    }

    fun hideProgress() {
        val animation = getDismissAnim(this)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(a: Animation?) {

            }

            override fun onAnimationEnd(a: Animation?) {
                mProgress.setVisibility(View.GONE)
            }

            override fun onAnimationStart(a: Animation?) {
            }

        })
        mProgress.startAnimation(animation)
    }

    fun getDismissAnim(context: Context): AnimationSet {
        val dismiss = AnimationSet(context, null)
        val alpha = AlphaAnimation(1.0f, 0.0f)
        alpha.setDuration(1000)
        dismiss.addAnimation(alpha)
        return dismiss
    }
}