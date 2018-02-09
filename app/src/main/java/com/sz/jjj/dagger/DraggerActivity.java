package com.sz.jjj.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

/**
 * Created by jjj on 2018/2/8.
 *
 * @description:
 */

public class DraggerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Man man=new Man();
        Log.e("dddd", man.getCarColor());
//        Log.e("dddd", man.getCar1Color());
        List<Car> list= man.makeCars();
        for(Car car: list){
//            Log.e("dddd", car.getColor());
        }
    }
}
