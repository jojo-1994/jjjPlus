package com.sz.jjj.anim.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sz.jjj.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author:jjj
 * @data:2018/8/24
 * @description:
 */

public class LayoutAnimationActivity extends AppCompatActivity {
    @BindView(R.id.listview)
    ListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_layout_anmition_activity);
        ButterKnife.bind(this);

        List<HashMap<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("text1", "name" + i);
            hashMap.put("text2", "content" + i);
            list.add(hashMap);
        }
        MyAdapter myAdapter = new MyAdapter(this, list);
        listview.setAdapter(myAdapter);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(2f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        listview.setAnimation(animation);
    }

    class MyAdapter extends BaseAdapter {

        private Context mContext;
        private List<HashMap<String, String>> list;

        public MyAdapter(Context context, List<HashMap<String, String>> datas) {
            this.mContext = context;
            this.list = datas;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(android.R.layout.simple_list_item_2, null);
                viewHolder.text1 = view.findViewById(android.R.id.text1);
                viewHolder.text2 = view.findViewById(android.R.id.text2);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            HashMap<String, String> map = list.get(position);
            viewHolder.text1.setText(map.get("text1"));
            viewHolder.text2.setText(map.get("text2"));
            return view;
        }
    }

    class ViewHolder {
        TextView text1, text2;
    }

}
