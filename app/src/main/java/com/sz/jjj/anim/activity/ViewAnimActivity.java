package com.sz.jjj.anim.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.sz.jjj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author:jjj
 * @data:2018/8/24
 * @description:
 */

public class ViewAnimActivity extends AppCompatActivity {
    @BindView(R.id.btn_translate)
    TextView btnTranslate;
    @BindView(R.id.btn_scale)
    Button btnScale;
    @BindView(R.id.btn_rotate)
    Button btnRotate;
    @BindView(R.id.btn_alpha)
    Button btnAlpha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_view_anim_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_translate, R.id.btn_scale, R.id.btn_rotate, R.id.btn_alpha})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_translate:
                Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
                btnTranslate.startAnimation(animation1);
                break;
            case R.id.btn_scale:
                Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
                btnScale.startAnimation(animation2);
                break;
            case R.id.btn_rotate:
                Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
                btnRotate.startAnimation(animation3);
                break;
            case R.id.btn_alpha:
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
                alphaAnimation.setDuration(3000);
                btnAlpha.startAnimation(alphaAnimation);
                break;
        }
    }
}
