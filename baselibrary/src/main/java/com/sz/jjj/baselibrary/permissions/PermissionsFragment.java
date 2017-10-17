package com.sz.jjj.baselibrary.permissions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.sz.jjj.baselibrary.R;


/**
 * Created by Chne on 2017/8/29.
 */

public class PermissionsFragment extends Fragment {
    private final int PERMISSIONS_REQUEST_CODE = 42;
    private final int DIALOG_REQUEST_CODE = 41;

    private OnPermissionsListener listener;

    private String[] PERMISSIONS;//未获取权限
    private boolean isShowDialog;//是否显示提示Dialog
    private boolean dialogCanCancel;//dialog是否可以取消

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    /**
     * 显示 权限提示Dialog
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.help);
        builder.setMessage(R.string.string_help_text);
        builder.setCancelable(dialogCanCancel);

        // 取消
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null)
                    listener.result(XPermission.lacksPermissions(getActivity(), PERMISSIONS));
            }
        });

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings(getActivity());
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (listener != null)
                    listener.result(XPermission.lacksPermissions(getActivity(), PERMISSIONS));
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (listener != null)
                    listener.result(XPermission.lacksPermissions(getActivity(), PERMISSIONS));
            }
        });
        builder.show();
    }

    // 启动应用的设置
    private void startAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        startActivityForResult(intent, DIALOG_REQUEST_CODE);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PERMISSIONS_REQUEST_CODE == requestCode && listener != null) {
            String[] tempPermission = XPermission.lacksPermissions(getActivity(), PERMISSIONS);
            if (isShowDialog && tempPermission.length > 0)
                showMissingPermissionDialog();
            else
                listener.result(tempPermission);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DIALOG_REQUEST_CODE && listener != null)
            listener.result(XPermission.lacksPermissions(getActivity(), PERMISSIONS));
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(@NonNull String[] permissions) {
        PERMISSIONS = permissions;
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
    }

    public void setListener(OnPermissionsListener listener) {
        this.listener = listener;
    }

    public void setDialog(boolean isShowDialog, boolean dialogCanCancel) {

        this.isShowDialog = isShowDialog;
        this.dialogCanCancel = dialogCanCancel;
    }
}
