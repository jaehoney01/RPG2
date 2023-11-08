package org.techtown.cap2.util;

import android.app.Activity;

public interface PermissionCallback {
    public void onPermissionGranted(Activity activity, String permission, int number);
}
