package org.techtown.cap2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!getClassSimpleName().equals("LoadingAcitivty")) {
            BluetoothThread.getInstance(this);
        }
    }

    protected void finishApp() {
        finishAffinity();
        System.runFinalization();
        System.exit(0);
    }

    protected String getClassSimpleName() {
        return this.getClass().getSimpleName();
    }
}
