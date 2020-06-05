package com.sz.jjj.dagger;

import javax.inject.Inject;

/**
 * Created by jjj on 2018/2/24.
 *
 * @description:
 */

public final class ActivityModule {

    private String name;

    @Inject
    public ActivityModule(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }
}
