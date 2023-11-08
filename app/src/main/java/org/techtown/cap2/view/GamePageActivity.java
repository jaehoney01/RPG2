package org.techtown.cap2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import org.techtown.cap2.BluetoothThread;
import org.techtown.cap2.BoomGameActivity;
import org.techtown.cap2.R;

import java.util.Random;

public class GamePageActivity extends AppCompatActivity {

    private Button rulletButton, sonByungHobutton, rollBombbutton, backButton;
    private String message1,message2,message3;
    private LinearLayout container;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        Random random = new Random();
        int num1 = random.nextInt(6); // Generates a random number between 0 and 5
        int num2 = random.nextInt(Math.max(0, 10 - num1 - 4)); // Generates a random number between 0 and (10 - num1 - 4)
        int num3 = 10 - num1 - num2;

        if (num3 < 0) {
            num1 = 0;
            num2 = 0;
            num3 = 0;
        }
        message1 = String.valueOf(num1);
        message2 = String.valueOf(num2);
        message3 = String.valueOf(num3);
        backButton = findViewById(R.id.back);
        rollBombbutton = findViewById(R.id.roll_bomb);
        rollBombbutton.setOnClickListener(view -> {
            showDialog02(1);
        });

        backButton.setOnClickListener(view -> {
            // container = findViewById(R.id.container2);

            // 이후 필요한 시점에서 두 번째 레이아웃으로 변경
            //container.removeAllViews(); // 현재의 모든 뷰를 제거
            //setContentView(R.layout.activity_main); // 두 번
            finish();
        });

        rulletButton = findViewById(R.id.rullet_button);
        sonByungHobutton = findViewById(R.id.son_byung_ho_button);


        rulletButton.setOnClickListener(view -> {
            showDialog01(1);
        });

        sonByungHobutton.setOnClickListener(view -> {
            showDialog01(2);
        });


    }

    private void showDialog01(int buttonIndex) {
        Dialog dialog01 = new Dialog(this);
        dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog01.setContentView(R.layout.activity_game_dialog);
        dialog01.show();

        Button noBtn = dialog01.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(view -> {
            // '아니오' 버튼 클릭 시 동작 구현
            if (buttonIndex == 1) {
                // 버튼 1에 대한 처리
                startActivity(new Intent(this, RouletteActivity.class));
            } else if (buttonIndex == 2) {
                sendDataToBluetooth(message1, message2, message3);
                // 버튼 2에 대한 처리
                startActivity(new Intent(this, Game2Activity.class));
            }
            dialog01.dismiss();
        });

        Button yesBtn = dialog01.findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(view -> {
            // '예' 버튼 클릭 시 동작 구현
            Intent intent = new Intent(this, Drink2Activity.class);
            startActivity(intent);
            dialog01.dismiss();
        });
    }
    private void showDialog02(int buttonIndex) {
        Dialog dialog02 = new Dialog(this);
        dialog02.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog02.setContentView(R.layout.activity_boomgame_dialog);
        dialog02.show();

        Button noBtn = dialog02.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(view -> {
            // '아니오' 버튼 클릭 시 동작 구현
            if (buttonIndex == 1) {
                sendDataToBluetooth(message1, message2, message3);

                // 버튼 2에 대한 처리
                startActivity(new Intent(this, BoomGameActivity.class));
            }
            dialog02.dismiss();
        });

        Button yesBtn = dialog02.findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(view -> {
            // '예' 버튼 클릭 시 동작 구현
            Intent intent = new Intent(this, BoomGameActivity.class);
            startActivity(intent);
            dialog02.dismiss();
        });
    }

    private void sendDataToBluetooth(String message1, String message2, String message3) {
        // BluetoothThread 객체의 sendData 메서드 호출
        BluetoothThread.getInstance(this).sendData(message1, message2, message3);
        Log.d("TAG", "전송된 데이터1: " + message1);
        Log.d("TAG", "전송된 데이터2: " + message2);
        Log.d("TAG", "전송된 데이터3: " + message3);
    }
    private void sendDataToBluetooth2(String message1) {
        // BluetoothThread 객체의 sendData 메서드 호출
        BluetoothThread.getInstance(this).sendData2(message1);
    }
}
