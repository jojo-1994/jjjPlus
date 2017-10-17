package com.sz.jjj.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sz.jjj.R;
import com.sz.jjj.baselibrary.permissions.PermissionsManager;
import com.sz.jjj.baselibrary.permissions.PermissionsResultAction;
import com.sz.jjj.util.UpdateAppUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj on 2017/8/29.
 *
 * @description:
 */

public class UpdateApkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_apk);
        ButterKnife.bind(this);

        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                Toast.makeText(UpdateApkActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String var1) {
                Toast.makeText(UpdateApkActivity.this, "权限被拒绝", Toast.LENGTH_SHORT).show();

            }
        });
    }



    @OnClick({R.id.btn_update_foreground, R.id.btn_update_background, R.id.btn_update_thirdparty, R.id.btn_update_browser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_update_foreground:
                UpdateAppUtils.updateForeground(this);
                break;
            case R.id.btn_update_background:

                break;
            case R.id.btn_update_thirdparty:

                break;
            case R.id.btn_update_browser:

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
