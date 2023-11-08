package org.techtown.cap2;


import static org.techtown.cap2.BoomGameActivity.imageView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import org.techtown.cap2.util.PermissionCallback;
import org.techtown.cap2.util.PermissionUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.UUID;


// BluetoothThread.java
public class BluetoothThread extends Thread {
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "BluetoothThread";
    private String address = "00:21:11:01:32:98"; // 아두이노 블루투스 모듈의 맥어드레스
    public static Context context;
    private BluetoothAdapter bluetoothAdapter;
    private InputStream inputStream;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
    private static BluetoothThread instance;
    private String water;
    private MediaPlayer mediaPlayer;
    private int[] imageResources = {R.drawable.boom1, R.drawable.boom2, R.drawable.boom3};
    private int currentIndex = 0; // 현재 이미지 인덱스
    private BluetoothThread(Context context) {
        this.context = context;
        BluetoothManager bluetoothManager = (BluetoothManager) this.context.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            // Bluetooth를 지원하지 않는 기기에 대한 처리
        }
    }


    public static synchronized BluetoothThread getInstance(Context context) {
        if (instance == null) {
            instance = new BluetoothThread(context);
        } else {
            changeContext(context);
        }
        return instance;
    }

    @Override
    public void run() {
        if (bluetoothAdapter == null) {
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            return;
        }

        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);

        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            bluetoothAdapter.cancelDiscovery();

            try {
                bluetoothSocket.connect();
                outputStream = bluetoothSocket.getOutputStream();
                inputStream = bluetoothSocket.getInputStream();

                // inputStream 초기화 추가
            } catch (IOException e) {
                try {
                    bluetoothSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "Error closing Bluetooth socket: " + e2.getMessage());
                    Toast.makeText(context, "블루투스 연결실패 재시동해주세요", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // outputStream = bluetoothSocket.getOutputStream(); 이 코드는 inputStream 초기화 이후로 이동해야 합니다.
        } catch (IOException e) {
            Log.e(TAG, "Error creating Bluetooth socket: " + e.getMessage());
            return;
        } catch (SecurityException e) {
            Log.e(TAG, "Error creating Bluetooth socket: " + e.getMessage());
            return;
        }
        receiveData();
    }

    public static void changeContext(Context context) {
        BluetoothThread.context = context;
    }

    public void sendData(String message1, String message2, String message3) {
        if (outputStream != null) {
            try {
                String message = message1 + "," + message2 + "," + message3;
                byte[] messageBytes = message.getBytes();
                outputStream.write(messageBytes);
                Log.d(TAG, "Sent data: " + message);
            } catch (IOException e) {
                Log.e(TAG, "Error sending data: " + e.getMessage());
            }
        }
    }
    public void sendData2(String message11) {
        if (outputStream != null) {
            try {
                String message = message11;
                byte[] messageBytes = message.getBytes();
                outputStream.write(messageBytes);
                Log.d(TAG, "Sent data: " + message);
            } catch (IOException e) {
                Log.e(TAG, "Error sending data: " + e.getMessage());
            }
        }
    }
    public void sendData3(String message1, String message2, String message3, String message4) {
        if (outputStream != null) {
            try {
                String message = message1 + "," + message2 + "," + message3+ "," + message4;
                byte[] messageBytes = message.getBytes();
                outputStream.write(messageBytes);
                Log.d(TAG, "Sent data: " + message);
            } catch (IOException e) {
                Log.e(TAG, "Error sending data: " + e.getMessage());
            }
        }
    }
    public void receiveData() {

        byte[] buffer = new byte[1024];
        int bytes;

        while (true) {
            try {
                bytes = inputStream.read(buffer);
                String receivedMessage = new String(buffer, 0, bytes);
                Log.d(TAG, "Received data: " + receivedMessage);
                // Process received data as needed
                if (receivedMessage.trim().equals("Winner!!")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Winner!!", Toast.LENGTH_SHORT).show();
                            Glide.with(context)
                                    .asGif()
                                    .load(R.drawable.boom4)
                                    .into(imageView);
                            mediaPlayer = MediaPlayer.create(context, R.raw.boom);
                            mediaPlayer.start();

                            // 5초 후에 MediaPlayer를 정지
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                        mediaPlayer.stop();
                                        mediaPlayer.release();
                                        mediaPlayer = null;
                                    }
                                }
                            }, 5000);
                            Intent intent = new Intent(context, BoomGameActivity.class);
                            context.startActivity(intent);
                        }
                    });
                } else if (receivedMessage.trim().equals("next")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 이미지 뷰를 다음 이미지로 변경
                            BoomGameActivity.imageView.setImageResource(imageResources[currentIndex]);

                            // 다음 이미지 인덱스로 이동
                            currentIndex = (currentIndex + 1) % imageResources.length;

                        }
                    });
                }



                else if (receivedMessage.trim().equals("NotEnough1")) {
                    water = "1번칸 물이 부족합니다\n";
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder ad1 = new AlertDialog.Builder(context);
                            ad1.setIcon(R.mipmap.ic_launcher);
                            ad1.setTitle("알림메시지");
                            ad1.setMessage(water);
                            ad1.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });

                            ad1.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            ad1.show();
                        }
                    });
                }else if (receivedMessage.trim().equals("NotEnough2")) {
                    water = "2번칸 물이 부족합니다\n";
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder ad1 = new AlertDialog.Builder(context);
                            ad1.setIcon(R.mipmap.ic_launcher);
                            ad1.setTitle("알림메시지");
                            ad1.setMessage(water);
                            ad1.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });

                            ad1.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            ad1.show();
                        }
                    });
                }else if (receivedMessage.trim().equals("NotEnough3")) {
                    water = "3번칸 물이 부족합니다";
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder ad1 = new AlertDialog.Builder(context);
                            ad1.setIcon(R.mipmap.ic_launcher);
                            ad1.setTitle("알림메시지");
                            ad1.setMessage(water);
                            ad1.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });

                            ad1.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            ad1.show();
                        }
                    });
                }



            } catch (IOException e) {
                Log.e(TAG, "Error receiving data: " + e.getMessage());
                break;
            }
        }
    }

    public void disconnect() {
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException e) {
                Log.e(TAG, "Error flushing output stream: " + e.getMessage());
            }
        }

        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Error closing Bluetooth socket: " + e.getMessage());
        }
    }
}
