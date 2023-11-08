package org.techtown.cap2;

import org.techtown.cap2.view.dialog.BeverRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Const {
    public static final int PERMISSION_BLUETOOTH_LOADING_ACTIVITY = 101;

    public static final ArrayList<BeverRecipe> RECIPE_LIST = new ArrayList<BeverRecipe>(
            Arrays.asList(
                    new BeverRecipe(new String[]{"소주","포도주스","핫식스"}, new int[]{1,2,3}),
                    new BeverRecipe(new String[]{"소주","데미소다","핫식스"}, new int[]{1,3,2}),
                    new BeverRecipe(new String[]{"소주","요구르트"}, new int[]{2,4}),
                    new BeverRecipe(new String[]{"소주","맥주","밀키스"}, new int[]{1,1,1}),
                    new BeverRecipe(new String[]{"소주","사이다","핫식스"}, new int[]{1,1,1}),
                    new BeverRecipe(new String[]{"사이다","막걸리","소주"}, new int[]{1,1,1}),
                    new BeverRecipe(new String[]{"소주","맥주", "콜라"}, new int[]{2,1,1}),
                    new BeverRecipe(new String[]{"맥주","토마토주스"}, new int[]{1,2}),
                    new BeverRecipe(new String[]{"소주","레스비"}, new int[]{1,2})
            )
    );


}
