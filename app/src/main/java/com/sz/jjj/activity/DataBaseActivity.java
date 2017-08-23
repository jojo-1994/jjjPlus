package com.sz.jjj.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.litesuits.orm.LiteOrm;

/**
 * Created by jjj on 2017/8/21.
 *
 * @description:
 */

public class DataBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LiteOrm liteOrm = null;
        String DB_NAME = "sport_medicine.db";
        if (liteOrm == null) {
            liteOrm = LiteOrm.newCascadeInstance(this, DB_NAME);
            liteOrm.setDebugged(false);
//            Log.e(DB_NAME, "createDb");
        }

        liteOrm.queryCount(String.class);
        liteOrm.close();
    }
}
