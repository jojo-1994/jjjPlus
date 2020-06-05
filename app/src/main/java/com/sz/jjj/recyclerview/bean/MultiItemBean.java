package com.sz.jjj.recyclerview.bean;

import com.sz.jjj.recyclerview.library.MultiItemTypeSupport;

/**
 * Created by jjj on 2018/3/28.
 *
 * @description:
 */

public class MultiItemBean implements MultiItemTypeSupport{


    private String name;
    private int resId;
    private int itemType;
    public static final int IMAGE=0x11;
    public static final int TEXT=0x12;

    public MultiItemBean(String name, int itemType) {
        this.name = name;
        this.itemType = itemType;
    }

    public MultiItemBean(int resId, int itemType) {
        this.resId = resId;
        this.itemType = itemType;
    }

    @Override
    public int getItemViewType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
