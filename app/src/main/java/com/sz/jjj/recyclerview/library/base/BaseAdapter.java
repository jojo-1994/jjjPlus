package com.sz.jjj.recyclerview.library.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jjj on 2018/3/27.
 *
 * @description:
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected int mLayoutId;
    protected List<T> mDatas;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public BaseAdapter(int layoutId, List<T> datas) {
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        BaseViewHolder viewHolder = BaseViewHolder.get(parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.updatePosition(position);
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(BaseViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

}
