package com.sz.jjj.annotation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;

/**
 * Created by jjj on 2018/4/2.
 *
 * @description:
 */
public abstract class AnnotationBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        annotationProcess();
    }

    //读取注解，进行处理
    private void annotationProcess() {
        Class c = this.getClass();
        Log.e("dddd", "getClass:" + getClass().toString() + "\n\n" +
                "getSuperclass:" + c.getSuperclass().toString() + "\n\n" +
                "getGenericSuperclass:" + c.getGenericSuperclass().toString());

        //遍历所有子类
        for (; c != Context.class; c = c.getSuperclass()) {
            //找到使用 ContentView 注解的类
            ContentView annotation = (ContentView) c.getAnnotation(ContentView.class);
            if (annotation != null) {
                try { //有可能出错的地方都要 try-catch
                    //获取 注解中的属性值，为 Activity 设置布局
                    this.setContentView(annotation.value());
                    ButterKnife.bind(this);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                return;
            }

        }
    }
}
