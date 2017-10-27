package net.angrycode.toolkit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import net.angrycode.core.R;

public class QuickClearEditText extends FrameLayout {
    private static final String LOG_TAG = "QuickClearEditText";

    /**
     * 输入内容距离清除按钮之间的距离
     */
    private static final int QUICK_CLEAR_MARGIN = 0;

    protected EditText mEditText;

    private FrameLayout mQuickClearBtnWrapper;
    private ImageView mQuickClearBtn;

    /**
     * 允许使用快速清除功能
     */
    private boolean mAllowQuickClear;

    private Drawable mQuickClearDrawable;

    private int mHeight = 0;

    public QuickClearEditText(Context context) {
        this(context, null);
    }

    public QuickClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // 让设置的背景作用于EditText上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(null);
        } else {
            setBackgroundDrawable(null);
        }
        setPadding(0, 0, 0, 0);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QuickClearEditText);
        final boolean allowQuickClear = array.getBoolean(R.styleable.QuickClearEditText_quickClear, true);
        mQuickClearDrawable = array.getDrawable(R.styleable.QuickClearEditText_quickClearIcon);
        array.recycle();

        // 设置一个默认的清除按钮
        if (mQuickClearDrawable == null) {
            mQuickClearDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_clear);
        }

        addEditText(context, attrs);

        setAllowQuickClear(allowQuickClear);

    }

    private void addEditText(Context context, AttributeSet attrs) {
        mEditText = onCreateEditText(context, attrs);
        mEditText.setId(ViewUtils.generateViewId());

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mEditText, lp);
        mEditText.addTextChangedListener(new InputContentChangeListener());
    }

    private void addQuickClearBtn(Context context) {
        mQuickClearBtn = new ImageView(context);
        mQuickClearBtnWrapper = new FrameLayout(context);

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, outValue, true);
        if (outValue.resourceId != -1) {
            mQuickClearBtn.setBackgroundResource(outValue.resourceId);
        }

        mQuickClearBtn.setScaleType(ImageView.ScaleType.CENTER);
        mQuickClearBtn.setImageDrawable(mQuickClearDrawable);

        int w = mHeight != 0 ? mHeight / 2 : LayoutParams.WRAP_CONTENT;
        int h = mHeight != 0 ? mHeight / 2 : LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams btnLp = new FrameLayout.LayoutParams(w, h);
        btnLp.gravity = Gravity.CENTER;
        mQuickClearBtnWrapper.addView(mQuickClearBtn, btnLp);

        int width = mHeight != 0 ? mHeight : LayoutParams.WRAP_CONTENT;
        int height = mHeight != 0 ? mHeight : LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
        lp.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        addView(mQuickClearBtnWrapper, lp);

        if (mEditText.getText().length() > 0) {
            mQuickClearBtnWrapper.setVisibility(View.VISIBLE);
        } else {
            mQuickClearBtnWrapper.setVisibility(View.INVISIBLE);
        }

        setEditTextPadding();

        mQuickClearBtnWrapper.setOnClickListener(new QuickClearClickListener());
    }

    private void removeQuickClearBtn() {
        if (mQuickClearBtn != null) {
            removeView(mQuickClearBtn);
            mQuickClearBtn = null;
        }
    }

    private void resizeQuickClearBtnIfNeed() {
        if (mAllowQuickClear && mQuickClearBtnWrapper != null) {
            ViewGroup.LayoutParams lp = mQuickClearBtn.getLayoutParams();
            if (lp != null) {
                lp.width = mHeight / 2;
                lp.height = mHeight / 2;
                mQuickClearBtn.setLayoutParams(lp);
            }

            lp = mQuickClearBtnWrapper.getLayoutParams();
            if (lp != null) {
                lp.width = mHeight;
                lp.height = mHeight;
                mQuickClearBtnWrapper.setLayoutParams(lp);
            }
            setEditTextPadding();
        }
    }

    private void setEditTextPadding() {
        if (mHeight > 0) {
            int l = mEditText.getPaddingLeft();
            int t = mEditText.getPaddingTop();
            int r = mEditText.getPaddingRight() + mHeight + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, QUICK_CLEAR_MARGIN, getResources().getDisplayMetrics());
            int b = mEditText.getPaddingBottom();
            mEditText.setPadding(l, t, r, b);
        }
    }

    public void setAllowQuickClear(boolean allow) {
        if (allow != mAllowQuickClear) {
            mAllowQuickClear = allow;
            if (mAllowQuickClear) {
                addQuickClearBtn(getContext());
            } else {
                removeQuickClearBtn();
            }
        }
    }

    public void setText(final CharSequence text) {
        mEditText.setText(text);
    }

    public int length() {
        return mEditText.length();
    }

    public void setSelection(int index) {
        mEditText.setSelection(index);
    }

    public Editable getText() {
        return mEditText.getText();
    }

    public EditText getEditText() {
        return mEditText;
    }

    public void append(final CharSequence text) {
        mEditText.append(text);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        resizeQuickClearBtnIfNeed();
//		Logger.d(LOG_TAG, "height:" + mHeight);
    }

    public void setPasswordVisible(boolean visible) {
        if (visible) {//显示明文
            mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {//显示密文
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        mEditText.setSelection(mEditText.getText().length());

    }

    protected EditText onCreateEditText(Context context, AttributeSet attrs) {

        return new EditText(context, attrs);
    }

    /**
     * 供子类使用，免得子类再去添加一个监听器
     */
    protected void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    private class InputContentChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (mTextWatcher != null) {
                mTextWatcher.beforeTextChanged(s, start, count, after);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            QuickClearEditText.this.onTextChanged(s, start, before, count);
            if (mTextWatcher != null) {
                mTextWatcher.onTextChanged(s, start, before, count);
            }
            if (mAllowQuickClear && mQuickClearBtnWrapper != null) {
                if (s != null && s.length() > 0) {
                    mQuickClearBtnWrapper.post(new Runnable() {
                        @Override
                        public void run() {
                            mQuickClearBtnWrapper.setVisibility(View.VISIBLE);
                        }
                    });

                } else {
                    mQuickClearBtnWrapper.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mTextWatcher != null) {
                mTextWatcher.afterTextChanged(s);
            }
        }
    }

    private class QuickClearClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (mEditText != null) {
                mEditText.setText(null);
                mEditText.requestFocus();
            }
        }
    }

    public TextWatcher mTextWatcher;

    public void setOnTextChangeListener(TextWatcher textWatcher) {
        mTextWatcher = textWatcher;
    }

}