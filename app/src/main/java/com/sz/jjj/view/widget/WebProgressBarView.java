package com.sz.jjj.view.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sz.jjj.R;

/**
 * Created by jjj on 2017/7/28.
 *
 * @description:
 */

public class WebProgressBarView extends View {

    private Context mContext;
    private int mCurProgress;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private int mColor;
    private EventEndListener endListener;


    public WebProgressBarView(Context context) {
        this(context, null);
    }

    public WebProgressBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.appProgress);
        mCurProgress = array.getInt(R.styleable.appProgress_progress, 0);
        mHeight = array.getInt(R.styleable.appProgress_progressHeight, 200);
        mColor = array.getColor(R.styleable.appProgress_progressColor, Color.parseColor("#06a7e1"));
        array.recycle();

        mPaint = new Paint();
        mPaint.setColor(mColor);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float result = mWidth * ((float) mCurProgress / (float) 100);
        canvas.drawRect(0, 0, result, mHeight, mPaint);
    }

    public void setCurProgress(long time, final EventEndListener endListener) {
        ValueAnimator animator = ValueAnimator.ofInt(mCurProgress, 100);
        animator.setDuration(time);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setNormalProgress((Integer) animation.getAnimatedValue());
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (endListener != null) {
                    endListener.onEndEvent();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();

    }

    public void setNormalProgress(int mCurProgress) {
        this.mCurProgress = mCurProgress;
        postInvalidate();
    }


    public interface EventEndListener {
        void onEndEvent();
    }
}
