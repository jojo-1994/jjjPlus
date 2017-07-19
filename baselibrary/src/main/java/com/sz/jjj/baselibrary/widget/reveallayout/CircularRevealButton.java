package com.sz.jjj.baselibrary.widget.reveallayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.jjj.baselibrary.R;

/**
 * Created by jjj on 2017/7/19.
 *
 * @description: 仿美团底部导航button按钮
 */

public class CircularRevealButton extends LinearLayout {

    private Drawable mFocusDrawable; // 选中图片
    private Drawable mDeFocusDrawable; // 未选中图片
    private int mFocusColor; // 选中颜色
    private int mDeFocusColor; // 未选中颜色
    private int mTextSize; // 底部文字大小
    private String mDesc; // 底部文字描述
    private boolean mAnimShow; // 是否显示动画效果
    private boolean mIsSelected; // 是否被选中

    private ImageView ivDeFocuse, ivFocuse;
    private TextView textView;

    public CircularRevealButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularRevealButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        addFrameLayout(context);
        addTextView(context);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircularRevealButton);
        mFocusDrawable = ta.getDrawable(R.styleable.CircularRevealButton_focus_icon);
        mDeFocusDrawable = ta.getDrawable(R.styleable.CircularRevealButton_defocus_icon);
        mFocusColor = ta.getColor(R.styleable.CircularRevealButton_focus_color, Color.BLUE);
        mDeFocusColor = ta.getColor(R.styleable.CircularRevealButton_defocus_color, Color.BLUE);
        mDesc = ta.getString(R.styleable.CircularRevealButton_text);
        mTextSize = ta.getDimensionPixelSize(R.styleable.CircularRevealButton_textsize, 12);
        mAnimShow = ta.getBoolean(R.styleable.CircularRevealButton_anim_show, false);
        mIsSelected = ta.getBoolean(R.styleable.CircularRevealButton_is_selected, false);
        ta.recycle();
    }

    // 添加图片
    private void addFrameLayout(Context context) {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // 未选中图片
        ivDeFocuse = new ImageView(context);
        ivDeFocuse.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ivDeFocuse.setImageDrawable(mDeFocusDrawable);
        frameLayout.addView(ivDeFocuse);

        // 选中图片
        ivFocuse = new ImageView(context);
        ivFocuse.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ivFocuse.setImageDrawable(mFocusDrawable);
        ivFocuse.setVisibility(mIsSelected ? VISIBLE : INVISIBLE);
        frameLayout.addView(ivFocuse);

        addView(frameLayout);
    }

    // 添加底部文字
    private void addTextView(Context context) {
        textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 3, 0, 0);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        textView.setTextColor(mIsSelected ? mFocusColor : mDeFocusColor);
        textView.setText(mDesc);
        addView(textView);
    }

    /**
     * 设置选中状态
     *
     * @param isSelected 是否选中
     */
    public void setonSelected(Boolean isSelected) {
        if (isSelected && ivFocuse.getVisibility() == INVISIBLE) {
            textView.setTextColor(mFocusColor);
            ivFocuse.setVisibility(VISIBLE);
            if (mAnimShow)
                handleAnimate(ivFocuse);

        } else {
            textView.setTextColor(mDeFocusColor);
            ivFocuse.setVisibility(INVISIBLE);
        }
    }

    // 揭露动画
    public void handleAnimate(final View animateView) {
        Animator mAnimator = CircularRevealLayout.Builder.on(animateView)
                .centerX(animateView.getWidth() / 2)
                .centerY(animateView.getHeight() / 2)
                .startRadius(0)
                .endRadius((float) Math.hypot(animateView.getWidth(), animateView.getHeight()))
                .create();
        mAnimator.setDuration(1000);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                animateView.setVisibility(VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    /**
     *重写此方法，可以在布局文件中预览
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

}
