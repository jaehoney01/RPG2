package org.techtown.cap2.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.core.app.ActivityCompat;

import org.techtown.cap2.BaseActivity;
import org.techtown.cap2.BluetoothThread;
import org.techtown.cap2.Const;
import org.techtown.cap2.R;
import org.techtown.cap2.util.PermissionCallback;
import org.techtown.cap2.util.PermissionUtil;

public class LoadingActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private ImageView rpgImageView;
    private Button start_button; //시작하기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        PermissionUtil.checkPermission(this, new String[] {Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT}, Const.PERMISSION_BLUETOOTH_LOADING_ACTIVITY, new PermissionCallback() {
            @Override
            public void onPermissionGranted(Activity activity, String permission, int number) {
                if(number == 1) {
                    Toast.makeText(activity, "블루투스 권한이 허용되어 있습니다.", Toast.LENGTH_SHORT).show();
                    init();
                }
            }
        });


        // 일정 시간이 지난 후에 메인 액티비티로 전환하도록 설정할 수 있습니다.
//        long loadingTime = 5000; // 로딩 화면을 표시할 시간 (밀리초)
//        new Handler().postDelayed(() -> {
//            Intent intent = new Intent(Loading.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }, loadingTime);
    }

    private void init() {
        // BluetoothThread 스레드 초기화
        BluetoothThread bluetoothThread = BluetoothThread.getInstance(LoadingActivity.this);
        bluetoothThread.start();

        rpgImageView = findViewById(R.id.load);
        start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(view -> {
            Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        rpgImageView.setImageResource(R.drawable.loadinggggg);
        Glide.with(this).load(R.drawable.loadinggggg).into(rpgImageView);

        // BluetoothThread 객체 생성

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Const.PERMISSION_BLUETOOTH_LOADING_ACTIVITY) { //101
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "블루투스 권한을 허용하였습니다.", Toast.LENGTH_SHORT).show();
                init();
            } else {
                Toast.makeText(this, "블루투스 권한을 거절하였습니다. 앱을 종료합니다.", Toast.LENGTH_SHORT).show();
                finishApp();
            }
        }
    }
}
