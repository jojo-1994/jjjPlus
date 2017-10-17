package com.sz.jjj.keyboard.view;

/**
 * Created by jjj on 2017/10/11.
 *
 * @description:
 */

public interface SoftKeyStatusListener {
    public void onPressed(SoftKey softKey);

    public void onDeleted();

    public void onConfirm();

}
