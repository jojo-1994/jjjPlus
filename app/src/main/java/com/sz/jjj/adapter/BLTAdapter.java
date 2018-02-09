package com.sz.jjj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jjj on 2018/2/5.
 *
 * @description:
 */

public class BLTAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> strArr = new ArrayList<>();

    public BLTAdapter(Context context, ArrayList<String> strArr) {
        this.context = context;
        this.strArr = strArr;
    }

    @Override
    public int getCount() {
        return strArr.size();
    }

    @Override
    public Object getItem(int i) {
        return strArr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        if (strArr.get(i) != null) {
            ((TextView) view).setText(strArr.get(i));
        }
//        BluetoothDevice device = strArr.get(i);
//        ((TextView) view).setText(device.getName() + "-----" + device.getAddress() + (device.getBondState() == BluetoothDevice.BOND_BONDED ? "已绑定" : "未绑定"));
        return view;
    }
}

