package com.sz.jjj.baselibrary.permissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by jjj on 2017/8/29.
 *
 * @description:
 */

public class PermissionsManager {
    private static final String TAG = PermissionsManager.class.getSimpleName();
    private final Set<String> mPendingRequests = new HashSet(1);
    private final Set<String> mPermissions = new HashSet(1);
    private final List<WeakReference<PermissionsResultAction>> mPendingActions = new ArrayList(1);
    private static PermissionsManager mInstance = null;

    public static PermissionsManager getInstance() {
        if (mInstance == null) {
            mInstance = new PermissionsManager();
        }

        return mInstance;
    }

    private PermissionsManager() {
        this.initializePermissionsMap();
    }

    private synchronized void initializePermissionsMap() {
        Field[] fields = Manifest.permission.class.getFields();
        Field[] var2 = fields;
        int var3 = fields.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[var4];
            String name = null;

            try {
                name = (String) field.get("");
            } catch (IllegalAccessException var8) {
                Log.e(TAG, "Could not access field", var8);
            }

            this.mPermissions.add(name);
        }

    }

    @SuppressLint("WrongConstant")
    @NonNull
    private synchronized String[] getManifestPermissions(@NonNull Activity activity) {
        PackageInfo packageInfo = null;
        ArrayList list = new ArrayList(1);

        try {
            Log.d(TAG, activity.getPackageName());
            packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 4096);
        } catch (PackageManager.NameNotFoundException var9) {
            Log.e(TAG, "A problem occurred when retrieving permissions", var9);
        }

        if (packageInfo != null) {
            String[] permissions = packageInfo.requestedPermissions;
            if (permissions != null) {
                String[] var5 = permissions;
                int var6 = permissions.length;

                for (int var7 = 0; var7 < var6; ++var7) {
                    String perm = var5[var7];
                    Log.d(TAG, "Manifest contained permission: " + perm);
                    list.add(perm);
                }
            }
        }

        return (String[]) list.toArray(new String[list.size()]);
    }

    private synchronized void addPendingAction(@NonNull String[] permissions, @Nullable PermissionsResultAction action) {
        if (action != null) {
            action.registerPermissions(permissions);
            this.mPendingActions.add(new WeakReference(action));
        }
    }

    private synchronized void removePendingAction(@Nullable PermissionsResultAction action) {
        Iterator iterator = this.mPendingActions.iterator();

        while (true) {
            WeakReference weakRef;
            do {
                if (!iterator.hasNext()) {
                    return;
                }

                weakRef = (WeakReference) iterator.next();
            } while (weakRef.get() != action && weakRef.get() != null);

            iterator.remove();
        }
    }

    public synchronized boolean hasPermission(@Nullable Context context, @NonNull String permission) {
        return context != null && (ActivityCompat.checkSelfPermission(context, permission) == 0 || !this.mPermissions.contains(permission));
    }

    public synchronized boolean hasAllPermissions(@Nullable Context context, @NonNull String[] permissions) {
        if (context == null) {
            return false;
        } else {
            boolean hasAllPermissions = true;
            String[] var4 = permissions;
            int var5 = permissions.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String perm = var4[var6];
                hasAllPermissions &= this.hasPermission(context, perm);
            }

            return hasAllPermissions;
        }
    }

    public synchronized void requestAllManifestPermissionsIfNecessary(@Nullable Activity activity, @Nullable PermissionsResultAction action) {
        if (activity != null) {
            String[] perms = this.getManifestPermissions(activity);
            this.requestPermissionsIfNecessaryForResult(activity, perms, action);
        }
    }

    public synchronized void requestPermissionsIfNecessaryForResult(@Nullable Activity activity, @NonNull String[] permissions, @Nullable PermissionsResultAction action) {
        if (activity != null) {
            this.addPendingAction(permissions, action);
            if (Build.VERSION.SDK_INT < 23) {
                this.doPermissionWorkBeforeAndroidM(activity, permissions, action);
            } else {
                List<String> permList = this.getPermissionsListToRequest(activity, permissions, action);
                if (permList.isEmpty()) {
                    this.removePendingAction(action);
                } else {
                    String[] permsToRequest = (String[]) permList.toArray(new String[permList.size()]);
                    this.mPendingRequests.addAll(permList);
                    ActivityCompat.requestPermissions(activity, permsToRequest, 1);
                }
            }

        }
    }

    public synchronized void requestPermissionsIfNecessaryForResult(@NonNull Fragment fragment, @NonNull String[] permissions, @Nullable PermissionsResultAction action) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            this.addPendingAction(permissions, action);
            if (Build.VERSION.SDK_INT < 23) {
                this.doPermissionWorkBeforeAndroidM(activity, permissions, action);
            } else {
                List<String> permList = this.getPermissionsListToRequest(activity, permissions, action);
                if (permList.isEmpty()) {
                    this.removePendingAction(action);
                } else {
                    String[] permsToRequest = (String[]) permList.toArray(new String[permList.size()]);
                    this.mPendingRequests.addAll(permList);
                    fragment.requestPermissions(permsToRequest, 1);
                }
            }

        }
    }

    public synchronized void notifyPermissionsChange(@NonNull String[] permissions, @NonNull int[] results) {
        int size = permissions.length;
        if (results.length < size) {
            size = results.length;
        }

        Iterator iterator = this.mPendingActions.iterator();

        while (true) {
            while (iterator.hasNext()) {
                PermissionsResultAction action = (PermissionsResultAction) ((WeakReference) iterator.next()).get();

                for (int n = 0; n < size; ++n) {
                    if (action == null || action.onResult(permissions[n], results[n])) {
                        iterator.remove();
                        break;
                    }
                }
            }

            for (int n = 0; n < size; ++n) {
                this.mPendingRequests.remove(permissions[n]);
            }

            return;
        }
    }

    private void doPermissionWorkBeforeAndroidM(@NonNull Activity activity, @NonNull String[] permissions, @Nullable PermissionsResultAction action) {
        String[] var4 = permissions;
        int var5 = permissions.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String perm = var4[var6];
            if (action != null) {
                if (!this.mPermissions.contains(perm)) {
                    action.onResult(perm, Permissions.NOT_FOUND);
                } else if (ActivityCompat.checkSelfPermission(activity, perm) != 0) {
                    action.onResult(perm, Permissions.DENIED);
                } else {
                    action.onResult(perm, Permissions.GRANTED);
                }
            }
        }

    }

    @NonNull
    private List<String> getPermissionsListToRequest(@NonNull Activity activity, @NonNull String[] permissions, @Nullable PermissionsResultAction action) {
        List<String> permList = new ArrayList(permissions.length);
        String[] var5 = permissions;
        int var6 = permissions.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            String perm = var5[var7];
            if (!this.mPermissions.contains(perm)) {
                if (action != null) {
                    action.onResult(perm, Permissions.NOT_FOUND);
                }
            } else if (ActivityCompat.checkSelfPermission(activity, perm) != 0) {
                if (!this.mPendingRequests.contains(perm)) {
                    permList.add(perm);
                }
            } else if (action != null) {
                action.onResult(perm, Permissions.GRANTED);
            }
        }

        return permList;
    }
}
