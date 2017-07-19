package com.sz.jjj.widget;

import android.animation.Animator;
import android.view.View;

import com.sz.jjj.baselibrary.widget.reveallayout.CircularRevealLayout;


/**
 * Created by zhangke on 2016-11-4.
 */
public class ViewAnimationCompatUtils {

    private ViewAnimationCompatUtils(){}

    public static Animator createCircularReveal(final View view,
                                                final int centerX, final int centerY, final float startRadius, final float endRadius) {

        Animator animator = CircularRevealLayout.Builder.on(view)
                .centerX(centerX)
                .centerY(centerY)
                .startRadius(startRadius)
                .endRadius(endRadius)
                .create();

        return animator;

    }



}
