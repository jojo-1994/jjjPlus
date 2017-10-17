package com.sz.jjj.keyboard.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.sz.jjj.R;


/**
 * 字母键盘
 */
public class SoftKeyAzLayView extends SoftKeyView {


    /**
     * 数字键盘的分布4行 -10列-9列-9列(包含2个其他的按钮)
     */
    private int OneCol = 10;
    private int twoCol = 9;
    private int row = 4;
    private SoftKey capsLockBtn;
    private SoftKey[] punctKeys;


    public SoftKeyAzLayView(Context context) {
        this(context, null);
    }

    public SoftKeyAzLayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoftKeyAzLayView(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public SoftKey[] initSoftKeys() {
        SoftKey[] result = new SoftKey[26];
        char cha = 'a';
        for (int index = 0; index < result.length; index++, cha++) {
            SoftKey sofkey = new SoftKey();
            sofkey.setText(String.valueOf(cha));
            result[index] = sofkey;
        }
        punctKeys = initPunctKeys();

        return result;
    }

    @Override
    public SoftKey[] measureSoftKeysPos(SoftKey[] softKeys) {
        if (softKeys == null) {
            return null;
        }

        //第一行10个字符按钮
        for (int index = 0; index < OneCol; index++) {
            softKeys[index].setX(blockWidth / 2 + (index % OneCol) * blockWidth);
            softKeys[index].setY(blockHeight / 2);
            softKeys[index].setWidth(blockWidth);
            softKeys[index].setHeight(blockHeight);
        }

        //第二行9个字符按钮
        for (int index = OneCol; index < OneCol + twoCol; index++) {
            softKeys[index].setX((index - (OneCol - 1)) * blockWidth);
            softKeys[index].setY(blockHeight * 3 / 2);
            softKeys[index].setWidth(blockWidth);
            softKeys[index].setHeight(blockHeight);
        }

        //第三行7个字符按钮
        for (int index = OneCol + twoCol; index < softKeys.length; index++) {
            softKeys[index].setX((index - (OneCol + twoCol - 1) + 1) * blockWidth);
            softKeys[index].setY(blockHeight * 5 / 2);
            softKeys[index].setWidth(blockWidth);
            softKeys[index].setHeight(blockHeight);
        }

        if (capsLockBtn == null) {
            capsLockBtn = new SoftKey();
            capsLockBtn.setWidth(blockWidth * 3 / 2);
            capsLockBtn.setKeyType(SoftKey.KeyType.ICON);
            capsLockBtn.setIcon(R.mipmap.ic_softkey_uncapslock);
            capsLockBtn.setX(blockWidth * 3 / 4);
            capsLockBtn.setY(blockHeight * 5 / 2);
            capsLockBtn.setHeight(blockHeight);
        }

        if (delBtn == null) {
            delBtn = new SoftKey();
            delBtn.setWidth(blockWidth * 3 / 2);
            delBtn.setKeyType(SoftKey.KeyType.ICON);
            delBtn.setIcon(R.mipmap.ic_softkey_delete);
            delBtn.setX(getWidth() - blockWidth * 3 / 4);
            delBtn.setY(blockHeight * 5 / 2);
            delBtn.setHeight(blockHeight);
        }

        for (int index = 0; index < punctKeys.length; index++) {
            punctKeys[index].setX(index * punctKeys[index].getWidth() + (blockWidth * 3 / 4));
            punctKeys[index].setY(row * blockHeight - blockHeight / 2);
        }

        if (confirmBtn == null) {
            confirmBtn = new SoftKey();
            confirmBtn.setWidth(blockWidth * 5 / 2);
            confirmBtn.setText("确定");
            confirmBtn.setX(getWidth() - blockWidth * 5 / 4);
            confirmBtn.setY(blockHeight * 7 / 2);
            confirmBtn.setHeight(blockHeight);
        }

        return softKeys;
    }

    @Override
    public int measureBlockWidth(int keyBoardwidth) {
        return keyBoardwidth / OneCol;
    }

    @Override
    public int measureBlockHeight(int keyBoardHeight) {
        return keyBoardHeight / row;
    }

    @Override
    public void drawSoftKeysPos(Canvas canvas, SoftKey[] softKeys) {
        if (softKeys == null) {
            return;
        }

        for (int index = 0; index < softKeys.length; index++) {
            drawSoftKey(canvas, softKeys[index]);
        }

        for (int index = 0; index < punctKeys.length; index++) {
            drawSoftKey(canvas, punctKeys[index]);
        }

        drawSoftKey(canvas, capsLockBtn);
        drawSoftKey(canvas, delBtn);
        drawSoftKey(canvas, confirmBtn);

        canvas.drawLine(0, 0, getWidth(), 0, keyBorderPaint);
        canvas.drawLine(0, blockHeight, getWidth(), blockHeight, keyBorderPaint);
        canvas.drawLine(0, blockHeight * (row - 2), getWidth(), blockHeight * (row - 2), keyBorderPaint);
        canvas.drawLine(0, blockHeight * (row - 1), getWidth(), blockHeight * (row - 1), keyBorderPaint);
        for (int index = 0; index < OneCol; index++) {
            int x = (index + 1) * blockWidth;
            canvas.drawLine(x, 0, x, blockHeight, keyBorderPaint);
        }

        for (int index = 0; index <= twoCol; index++) {
            int x = index * blockWidth + blockWidth / 2;
            canvas.drawLine(x, blockHeight, x, blockHeight * 2, keyBorderPaint);
        }

        for (int index = 0; index <= softKeys.length - twoCol - OneCol; index++) {
            int x = (index + 1) * blockWidth + blockWidth / 2;
            canvas.drawLine(x, blockHeight * 2, x, blockHeight * 3, keyBorderPaint);
        }

        for (int index = 0; index < 5; index++) {
            int x = (index + 1) * blockWidth * 3 / 2;
            canvas.drawLine(x, blockHeight * 3, x, blockHeight * 4, keyBorderPaint);
        }


//        for (int index = 0; index < softKeys.length; index++) {
//            if (softKeys[index].isPreessed())
//                drawSoftKeyPressBg(canvas, softKeys[index]);
//        }

    }

    @Override
    public boolean handleKeyTouching(int eventX, int eventY, int action) {
        boolean needRefresh = super.handleKeyTouching(eventX, eventY, action);
        if (!needRefresh) {
            if (capsLockBtn.inRange(eventX, eventY)) {
                setKeyPressed(capsLockBtn);
                return true;
            }
        }
        return needRefresh;
    }

    private boolean isUpperCase = true;

    @SuppressLint("DefaultLocale")
    @Override
    public boolean handleTouchUp(int eventX, int eventY, int action) {
        boolean isDeal = super.handleTouchUp(eventX, eventY, action);
        if (!isDeal) {
            if (capsLockBtn.inRange(eventX, eventY)) {
                for (int index = 0; index < softKeys.length; index++) {
                    String txt = softKeys[index].getText();
                    txt = isUpperCase ? txt.toUpperCase() : txt.toLowerCase();
                    softKeys[index].setText(txt);
                }
                isUpperCase = !isUpperCase;
                isDeal = true;
            }
        }
        return isDeal;
    }

    /**
     * 初始化指定的标点字符
     *
     * @return
     */
    public SoftKey[] initPunctKeys() {
        String puncts = "@#*./";
        SoftKey[] result = new SoftKey[puncts.length()];
        for (int index = 0; index < result.length; index++) {
            SoftKey softkey = new SoftKey();
            softkey.setText(String.valueOf(puncts.charAt(index)));
            softkey.setWidth(blockWidth * 3 / 2);
            softkey.setHeight(blockHeight);
            result[index] = softkey;
        }

        return result;
    }

    @Override
    public SoftKey obtainTouchSoftKey(int eventX, int eventY) {
        SoftKey softey = super.obtainTouchSoftKey(eventX, eventY);
        if (softey == null) {
            for (int index = 0; index < punctKeys.length; index++) {
                if (punctKeys[index].inRange(eventX, eventY)) {
                    return punctKeys[index];
                }
            }
        }
        return softey;
    }

}
