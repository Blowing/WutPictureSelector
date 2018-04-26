package com.wujie.pictureselector.util;

import android.content.Context;

/**
 * Created by wujie on 2018/4/17/017.
 */

public class Utils {

    public static int dp2Px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    public static int px2dp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return  (int) (px / density + 0.5f);
    }
}
