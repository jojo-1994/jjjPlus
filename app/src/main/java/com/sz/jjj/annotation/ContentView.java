package com.sz.jjj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jjj on 2018/4/2.
 *
 * @description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentView {
    //属性叫 value ，在使用时可以直接传参数即可，不必显式的指明键值对，是一种快捷方法
    int value() ;
}
