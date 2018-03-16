package com.sz.jjj.anim.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.sz.jjj.R;
import com.sz.jjj.anim.Rotate3dAnimation;
import com.sz.jjj.view.widget.Rotate3dDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj on 2017/8/22.
 *
 * @description: 3d翻转动画
 */

public class Rotate3dAnimActivity extends AppCompatActivity {
    @BindView(R.id.btn_rotate)
    Button btnRotate;
    @BindView(R.id.image)
    TextView image;

    private float centerX, centerY, depthZ = 400;
    private int duration = 600;
    private Rotate3dAnimation openAnimation;
    private Rotate3dAnimation closeAnimation;
    private boolean isOpen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_3drotate_activity);
        ButterKnife.bind(this);

        Rotate3dDialog dialog = new Rotate3dDialog(this, "DDD");
//        dialog.show();
    }

    @OnClick(R.id.btn_rotate)
    public void onViewClicked() {
        if(openAnimation == null){
            initOpenAnim();
            initCloseAnim();
        }

        //用作判断当前点击事件发生时动画是否正在执行
        if (openAnimation.hasStarted() && !openAnimation.hasEnded()) {
            return;
        }
        if (closeAnimation.hasStarted() && !closeAnimation.hasEnded()) {
            return;
        }

        if (isOpen) {
            image.startAnimation(closeAnimation);
        }else {
            image.startAnimation(openAnimation);
        }
        isOpen = !isOpen;
    }

    private void initOpenAnim() {
        centerX = image.getWidth() / 2;
        centerY = image.getHeight() / 2;

        //从0到90度，顺时针旋转视图，此时reverse参数为true，达到90度时动画结束时视图变得不可见，
        openAnimation = new Rotate3dAnimation(0, 90, centerX, centerY, depthZ, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
        openAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //从270到360度，顺时针旋转视图，此时reverse参数为false，达到360度动画结束时视图变得可见
                image.setText("反面");
                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(270, 360, centerX, centerY, depthZ, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                image.startAnimation(rotateAnimation);
            }
        });
    }

    private void initCloseAnim() {
        closeAnimation = new Rotate3dAnimation(360, 270, centerX, centerY, depthZ, true);
        closeAnimation.setDuration(duration);
        closeAnimation.setFillAfter(true);
        closeAnimation.setInterpolator(new AccelerateInterpolator());
        closeAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.setText("正面");
                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(90, 0, centerX, centerY, depthZ, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                image.startAnimation(rotateAnimation);
            }
        });
    }
}
