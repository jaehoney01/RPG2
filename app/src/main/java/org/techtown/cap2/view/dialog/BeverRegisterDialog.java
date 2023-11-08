package org.techtown.cap2.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.cap2.R;

import java.util.ArrayList;

public class BeverRegisterDialog extends Dialog {
    private TextView cancel;

    private String titleText;

    private View.OnClickListener cancelBtnListener;
    private  OnClickBeverRegisterItem onClickBeverRegisterItem;
    private GridView gridView;

    public TextView title;


    public BeverRegisterDialog(@NonNull Context context, View.OnClickListener cancelBtnListener, String title, OnClickBeverRegisterItem onClickBeverRegisterItem) {
        super(context);
        //생성자에서 리스너 및 텍스트 초기화
        this.cancelBtnListener = cancelBtnListener;
        this.titleText = title;
        this.onClickBeverRegisterItem = onClickBeverRegisterItem;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그의 꼭짓점이 짤리는부분 방지.
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.dialog_bever_register);

        title = findViewById(R.id.title);
        title.setText(titleText);

        cancel = findViewById(R.id.button_cancel);
        cancel.setOnClickListener(cancelBtnListener);

        gridView = findViewById(R.id.gridView);
        
        // 다른 음료수 항목들을 추가할 수 있습니다.
        GridViewAdapter adapter = new GridViewAdapter(onClickBeverRegisterItem);
        adapter.addItem("소주");
        adapter.addItem("포도주스");
        adapter.addItem("핫식스");
        adapter.addItem("데미소다");
        adapter.addItem("요구르트");
        adapter.addItem("맥주");
        adapter.addItem("밀키스");
        adapter.addItem("막걸리");
        adapter.addItem("사이다");
        adapter.addItem("콜라");
        adapter.addItem("토마토주스");
        adapter.addItem("레스비");
        gridView.setAdapter(adapter);

    }



    class GridViewAdapter extends BaseAdapter {
        OnClickBeverRegisterItem onClickBeverRegisterItem;
        ArrayList<String> items = new ArrayList<String>();

        public GridViewAdapter(OnClickBeverRegisterItem onClickBeverRegisterItem) {
            this.onClickBeverRegisterItem = onClickBeverRegisterItem;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(String item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final String bever = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_bever_item, viewGroup, false);

                TextView tv_num = (TextView) convertView.findViewById(R.id.tv_item_bever);
                tv_num.setText(bever);
                tv_num.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickBeverRegisterItem.onClickGridBeverItem(bever);
                    }
                });
            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            return convertView;  //뷰 객체 반환
        }
    }

    public interface OnClickBeverRegisterItem {
        public void onClickGridBeverItem(String Text);
    }


}