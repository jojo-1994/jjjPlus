package com.sz.jjj.dagger;

import android.util.Log;

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
    Car car;

    @Inject
    Lazy<Car> lazyCar;

    @Inject
    Provider<Car> carProvider;

    @Inject
    @Named("car1")
    Car carQualifier;

    public Man() {
        DaggerManComponent.create().injectMan(this);
    }

    public String getCarColor() {
        return car.getColor();
    }

    public String getCarColor2() {
        return carQualifier.getColor();
    }

    public String getCarColor3() {
        return lazyCar.get().getColor();
    }

    public List<Car> makeCar(int num) {
        List<Car> carList = new ArrayList(num);
        for (int i = 0; i < num; i++) {
            carList.add(carProvider.get());
        }
        Log.e("ddd", carList.get(0).hashCode()+"");
        Log.e("ddd", carList.get(1).hashCode()+"");
        Log.e("ddd", carList.get(1).getColor());
        carList.get(0).setColor("blue");
        Log.e("ddd", carList.get(1).getColor());
        return carList;
    }
}
