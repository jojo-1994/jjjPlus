package com.sz.jjj.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by jjj on 2017/7/18.
 *
 * @description:
 */

public class CircleAnimateUtils {

    public static void handleAnimate(final View animateView) {
        // 显示

        Animator mAnimator = ViewAnimationCompatUtils.createCircularReveal(animateView,
                animateView.getWidth() / 2, animateView.getHeight() / 2, 0, (float) Math.hypot(animateView.getWidth(), animateView.getHeight()));
        mAnimator.setDuration(5000);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                animateView.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();

        // 5.0揭露动画
//        if (animateView.getVisibility() == View.VISIBLE) {
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                final Animator animator = ViewAnimationUtils.createCircularReveal(animateView,
//                        animateView.getWidth() / 2,
//                        animateView.getHeight() / 2,
//                        0,
//                        (float) Math.hypot(animateView.getWidth(), animateView.getHeight()));
//                animator.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        animateView.setVisibility(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                animator.setDuration(5000);
//                animator.start();
//            } else {
//                animateView.setVisibility(View.VISIBLE);
//            }
//            animateView.setEnabled(true);
//        }
    }
}

