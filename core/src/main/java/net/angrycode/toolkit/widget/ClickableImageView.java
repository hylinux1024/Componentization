package net.angrycode.toolkit.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by wecodexyz@gmail.com on 2017/10/27 上午11:29.
 * GitHub - https://github.com/wecodexyz
 * Description:
 */

public class ClickableImageView extends AppCompatImageView {

    private int mColorSelected;
    private int mColorTransparent;

    public ClickableImageView(Context context) {
        super(context);
        init();
    }

    public ClickableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClickableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setClickable(true);
        mColorSelected = Color.parseColor("#33555555");
        mColorTransparent = Color.TRANSPARENT;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setColorFilter(mColorSelected, PorterDuff.Mode.SRC_ATOP);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_HOVER_MOVE:
            case MotionEvent.ACTION_CANCEL:
                setColorFilter(mColorTransparent, PorterDuff.Mode.SRC_ATOP);
                break;
        }
        return super.onTouchEvent(event);
    }
}
