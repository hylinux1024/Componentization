package net.angrycode.toolkit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import net.angrycode.core.R;

/**
 * Created by wecodexyz on 2017/8/19.
 */

public class CheckedImageView extends AppCompatImageView {
    boolean mChecked = false;

    protected Drawable mNormalState;
    protected Drawable mCheckedState;

    public CheckedImageView(Context context) {
        super(context);
    }

    public CheckedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CheckedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckedImageView, defStyleAttr, 0);
        mChecked = array.getBoolean(R.styleable.CheckedImageView_ImageChecked, false);
        int normal = array.getResourceId(R.styleable.CheckedImageView_ImageNormalState, 0);
        int checked = array.getResourceId(R.styleable.CheckedImageView_ImageCheckedState, 0);
        if (normal != 0) {
            mNormalState = ContextCompat.getDrawable(context, normal);
        }
        if (checked != 0) {
            mCheckedState = ContextCompat.getDrawable(context, checked);
        }
        setChecked(mChecked);
        array.recycle();
    }

    public void toggle() {
        setChecked(!mChecked);
    }

    public boolean isChecked() {
        return mChecked;
    }

    /**
     * Sets the checked state of this view.
     *
     * @param checked {@code true} set the state to checked, {@code false} to
     *                uncheck
     */
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
        }
        if (mChecked) {
            setImageDrawable(mCheckedState);
        } else {
            setImageDrawable(mNormalState);
        }
    }

    public void setImageDrawableState(@NonNull Drawable checkedState, @NonNull Drawable normalState) {
        mCheckedState = checkedState;
        mNormalState = normalState;
        setChecked(mChecked);
    }
}
