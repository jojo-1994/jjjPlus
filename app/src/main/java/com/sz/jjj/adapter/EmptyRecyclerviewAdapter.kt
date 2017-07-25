package com.sz.jjj.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sz.jjj.R
import com.sz.jjj.baselibrary.utils.ToastUtils


/**
 * Created by jjj on 2017/7/25.
@description:
 */
class EmptyRecyclerviewAdapter(var context: Context) : RecyclerView.Adapter<EmptyRecyclerviewAdapter.ViewHolder>() {

    val mData = ArrayList<String>();

    fun addData(){
        mData.add("test")
        notifyItemInserted(mData.size)
    }

    fun deleteData(){
        if(mData.size==0){
            ToastUtils.show(context, "没有数据了")
            return
        }
        mData.removeAt(mData.size - 1)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.mButton.setText(mData.get(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mButton: Button

        init {
            mButton = itemView.findViewById(R.id.text) as Button
        }
    }
}