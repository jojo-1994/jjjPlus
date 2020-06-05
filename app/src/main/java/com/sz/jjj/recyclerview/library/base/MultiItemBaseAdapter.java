package com.sz.jjj.recyclerview.library.base;

import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.sz.jjj.recyclerview.library.MultiItemTypeSupport;

import java.util.List;

/**
 * Created by jjj on 2018/3/27.
 *
 * @description:
 */

public abstract class MultiItemBaseAdapter<T> extends BaseAdapter<T> {


    private SparseArray<Integer> layouts;

    public MultiItemBaseAdapter(List<T> datas) {
        super(-1, datas);
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mDatas.get(position);
        if (item instanceof MultiItemTypeSupport) {
            return ((MultiItemTypeSupport)item).getItemViewType();
        }
        return 0;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = layouts.get(viewType);
        BaseViewHolder holder = BaseViewHolder.get(parent, layoutId);
        return holder;
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseArray<>();
        }
        layouts.put(type, layoutResId);
    }

}
