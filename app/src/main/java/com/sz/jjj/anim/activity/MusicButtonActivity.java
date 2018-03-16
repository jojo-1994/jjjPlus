package com.sz.jjj.anim.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sz.jjj.R;
import com.sz.jjj.view.widget.MusicButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj on 2017/12/22.
 *
 * @description:
 */

public class MusicButtonActivity extends AppCompatActivity {

    @BindView(R.id.play)
    Button play;
    @BindView(R.id.stop)
    Button stop;
    @BindView(R.id.musicButton)
    MusicButton musicButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_musicbtn_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.play, R.id.stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play:
                musicButton.playMusic();
                if (musicButton.state == MusicButton.STATE_PLAYING) {
                    play.setText("暂停");
                } else if (musicButton.state == MusicButton.STATE_PAUSE) {
                    play.setText("继续");
                }
                break;
            case R.id.stop:
                musicButton.stopMusic();
                break;
        }
    }
}
