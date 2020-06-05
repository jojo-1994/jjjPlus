package com.sz.jjj.anim.activity;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sz.jjj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author:jjj
 * @data:2018/8/24
 * @description:
 */

public class ObjectAnimActivity extends AppCompatActivity {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.btn_anim1)
    Button btnAnim1;
    @BindView(R.id.btn_anim2)
    Button btnAnim2;
    @BindView(R.id.btn_anim3)
    Button btnAnim3;
    @BindView(R.id.btn_anim4)
    Button btnAnim4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_object_anim_activity);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.btn_anim1, R.id.btn_anim2, R.id.btn_anim3, R.id.btn_anim4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_anim1:
                // 平移
                ObjectAnimator.ofFloat(image, "translationY",0,  image.getHeight()).start();
                break;
            case R.id.btn_anim2:
                // 颜色渐变动画
                ValueAnimator colorAnim = ObjectAnimator.ofInt(image, "backgroundColor",
                        0xffff8080, 0xff8080ff);
                colorAnim.setDuration(3000);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                colorAnim.setRepeatMode(ValueAnimator.RESTART);
                colorAnim.start();
                break;
            case R.id.btn_anim3:
                // 动画集合
                AnimatorSet set = new AnimatorSet();
                set.playTogether(
                        ObjectAnimator.ofFloat(image, "rotationX", 0, 360),
                        ObjectAnimator.ofFloat(image, "rotationY", 0, 360),
                        ObjectAnimator.ofFloat(image, "rotation", 0, -90),
                        ObjectAnimator.ofFloat(image, "translationX", 0, 90),
                        ObjectAnimator.ofFloat(image, "translationY", 0, 90),
                        ObjectAnimator.ofFloat(image, "scaleX", 1, 1.5f),
                        ObjectAnimator.ofFloat(image, "scaleY", 1, 1.5f),
                        ObjectAnimator.ofFloat(image, "alpha", 1, 0.25f, 1)
                );
                set.start();
                break;
            case R.id.btn_anim4:
                ObjectAnimator.ofInt(btnAnim4, "width", 500).setDuration(5000).start();
                break;
            default:
        }
    }
}
