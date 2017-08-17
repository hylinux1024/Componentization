package net.angrycode.toolkit;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class KeyboardVisibilityEvent {

    private final static int KEYBOARD_VISIBLE_THRESHOLD_DP = 100;

    /**
     * Set keyboard visiblity change event listener.
     *
     * @param activity Activity
     * @param listener KeyboardVisibilityEventListener
     */
    public static void setEventListener(
            @NonNull final Activity activity,
            @NonNull final KeyboardVisibilityEventListener listener) {


        setEventListener(activity, false, listener);
    }

    /***
     *
     * @param activity
     * @param checkAlways https://yun.115.com/5/T291465.html#[Bug][内测版][115+][V5.1][事务][Android]搜索事务后退至后台再进入底部的统计小黑条会显示在输入法的上方
     * @param listener
     */
    public static void setEventListener(
            @NonNull final Activity activity, final boolean checkAlways,
            @NonNull final KeyboardVisibilityEventListener listener) {


        final View activityRoot = getActivityRoot(activity);


        activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {


                    private final Rect r = new Rect();


                    private final int visibleThreshold = Math.round(
                            Utils.dip2px(activity, KEYBOARD_VISIBLE_THRESHOLD_DP));


                    private boolean wasOpened = false;


                    @Override
                    public void onGlobalLayout() {
                        activityRoot.getWindowVisibleDisplayFrame(r);


                        int heightDiff = activityRoot.getRootView().getHeight() - r.height();


                        boolean isOpen = heightDiff > visibleThreshold;

                        if (!checkAlways) {//每次都检查，默认为false
                            if (isOpen == wasOpened) {
                                // keyboard state has not changed
                                return;
                            }
                        }


                        wasOpened = isOpen;


                        listener.onVisibilityChanged(isOpen);
                    }
                });
    }

    /**
     * Determine if keyboard is visible
     *
     * @param activity Activity
     * @return Whether keyboard is visible or not
     */
    public static boolean isKeyboardVisible(Activity activity) {
        Rect r = new Rect();


        View activityRoot = getActivityRoot(activity);
        int visibleThreshold = Math
                .round(Utils.dip2px(activity, KEYBOARD_VISIBLE_THRESHOLD_DP));


        activityRoot.getWindowVisibleDisplayFrame(r);


        int heightDiff = activityRoot.getRootView().getHeight() - r.height();


        return heightDiff > visibleThreshold;
    }

    private static View getActivityRoot(Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }


    public interface KeyboardVisibilityEventListener {

        void onVisibilityChanged(boolean isOpen);
    }


} 