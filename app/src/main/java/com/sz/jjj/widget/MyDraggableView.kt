package com.sz.jjj.widget

import android.content.Context
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * Created by jjj on 2018/1/2.
@description:
 */
class MyDraggableView : RelativeLayout {

    lateinit var viewDragHelper : ViewDragHelper

    constructor(context: Context, attr: AttributeSet) : super(context, attr){
        initView()
    }

    fun initView(){
//        viewDragHelper = ViewDragHelper.create(this, 1.0f, cb: DraggableViewCallback(this));
    }
}