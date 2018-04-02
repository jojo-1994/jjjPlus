package com.sz.jjj.annotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sz.jjj.R;

import butterknife.BindView;

/**
 * Created by jjj on 2018/4/2.
 *
 * @description:
 */

//@ContentView(R.layout.annotation_activity)
public class AnnotationActivity extends AnnotationActivity2 {
    @BindView(R.id.textview)
    TextView textview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class c = this.getClass();
//        textview.setText("getClass:" + getClass().toString() + "\n\n" +
//                "getSuperclass:" + c.getSuperclass().toString() + "\n\n" +
//                "getGenericSuperclass:" + c.getGenericSuperclass().toString());
    }
}
