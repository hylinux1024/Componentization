package net.angrycode.toolkit;

import android.content.Context;

/**
 * Created by wecodexyz on 2017/8/17.
 */

public class Utils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
