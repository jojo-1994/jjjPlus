package com.sz.jjj.dagger;

import javax.inject.Inject;

/**
 * Created by jjj on 2018/2/8.
 *
 * @description:
 */

public class Car {
    @Inject
    public Car() {}

    public String getColor(){
        return "red";
    }
}
