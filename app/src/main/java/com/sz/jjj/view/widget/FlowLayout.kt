package com.sz.jjj.view.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.sz.jjj.R

/**
 * Created by jjj on 2018/3/5.
@description: 流布局，自定义控件的三步走
 */
class FlowLayout : ViewGroup {

    // 存储行的集合，管理行
    private var mLines = ArrayList<Line>()

    // 竖直方向的间距
    private var vertical_space: Float = 0.0f;

    // 水平方向的间距
    private var horizontal_space: Float = 0.0f;

    // 当前行的指针
    private var mCurrentLine: Line? = null;

    // 行的最大宽度，除去边距的宽度
    private var mMaxWidth: Int = 0

    constructor(context: Context) : this(context, null) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        // 获取自定义属性
        var array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout)
        horizontal_space = array.getDimension(R.styleable.FlowLayout_width_space, 0f)
        vertical_space = array.getDimension(R.styleable.FlowLayout_height_space, 0f)
        array.recycle()
    }

    // 第一步：测量
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mLines.clear()
        mCurrentLine = null
        // 获取总宽度
        var width = MeasureSpec.getSize(widthMeasureSpec)
        // 计算最大宽度
        var maxWidth = width - paddingLeft - paddingRight
        for (i in 0..(childCount-1)) {
            var view = getChildAt(i)
            // 测量孩子
            measureChild(view, widthMeasureSpec, heightMeasureSpec)
            if (mCurrentLine == null) {
                // 添加第一个孩子时
                mCurrentLine = Line(maxWidth, horizontal_space)
                // 添加孩子
                mCurrentLine!!.addView(view)
                // 添加到行集合
                mLines.add(mCurrentLine!!)
            } else {
                // 行里有孩子,判断下能添加
                if (mCurrentLine!!.canAddView(view)) {
                    // 继续往行里添加
                    mCurrentLine!!.addView(view)
                } else {
                    // 转行添加
                    mCurrentLine = Line(maxWidth, horizontal_space)
                    mCurrentLine!!.addView(view)
                    mLines.add(mCurrentLine!!)
                }
            }
        }

        /*************测量自己*************/
        // 测量自己只需要测量高度，因为宽度是被充满的
        var height = paddingTop + paddingBottom
        for (i in mLines.indices) {
            // 所有行的高度
            height += mLines.get(i).height
        }
        // 所有竖直方向的间距
        height += (mLines.size - 1) * vertical_space.toInt()
        // 测量
        setMeasuredDimension(width, height)
    }

    // 第一步：布局
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 这里只负责高度的位置，具体宽度和孩子的位置交给具体的行去管理
        var l = paddingLeft
        var t = paddingTop
        for (i in mLines.indices) {
            // 获取行
            var line = mLines.get(i)
            // 如果是最后一行
            if (i == mLines.size - 1) {
                // 不适配宽度
                line.layout(t, l, true)
            } else {
                // 适配宽度，均匀占据行宽
                line.layout(t, l, false)
            }

            // 更新高度
            t += line.height
            if (i != mLines.size - 1) {
                // 不是最后一行就添加间距
                t += vertical_space.toInt()
            }

        }
    }

    // 第一步：绘制
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    // 内部类，行内管理器，管理每行的孩子
    class Line(var maxWidth: Int, var space: Float) {

        // 定义一个行内的集合用来存放子View
        var views = ArrayList<View>()
        // 使用宽度
        var useWidth = 0
        // 行高
        var height = 0

        /**
         * 往集合重添加孩子
         */
        fun addView(view: View) {
            var childWidth = view.measuredWidth
            var childHeight = view.measuredHeight
            // 添加第一个孩子
            if (views.size == 0) {
                // 如果孩子宽度大于最大宽度
                if (childWidth > maxWidth) {
                    useWidth = maxWidth
                    height = childHeight
                } else {
                    // 添加孩子
                    useWidth = childWidth
                    height = childHeight
                }
            } else {
                useWidth += childWidth + space.toInt()
                height = if (childHeight > height) childHeight else height
            }
            // 添加孩子到集合
            views.add(view)
        }

        /**
         * 判断当前行是否可以添加孩子
         */
        fun canAddView(view: View): Boolean {
            // 集合里没有数据可以添加
            if (views.size == 0) {
                return true
            }
            // 如果把这个孩子放入后的宽度大于最大宽度，则不可以添加
            if (useWidth + view.measuredWidth + space > maxWidth) {
                return false
            }
            return true
        }

        /**
         *指定孩子显示的位置
         * left:
         * top:
         * lastLine: 是否是最后一行
         */
        fun layout(t: Int, l: Int, lastLine: Boolean) {
            var left = l
            var top = t

            var avg = (maxWidth - useWidth) / views.size
            for (view in views) {
                var measureWidth = view.measuredWidth
                var measureHeight = view.measuredHeight
                // 如果是最后一行，实现右对齐；不过不是最后一行，实现两端对齐
                measureWidth = if (lastLine) measureWidth else measureWidth + avg
                // 重新测量
                view.measure(MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY))
                // 重新获取宽度值
                measureWidth = view.measuredWidth


                var right = left + measureWidth
                var bottom = top + measureHeight
                // 指定位置
                view.layout(left, top, right, bottom)

                left += measureWidth + space.toInt()
            }
        }
    }
}