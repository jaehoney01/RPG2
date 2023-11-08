package org.techtown.cap2.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.cap2.R;
import org.techtown.cap2.SharePreferenceConst;
import org.techtown.cap2.util.SharedPreferenceUtil;
import org.techtown.cap2.view.Drink2Activity;

import java.util.ArrayList;

public class RecipeDialogAdapter extends RecyclerView.Adapter<RecipeDialogAdapter.RecipeHolder> {
    private ArrayList<BeverRecipe> list;
    private Context context;
    Button okbtn;
    private OnClickRecipeItem onClickRecipeItem;
    Dialog dilaog01;

    public RecipeDialogAdapter(Context context, ArrayList<BeverRecipe> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnClickRecipeItem(OnClickRecipeItem onClickRecipeItem) {
        this.onClickRecipeItem = onClickRecipeItem;
    }

    @NonNull
    @Override
    public RecipeDialogAdapter.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDialogAdapter.RecipeHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bindItems(list.get(position).getRecipe());
        holder.firstItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = 0;
                int num2 = 0;
                int num3 = 0;

                String firstBever = SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.FIRST_BEVER);
                String secondBever = SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.SECOND_BEVER);
                String thirdBever = SharedPreferenceUtil.getSharedPreference(context, SharePreferenceConst.THIRD_BEVER);

                for(int i = 0; i < list.get(position).getRecipeCount().length; i++) {
                    if(list.get(position).getRecipe()[i].equals(firstBever)) {
                        num1 = list.get(position).getRecipeCount()[i];
                    } else if(list.get(position).getRecipe()[i].equals(secondBever)) {
                        num2 = list.get(position).getRecipeCount()[i];
                    } else if(list.get(position).getRecipe()[i].equals(thirdBever)) {
                        num3 = list.get(position).getRecipeCount()[i];
                    }
                }

                onClickRecipeItem.onClickRecipeItem(num1, num2, num3);

            }
        });

        if(list.get(position).getRecipe().length == 2) {
            holder.inVisiblePlus2();
            holder.visiblePlus1();
        } else if(list.get(position).getRecipe().length == 3) {
            holder.visiblePlus1();
            holder.visiblePlus2();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    protected class RecipeHolder extends RecyclerView.ViewHolder {
        private final TextView firstItem;
        private final TextView secondItem;
        private final TextView thirdItem;
        private final TextView plus1;
        private final TextView plus2;
        private final TextView pullBeverage;

        public RecipeHolder(@NonNull View itemView) {
            super(itemView);

            firstItem = itemView.findViewById(R.id.first_item);
            secondItem = itemView.findViewById(R.id.second_item);
            thirdItem = itemView.findViewById(R.id.third_item);
            plus1 = itemView.findViewById(R.id.plus1);
            plus2 = itemView.findViewById(R.id.plus2);
            pullBeverage = itemView.findViewById(R.id.out_button);

        }

        public void bindItems(String[] item) {
            for(int i = 0; i < item.length; i++) {
                if(i == 0) {
                    firstItem.setText(item[i]);
                } else if(i == 1) {
                    secondItem.setText(item[i]);
                } else if(i == 2) {
                    thirdItem.setText(item[i]);
                }
            }
        }

        public void visiblePlus1() {
            plus1.setVisibility(View.VISIBLE);
        }
        public void inVisiblePlus2() {
            plus2.setVisibility(View.INVISIBLE);
        }
        public void visiblePlus2() {
            plus2.setVisibility(View.VISIBLE);
        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            pullBeverage.setOnClickListener(onClickListener);
        }
    }

    public interface OnClickRecipeItem {
        void onClickRecipeItem(int num1, int num2, int num3);
    }
}
