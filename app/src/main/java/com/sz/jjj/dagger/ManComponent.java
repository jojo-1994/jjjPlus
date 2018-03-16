package com.sz.jjj.dagger;

import dagger.Component;

/**
 * Created by jjj on 2018/2/8.
 *
 * @description:
 */

//@Singleton
@MyScope
@Component(modules = CarModule.class)
public interface ManComponent {
    void injectMan(Man man);
}