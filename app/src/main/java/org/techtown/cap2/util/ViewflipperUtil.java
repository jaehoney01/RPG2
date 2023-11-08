package org.techtown.cap2.util;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class ViewflipperUtil {
    private final Context context;
    private int[] images = null;

    public ViewflipperUtil(Context context, int[] images) {
        this.context = context;
        this.images = images.clone();
    }
    public void basicFlip(ViewFlipper viewFlipper) {
        for(int image: images){
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(image);

            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);
            viewFlipper.setInAnimation(context, android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(context, android.R.anim.slide_out_right);
        }
    }
}
