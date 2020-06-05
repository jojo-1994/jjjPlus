package com.sz.jjj.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sz.jjj.R;
import com.sz.jjj.util.ScreenUtils;
import com.sz.jjj.view.widget.HorizontalScrollViewEx;

import java.util.ArrayList;

/**
 * Created by jiangjiaojiao on 2018/7/25.
 */

public class ScrollActivity extends Activity {

    private HorizontalScrollViewEx mListContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_scroll_activity);
    }

    private void initView(){
        LayoutInflater inflater=getLayoutInflater();
        mListContainer=findViewById(R.id.container);
        final int screenWidth= ScreenUtils.getScreenWidth(this);
        final int screenHeight= ScreenUtils.getScreenHeight(this);
        for(int i=0;i<3;i++){
            ViewGroup layout= (ViewGroup) inflater.inflate(R.layout.view_content_layout,
                    mListContainer, false);
            layout.getLayoutParams().width=screenWidth;
            TextView textView=layout.findViewById(R.id.title);
            textView.setText("page"+(i+1));
            layout.setBackgroundColor(Color.rgb((255/(i+1)), (255/(i+1)),(255/(i+1))));
            createList(layout);
            mListContainer.addView(layout);
        }

    }

    private void createList(ViewGroup layout){
        ListView listView=layout.findViewById(R.id.list);
        ArrayList<String> datas=new ArrayList<>();
        for(int i=0;i<50;i++){
            datas.add("name "+i);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                R.layout.view_content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
    }
}
