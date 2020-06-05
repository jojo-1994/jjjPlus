package com.sz.jjj.dagger;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

/**
 * Created by jjj on 2018/2/9.
 *
 * @description:
 */

@Module
public class CarModule {

    @Provides
    @Named("car1")
    static Car provideCar1() {
        return new Car1();
    }

    @Provides
    @Named("car2")
    static Car provideCar2() {
        return new Car2();
    }

//    @MyScope
//    @Singleton
    @Reusable
    @Provides
    static Car provideCar() {
        return new Car();
    }

    @Inject
    public CarModule(String color){
    }
}
