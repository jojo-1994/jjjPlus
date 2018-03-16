package com.sz.jjj.dagger;

import javax.inject.Inject;

/**
 * Created by jjj on 2018/2/8.
 *
 * @description:
 */

public class Car {

    String color = "red";

    @Inject
    public Car() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
