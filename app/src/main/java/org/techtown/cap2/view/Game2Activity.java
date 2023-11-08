package org.techtown.cap2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.techtown.cap2.BluetoothThread;
import org.techtown.cap2.R;

import java.util.Random;

public class Game2Activity extends AppCompatActivity {
    private String[] messages = {"지금 이자리에 관심 있는 사람이 있다.", "솔직히 여기서 내가 제일 낫다고 생각한다.!", "전애인 스토리를 훔쳐본다", "양말 안 신은 사람",
            "나보다 나이 많은 사람","본인 주량 소주 2병 이하인 사람","애인 없는 사람 접어","오늘 머리 안감은 사람 접어","어릴 때 코딱지 먹어본 적 있는 사람","나는 사실 지금 취했다",
            "앞머리 있는 사람","MBTI E인 사람 접어","안경 쓴 사람 "};
    private AlertDialog alertDialog;
    private Handler handler;
    private HandlerThread handlerThread;

    TextView textView7;
    Button back_button,button6,button7;

    boolean messageRunning = true;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        BluetoothThread.getInstance(this);
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        textView7 = findViewById(R.id.textView7);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        back_button = findViewById(R.id.back_button);
        displayRandomMessage();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null); // 모든 대기 중인 호출 제거
                messageRunning = false; // messageRunning 값을 false로 설정하여 다음 호출에서 질문을 출력하지 않도록 함
            }
        });


        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!messageRunning) {
                    messageRunning = true; // messageRunning을 true로 설정하여 다시 질문이 나오도록 함
                    displayRandomMessage();
                }
            }
        });

    }

    private void displayRandomMessage() {
        Random random = new Random();
        int randomIndex = random.nextInt(messages.length);
        String randomMessage = messages[randomIndex];
        textView7.setText(randomMessage);

        if (messageRunning) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayRandomMessage();
                }
            }, 5000);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (handlerThread != null) {
            handlerThread.quit();
        }
    }
}
