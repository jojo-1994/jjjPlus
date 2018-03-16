package com.sz.jjj.updateapk;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sz.jjj.baselibrary.permissions.PermissionsManager;
import com.sz.jjj.baselibrary.permissions.PermissionsResultAction;
import com.sz.jjj.baselibrary.utils.AppUtils;
import com.sz.jjj.baselibrary.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jjj on 2017/8/29.
 *
 * @description:
 */

public class UpdateAppUtils {

    private static String URL = "http://issuecdn.baidupcs.com/issue/netdisk/apk/BaiduNetdisk_7.15.1.apk";

    /**
     * 后台更新
     *
     * @param activity
     */
    public static void updateBackground(Activity activity) {
        if (compareVersion(activity, 2)) {
            showUpdateDialog(activity);
        }
    }

    /**
     * 前台更新，弹出对话框
     *
     * @param activity
     */
    public static void updateForeground(final Activity activity) {
        // TODO: 2017/8/29 获取服务端版本号，是否有新版本
        if (compareVersion(activity, 2)) {
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, new PermissionsResultAction() {
                public void onGranted() {
                    showUpdateDialog(activity);
                }

                public void onDenied(String permission) {
                    ToastUtils.show(activity, "授权失败");
                }
            });

        }
    }

    private static void showUpdateDialog(final Activity activity) {
        MaterialDialog dialog = new MaterialDialog.Builder(activity)
                .progress(false, 100)
                .cancelable(false)
                .progressIndeterminateStyle(true)
                .canceledOnTouchOutside(false)
                .title("软件更新")
                .positiveText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(URL).build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(new File("/sdcard/wangshu.jpg"));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    Log.i("wangshu", e.toString());
                    e.printStackTrace();
                }

                Log.d("wangshu", "文件下载成功");
            }
        });

    }

    private static void downloadApk(final Context context, String app_name, String version, final String url) {

    }

    public static boolean compareVersion(Context context, int verCodeFromServer) {
        int verCodeFromApp = AppUtils.getVersionCode(context);
        return verCodeFromApp < verCodeFromServer ? true : false;
    }
}
