package com.sz.jjj.dagger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import dagger.Lazy;

/**
 * Created by jjj on 2018/2/8.
 *
 * @description:
 */

public class Man {
    @Inject
    Lazy<Car> lazyCar;

    @Inject
    Car car;

    @Inject
    @Named("car1")
    Car1 car1;

    @Inject
    Provider<Car> providerCar;

    public Man() {
        DaggerManComponent.create().injectMan(this);
    }

    public String getCarColor() {
        return lazyCar.get().getColor();
    }

    public List<Car> makeCars() {
        List<Car> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(providerCar.get());
        }
        return list;
    }

    public String getCar1Color() {
        return car1.getColor();
    }
}
