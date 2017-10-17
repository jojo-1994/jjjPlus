package com.sz.jjj.keyboard.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.sz.jjj.R;

/**
 * Created by jjj on 2017/10/11.
 *
 * @description:
 */

public class SoftKeyBoard extends PopupWindow implements
        SoftKeyStatusListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = SoftKeyBoard.class.getSimpleName();
    private View contentView;
    private View decorView;
    private SafeEdit edit;
    private ImageView colseSoftKey;
    private RadioGroup inputTypeGroup;
    private SoftKeyAzLayView azLayView;
    private SoftKeyNumLayView numLayView;

    private int viewMode;
    private boolean isPendding = true;
    private int softKeyHeight;
    private int decorViewHeight;
    private StringBuilder inputStr;

    public SoftKeyBoard(Context context) {
        this(context, null);
    }

    public SoftKeyBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressWarnings("deprecation")
    public SoftKeyBoard(Context context, AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        decorView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        contentView = View.inflate(context, R.layout.layout_keyboard, null);
        colseSoftKey = (ImageView) contentView.findViewById(R.id.softkeyBoard_colse);
        inputTypeGroup = (RadioGroup) contentView.findViewById(R.id.sofkeyBoard_Type);
        azLayView = (SoftKeyAzLayView) contentView.findViewById(R.id.softkeyBoard_Az);
        numLayView = (SoftKeyNumLayView) contentView.findViewById(R.id.softkeyBoard_Number);
        contentView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        azLayView.setSoftKeyListener(this);
        numLayView.setSoftKeyListener(this);
        colseSoftKey.setOnClickListener(this);
        inputTypeGroup.setOnCheckedChangeListener(this);
        softKeyHeight = contentView.getMeasuredHeight();
        decorViewHeight = decorView.getHeight();
        setFocusable(true);
        setOutsideTouchable(false);
        setContentView(contentView);
        setBackgroundDrawable(new BitmapDrawable());
        setWidth(decorView.getWidth());
        setHeight(softKeyHeight);
        setAnimationStyle(android.R.style.Animation_InputMethod);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        inputTypeGroup.check(checkedId);
        updateViewByMode(mapViewModeById(checkedId));
    }


    /**
     * 更新显示的视图
     *
     * @param viewMode
     */
    public void updateViewByMode(int viewMode) {
        switch (viewMode) {
            case SoftKeyView.MODE_NUMBER:
                numLayView.setVisibility(View.VISIBLE);
                azLayView.setVisibility(View.INVISIBLE);
                break;
            case SoftKeyView.MODE_AZ:
                azLayView.setVisibility(View.VISIBLE);
                numLayView.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * 显示输入法
     */
    public void show() {
        if (edit==null) {
            new IllegalAccessError("safeEdit is null refrence");
        }
        inputStr = new StringBuilder(edit.getText().toString());
        showAsDropDown(decorView, 0, -getHeight());
        inputTypeGroup.check(mapCheckedIdByMode(getInputType(edit)));
        //updateViewByMode(getInputType(edit));
    }


    /**
     * 释放相关的资源
     */
    public void recycle() {
        edit = null;
        decorView = null;
    }

    /**
     * 关闭输入法
     */
    public void close() {
        if (isShowing()) {
            recycle();
            dismiss();
        }
    }

    /**
     * 重新刷新页面排版
     */
    public void updateViewDraw() {
        if (isPendding) {

        }
    }

    /**
     * 获取输入文本的类型
     *
     * @param edit
     * @return
     */
    public int getInputType(EditText edit) {
        return SoftKeyView.MODE_NUMBER;
    }

    /**
     * 映射出视图模式
     *
     * @return
     */
    public int mapViewModeById(int checkedId) {
        switch (checkedId) {
            case R.id.softBoard_Az:
                return SoftKeyView.MODE_AZ;
            case R.id.softBoard_Number:
                return SoftKeyView.MODE_NUMBER;
            default:
                return SoftKeyView.MODE_AZ;
        }
    }

    /**
     * 映射出视图模式
     *
     * @return
     */
    public int mapCheckedIdByMode(int viewMode) {
        switch (viewMode) {
            case SoftKeyView.MODE_AZ:
                return R.id.softBoard_Az;
            case SoftKeyView.MODE_NUMBER:
                return R.id.softBoard_Number;
            default:
                return R.id.softBoard_Az;
        }
    }

    public ViewTreeObserver.OnGlobalLayoutListener contetviewLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            // TODO Auto-generated method stub
            if (decorViewHeight == decorView.getHeight()) {
                Log.i(TAG, "updateViewDraw-->decorViewHeight=" + decorViewHeight);
                TranslateAnimation translate = new TranslateAnimation(0, 0, 0, softKeyHeight);
                translate.setDuration(200);
                translate.setFillAfter(true);
                decorView.startAnimation(translate);
            } else {
                Log.i(TAG, "updateViewDraw-->decorViewHeight=" + decorViewHeight);
                TranslateAnimation translate = new TranslateAnimation(0, 0, -softKeyHeight, 0);
                translate.setDuration(200);
                translate.setFillAfter(true);
                decorView.startAnimation(translate);
            }
        }

    };

    @Override
    public void onPressed(SoftKey softKey) {
        if (edit != null) {
            inputStr.append(softKey.getText());
            edit.setText(inputStr.toString());
            edit.setSelection(inputStr.length());
        }
    }

    @Override
    public void onDeleted() {
        if (edit != null && (!TextUtils.isEmpty(inputStr.toString()))) {
            edit.setText(inputStr.deleteCharAt(inputStr.length() - 1));
            edit.setSelection(inputStr.length());
        }
    }

    @Override
    public void onConfirm() {
            close();
    }


    public void setEdit(SafeEdit edit) {
        this.edit = edit;
    }


    public void setViewMode(int viewMode) {
        if (viewMode != this.viewMode) {
            this.viewMode = viewMode;
            updateViewByMode(viewMode);
        }
    }

    public boolean isPendding() {
        return isPendding;
    }

    public void setPendding(boolean isPendding) {
        this.isPendding = isPendding;
    }

    @Override
    public void onClick(View v) {

    }

}
