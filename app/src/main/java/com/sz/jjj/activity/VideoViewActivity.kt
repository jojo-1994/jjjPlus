package com.sz.jjj.activity

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sz.jjj.R
import kotlinx.android.synthetic.main.activity_videoview.*

/**
 * Created by jjj on 2017/7/26.
@description:
 */
class VideoViewActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videoview)

        val uri = Uri.parse("android.resource://com.sz.jjj/raw/" + R.raw.test1)
        play.setOnClickListener(View.OnClickListener { videoView.playVideo(uri) })
        pause.setOnClickListener(View.OnClickListener { videoView.pause() })
        start.setOnClickListener(View.OnClickListener { videoView.start() })
        stop.setOnClickListener(View.OnClickListener { videoView.stopPlayback() })
    }
}