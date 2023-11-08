package org.techtown.cap2;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class BoomGameActivity extends AppCompatActivity {

    static ImageView imageView;
//    private Button changeImageButton;
//    private int[] imageResources = {R.drawable.boom1, R.drawable.boom2, R.drawable.boom3};
//    private int currentImageIndex = 0;
    Button backBtn;
    private BluetoothThread bluetoothThread;
    Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boomb_game);
        context = this;

        bluetoothThread = BluetoothThread.getInstance(this);
        showConfirmationDialog();
        imageView = findViewById(R.id.view);

        backBtn= findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToBluetooth2("c");
                finish();
            }
        });

    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("게임 방법")
                .setMessage("이 게임은 리모컨으로 진행됩니다.")
                .setPositiveButton("시작하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        sendDataToBluetooth2("r");
                        //int maxNumber = Integer.parseInt(numberTextView.getText().toString());
                        //Random random = new Random();
                        //int num1 = random.nextInt(10) + 1; // 1 이상 maxNumber 이하의 난수 생성

                        //Log.d(TAG, "random num: " + num1); // 랜덤값을 로그로 출력
                        //sendDataToBluetooth3("0", "0", "0", String.valueOf(num1));

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void sendDataToBluetooth2(String message1) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData2(message1);
    }
    private void sendDataToBluetooth3(String message1, String message2, String message3, String message4) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData3(message1, message2, message3, message4);
    }
}
