package org.techtown.cap2.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.techtown.cap2.view.MainActivity;

/**
 * 권한 요청 유틸
 */
public class PermissionUtil {
    /**
     * checkPermission targetsdk 33 올리면서 만든 함수
     * sdk 33에서만 작동하는 함수입니다.
     * 권한 하나만 요청가능.
     *
     * @author 김성민
     * @since 2023/06/30 4:26 PM
     *
     * @param activity 액티비티
     */

    public static void checkPermission(Activity activity, String[] permissions, int requestCode, PermissionCallback permissionCallback) {
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permissions[i]) == PackageManager.PERMISSION_GRANTED) {
                permissionCallback.onPermissionGranted(activity, permissions[i], i);
            } else {
                long loadingTime = 2000; // 로딩 화면을 표시할 시간 (밀리초)
                new Handler().postDelayed(() -> {
                    activity.requestPermissions(permissions, requestCode);
                }, loadingTime);
                break;
            }
        }
    }
}
