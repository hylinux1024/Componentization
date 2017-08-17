package net.angrycode.toolkit;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Keyboard utilities
 */
public class Keyboard {

    public static void show(final View view, final long delay) {
        if (view == null) {
            return;
        }
        final InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (view.getWidth() == 0 && view.getHeight() == 0) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    removeViewTreeObserver(view, this);
                    if (delay <= 0) {
                        manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                    } else {
                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                            }
                        }, delay);
                    }
                }
            });
        } else {
            if (delay <= 0) {
                manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            } else {
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                    }
                }, delay);
            }
        }

    }

    public static void hide(final View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 移除View上的ViewTreeObserver，兼容方法
     */
    public static void removeViewTreeObserver(View view, ViewTreeObserver.OnGlobalLayoutListener l) {
        if (view != null && view.getViewTreeObserver() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(l);
            } else {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(l);
            }
        }
    }

    /**
     * 点击屏幕空白区域隐藏软键盘（方法2）
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     * 需重写dispatchTouchEvent
     * 参照以下注释代码
     */
    public static void clickBlankArea2HideSoftInput1() {
        /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }
        // 获取InputMethodManager，隐藏软键盘
        private void hideKeyboard(IBinder token) {
            if (token != null) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        */
    }

} 