package com.sz.jjj.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by jjj on 2018/2/8.
 *
 * @description:
 */

public class DaggerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Man man = new Man();

        Log.e("dddd", man.getCarColor());
//      Qualifier（限定符）
        Log.e("dddd", man.getCarColor2());
//      Lazy （延迟注入）
        Log.e("dddd", man.getCarColor3());
//      Provider 注入
        man.makeCar(2);

//      @BindsInstance
        ActivityComponent component = DaggerActivityComponent.builder().setName("dagger").build();
        ActivityModule module = component.getActivityModule();
        Log.e("dddd", module.getName());
    }
}
