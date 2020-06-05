package com.sz.jjj.recyclerview.adapter;

import com.sz.jjj.R;
import com.sz.jjj.recyclerview.bean.MultiItemBean;
import com.sz.jjj.recyclerview.library.base.BaseViewHolder;
import com.sz.jjj.recyclerview.library.base.MultiItemBaseAdapter;

import java.util.List;

/**
 * Created by jjj on 2018/3/27.
 *
 * @description:
 */

public class MultiItemAdapter extends MultiItemBaseAdapter<MultiItemBean> {

    public MultiItemAdapter(List<MultiItemBean> datas) {
        super(datas);
        addItemType(MultiItemBean.TEXT, R.layout.recyclerview_text_item);
        addItemType(MultiItemBean.IMAGE, R.layout.recyclerview_image_item);
    }

    @Override
    public void convert(BaseViewHolder holder, MultiItemBean s) {
        switch (holder.getItemViewType()) {
            case MultiItemBean.TEXT:
                holder.setText(R.id.textview, s.getName());
                break;
            case MultiItemBean.IMAGE:
                holder.setImageResource(R.id.image, s.getResId());
                break;
            default:
        }
    }
}
