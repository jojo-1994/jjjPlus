package com.sz.jjj.view.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.sz.jjj.R;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by jjj on 2017/10/16.
 *
 * @description:
 */

public class KeyboardUtil {


    private Activity mActivity;
    private KeyboardView mKeyboardView;
    private Keyboard mNumberKeyboard; // 数字键盘
    private Keyboard mLetterKeyboard; // 字母键盘

    private boolean isNumber = true;  // 是否数字键盘
    private boolean isUpper = false;   // 是否大写
    private EditText mEditText;

    public KeyboardUtil(Activity activity, EditText... editTexts) {
        mActivity = activity;
        mEditText = editTexts[0];

        initEditText(editTexts);

        mNumberKeyboard = new Keyboard(mActivity, R.xml.soft_keyboard_numbers);
        mLetterKeyboard = new Keyboard(mActivity, R.xml.soft_keyboard_qwerty);
        mKeyboardView = (KeyboardView) mActivity.findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(mNumberKeyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(true);
        mKeyboardView.setOnKeyboardActionListener(listener);
    }

    /**
     * 初始化editText
     *
     * @param editTexts
     */
    private void initEditText(final EditText... editTexts) {
        setShowSoftInputOnFocus(editTexts);
        for (final EditText editText : editTexts) {
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mEditText = editText;
                    showKeyboard();
                    return false;
                }
            });
        }
    }

    /**
     * 通过反射完成隐藏软键盘并显示光标的效果
     *
     * @param editTexts
     */
    private void setShowSoftInputOnFocus(EditText... editTexts) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {//4.0以下
            for (EditText editText : editTexts) {
                editText.setInputType(InputType.TYPE_NULL);
            }
        } else {
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                for (EditText editText : editTexts) {
                    setShowSoftInputOnFocus.invoke(editText, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = mEditText.getText();
            int start = mEditText.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) { // cancel
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideKeyboard();
                    }
                }, 300);
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) { // 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) { // 大小写切换
                changeKeyboart();
                mKeyboardView.setKeyboard(mLetterKeyboard);
            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) { // 数字与字母键盘互换
                if (isNumber) {
                    isNumber = false;
                    mKeyboardView.setKeyboard(mLetterKeyboard);
                } else {
                    isNumber = true;
                    mKeyboardView.setKeyboard(mNumberKeyboard);
                }

            } else { // 输入键盘值
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    private void changeKeyboart() {
        Drawable capsLock = ContextCompat.getDrawable(mActivity, R.drawable.soft_keyboard_btn_capslock);
        Drawable unCapsLock = ContextCompat.getDrawable(mActivity, R.drawable.soft_keyboard_btn_uncapslock);
        List<Keyboard.Key> keyList = mLetterKeyboard.getKeys();
        for (Keyboard.Key key : keyList) {
            if (key.label != null && isLetter(key.label.toString())) {
                key.label = isUpper ? key.label.toString().toLowerCase() : key.label.toString().toUpperCase();
                key.codes[0] = isUpper ? key.codes[0] + 32 : key.codes[0] - 32;
            } else if (key.codes[0] == Keyboard.KEYCODE_SHIFT && key.icon != null) {
                key.icon = isUpper ? unCapsLock : capsLock;
            }
        }
        isUpper = !isUpper;
    }

    /**
     * 判断是否是字母
     */
    private boolean isLetter(String str) {
        String wordStr = "abcdefghijklmnopqrstuvwxyz";
        return wordStr.contains(str.toLowerCase());
    }

    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.INVISIBLE);
        }
    }

    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

}
