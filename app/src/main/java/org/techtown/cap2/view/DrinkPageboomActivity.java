package org.techtown.cap2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.cap2.BluetoothThread;
import org.techtown.cap2.Const;
import org.techtown.cap2.R;
import org.techtown.cap2.SharePreferenceConst;
import org.techtown.cap2.util.SharedPreferenceUtil;
import org.techtown.cap2.view.dialog.BeverRecipe;
import org.techtown.cap2.view.dialog.BeverRegisterDialog;
import org.techtown.cap2.view.dialog.RecipeDialog;
import org.techtown.cap2.view.dialog.RecipeDialogAdapter;

import java.util.ArrayList;

public class DrinkPageboomActivity extends AppCompatActivity {

    Dialog dilaog01;
    Button back2,btn,btn4;
    TextView st1,st2,st3;
    private String num1, num2, num3, water;
    private RecipeDialog recipeDialog;



    private int maxTotal = 20;
    private SeekBar bar1;
    private SeekBar bar2;
    private SeekBar bar3;
    private BluetoothThread bluetoothThread;
    Context context;

    private TextView firstBever,secondBever,thirdBever;
    private Button recipe;
    private int currentChoiceRegister = -1;

    private BeverRegisterDialog beverRegisterDialog;



    public DrinkPageboomActivity() {
        // BluetoothThread 인스턴스를 가져옴
        bluetoothThread = BluetoothThread.getInstance(this);
    }

    public void sendDataToBluetooth(String message1, String message2, String message3) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData(message1, message2, message3);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_page);
        context = this;


        bluetoothThread = BluetoothThread.getInstance(this);
        bar1 = findViewById(R.id.bar1);
        bar2 = findViewById(R.id.bar2);
        bar3 = findViewById(R.id.bar3);

        firstBever = findViewById(R.id.first_bever);
        secondBever = findViewById(R.id.second_bever);
        thirdBever = findViewById(R.id.third_bever);

        firstBever.setText(SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.FIRST_BEVER) == null ? "1번음료" : SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.FIRST_BEVER));
        secondBever.setText(SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.SECOND_BEVER) == null ? "2번음료" : SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.SECOND_BEVER));
        thirdBever.setText(SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.THIRD_BEVER) == null ? "3번음료" : SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.THIRD_BEVER));
        firstBever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentChoiceRegister = 0;
                showBeverRegisterDialog();
            }
        });

        secondBever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentChoiceRegister = 1;
                showBeverRegisterDialog();
            }
        });

        thirdBever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentChoiceRegister = 2;
                showBeverRegisterDialog();
            }
        });


        recipe = findViewById(R.id.recipe);
        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecipeDialog();
            }
        });
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int total = bar1.getProgress() + bar2.getProgress() + bar3.getProgress();
                if (total > maxTotal) {
                    int diff = total - maxTotal;
                    int newProgress = progress - diff;
                    seekBar.setProgress(newProgress);

                    // 합이 20을 초과하는 경우 토스트 메시지를 표시합니다.
                    Toast.makeText(getApplicationContext(), "3개의 합이 20이 넘으면 안됩니다 !", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No action needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No action needed
            }
        };

        bar1.setOnSeekBarChangeListener(seekBarChangeListener);
        bar2.setOnSeekBarChangeListener(seekBarChangeListener);
        bar3.setOnSeekBarChangeListener(seekBarChangeListener);







        back2 = findViewById(R.id.back2);

        back2.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btn4 = findViewById(R.id.btn4);

        btn4.setOnClickListener(view -> {
            finish();
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);


        dilaog01 = new Dialog(DrinkPageboomActivity.this);       // Dialog 초기화
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog01.setContentView(R.layout.activity_custom_dialog);             // xml 레이아웃 파일과 연결

        // 버튼: 커스텀 다이얼로그 띄우기
        findViewById(R.id.imageButton3).setOnClickListener(view -> {
            showDialog01(); // 아래 showDialog01() 함수 호출
        });
        SeekBar seekbar1 = findViewById(R.id.bar1);
        SeekBar seekbar2 = findViewById(R.id.bar2);
        SeekBar seekbar3 = findViewById(R.id.bar3);

        st1 = findViewById(R.id.st1);
        st1.setText("0 잔");

        SeekBar seekBar1 = findViewById(R.id.bar1);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 원하는 기능 구현
                st1.setText(String.format(" %d 잔", seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 원하는 기능 구현
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 원하는 기능 구현
                st1.setText(String.format(" %d 잔", seekBar.getProgress()));
                num1 = String.valueOf(seekBar.getProgress());

                int totalProgress = seekbar1.getProgress()+ seekbar2.getProgress()+ seekbar3.getProgress();
                if (totalProgress > 20) {
                    Toast.makeText(getApplicationContext(), "3개를 합한 값이 20잔을 넘으면 안됩니다", Toast.LENGTH_LONG).show();
                    seekbar1.setProgress(0);
                    num1= String.valueOf(0);

                    return;
                }
            }
        });

        st2 = findViewById(R.id.st2);
        st2.setText("0 잔");

        SeekBar seekBar2 = findViewById(R.id.bar2);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 원하는 기능 구현
                st2.setText(String.format(" %d 잔", seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 원하는 기능 구현
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 원하는 기능 구현
                st2.setText(String.format(" %d 잔", seekBar.getProgress()));
                num2 = String.valueOf(seekBar.getProgress());

                int totalProgress = seekbar1.getProgress()+ seekbar2.getProgress()+ seekbar3.getProgress();
                if (totalProgress > 20) {
                    Toast.makeText(getApplicationContext(), "3개를 합한 값이 20잔을 넘으면 안됩니다", Toast.LENGTH_SHORT).show();

                    seekbar2.setProgress(0);
                    num2= String.valueOf(0);

                    return;
                }
            }
        });


        st3 = findViewById(R.id.st3);
        st3.setText("0 잔");

        SeekBar seekBar3  = findViewById(R.id.bar3);
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 원하는 기능 구현
                st3.setText(String.format(" %d 잔", seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 원하는 기능 구현
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 원하는 기능 구현
                st3.setText(String.format(" %d 잔", seekBar.getProgress()));
                num3 = String.valueOf(seekBar.getProgress());

                int totalProgress = seekbar1.getProgress()+ seekbar2.getProgress()+ seekbar3.getProgress();
                if (totalProgress > 20) {
                    Toast.makeText(getApplicationContext(), "3개를 합한 값이 20잔을 넘으면 안됩니다", Toast.LENGTH_SHORT).show();

                    seekbar3.setProgress(0);
                    num3= String.valueOf(0);
                    return;
                }
            }
        });


        btn=findViewById(R.id.roul);
        btn.setOnClickListener(v -> {

            sendDataToBluetooth(num1,num2,num3);
            Log.d("TAG", "전송된 데이터: " + num1);
            Log.d("TAG", "전송된 데이터: " + num2);
            Log.d("TAG", "전송된 데이터: " + num3);

            Toast.makeText(getApplicationContext(), "음료 나오는중 ", Toast.LENGTH_SHORT).show();
        });
    }

    // dialog01을 디자인하는 함수
    public void showDialog01(){
        dilaog01.show(); // 다이얼로그 띄우기


        // 아니오 버튼
        Button noBtn = dilaog01.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(view -> {
            // 원하는 기능 구현
            dilaog01.dismiss(); // 다이얼로그 닫기
        });





    }

    private void showRecipeDialog() {
        ArrayList<BeverRecipe> filteredList = new ArrayList<BeverRecipe>();

        String firstBever = SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.FIRST_BEVER);
        String secondBever = SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.SECOND_BEVER);
        String thirdBever = SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.THIRD_BEVER);
        ArrayList<BeverRecipe> beverRecipePlusSize = (ArrayList<BeverRecipe>) Const.RECIPE_LIST.clone();
        aa: for(BeverRecipe beverRecipe : beverRecipePlusSize) {
            bb : for(int i = 0; i < beverRecipe.getRecipe().length; i++) {
                if(beverRecipe.getRecipe()[i].equals(firstBever) ||beverRecipe.getRecipe()[i].equals(secondBever) ||  beverRecipe.getRecipe()[i].equals(thirdBever)) {

                } else {
                    continue aa;
                }
            }
            for(int i = 0; i < beverRecipe.getRecipeCount().length; i++) {
                beverRecipe.getRecipeCount()[i] *= 2;
            }

            filteredList.add(beverRecipe);
        }

        recipeDialog = new RecipeDialog(this, onClickCommDialogConfirmButton,"추천 레시피", filteredList, onClickRecipeItem);
        recipeDialog.show();
    }

    private final View.OnClickListener onClickCommDialogConfirmButton = new View.OnClickListener() {
        public void onClick(View v) {
            recipeDialog.dismiss();
        }
    };

    private final RecipeDialogAdapter.OnClickRecipeItem onClickRecipeItem = new RecipeDialogAdapter.OnClickRecipeItem() {
        @Override
        public void onClickRecipeItem(int num1, int num2, int num3) {

            sendDataToBluetooth(String.valueOf(num1),String.valueOf(num2),String.valueOf(num3));

            Log.d("TAG", "전송된 데이터1: " + num1);
            Log.d("TAG", "전송된 데이터2: " + num2);
            Log.d("TAG", "전송된 데이터3: " + num3);

            DrinkPageboomActivity.this.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "음료 나오는중 ", Toast.LENGTH_SHORT).show();

                        }
                    }
            );
        }
    };
    private void showBeverRegisterDialog() {
        beverRegisterDialog = new BeverRegisterDialog(this, onClickBeverRegisterCancelListener,"음료 등록", onClickBeverRegisterItem);
        beverRegisterDialog.show();
    }
    private final View.OnClickListener onClickBeverRegisterCancelListener = new View.OnClickListener() {
        public void onClick(View v) {
            beverRegisterDialog.dismiss();
        }
    };
    private final BeverRegisterDialog.OnClickBeverRegisterItem onClickBeverRegisterItem = new BeverRegisterDialog.OnClickBeverRegisterItem() {

        @Override
        public void onClickGridBeverItem(String text) {
            if(currentChoiceRegister == 0) {
                SharedPreferenceUtil.putSharedPreference(DrinkPageboomActivity.this, SharePreferenceConst.FIRST_BEVER, text);
                firstBever.setText(text);
            } else if(currentChoiceRegister == 1) {
                SharedPreferenceUtil.putSharedPreference(DrinkPageboomActivity.this, SharePreferenceConst.SECOND_BEVER, text);
                secondBever.setText(text);
            } else if(currentChoiceRegister == 2) {
                SharedPreferenceUtil.putSharedPreference(DrinkPageboomActivity.this, SharePreferenceConst.THIRD_BEVER, text);
                thirdBever.setText(text);
            } else {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }

            beverRegisterDialog.dismiss();
        }
    };
}