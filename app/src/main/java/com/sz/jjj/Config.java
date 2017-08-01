package com.sz.jjj;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jjj on 2017/8/1.
 *
 * @description:
 */

public class Config {


    public static final String TAG = "jjjPlus";
    public static final String ACTION_MyACCESSITITLITY_SERVICE_CONNECT = "com.sz.jjj.ACCESSBILITY_CONNECT";
    public static final String KEY_NOTIFY_NIGHT_ENABLE = "KEY_NOTIFY_NIGHT_ENABLE";
    public static final String KEY_NOTIFY_SOUND = "KEY_NOTIFY_SOUND";
    public static final String KEY_NOTIFY_VIBRATE = "KEY_NOTIFY_VIBRATE";
    public static final String PREFERENCE_NAME = "config";

    private SharedPreferences preferences;
    private Context mContext;
    private static Config current;

    private Config(Context context) {
        mContext = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized Config getConfig(Context context) {
        if(current == null) {
            current = new Config(context.getApplicationContext());
        }
        return current;
    }

    /** 是否开启夜间免打扰模式*/
    public boolean isNotifyNight() {
        return preferences.getBoolean(KEY_NOTIFY_NIGHT_ENABLE, false);
    }

    /** 是否开启声音*/
    public boolean isNotifySound() {
        return preferences.getBoolean(KEY_NOTIFY_SOUND, true);
    }

    /** 是否开启震动*/
    public boolean isNotifyVibrate() {
        return preferences.getBoolean(KEY_NOTIFY_VIBRATE, true);
    }
}
