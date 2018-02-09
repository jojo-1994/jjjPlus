package com.sz.jjj.dagger;

import dagger.Component;

/**
 * Created by jjj on 2018/2/8.
 *
 * @description:
 */

@Component
public interface ManComponent {
    void injectMan(Man man);
}