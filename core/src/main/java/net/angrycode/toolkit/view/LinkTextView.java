package net.angrycode.toolkit.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import net.angrycode.core.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkTextView extends AppCompatTextView {

    private static final String URL_REGEX = Patterns.WEB_URL.pattern();

    private static final String _EMIAL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    private LocalLinkMovementMethod mLocalLinkMovementMethod;

    private LinkSpannableString mLocalLinkSpannableString;
    private OnLinkClickListener mOnLinkClickListener;

    public LinkTextView(Context context) {
        this(context, null);
    }

    public LinkTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinkTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        mLocalLinkMovementMethod = new LocalLinkMovementMethod();
        mLocalLinkMovementMethod.setLinkTextView(this);
        mLocalLinkMovementMethod.setOnLinkClickListener(mOnLinkClickListener);
    }

    public void setLinkText(CharSequence text) {
        LinkSpannableString ss = new LinkSpannableString(text);
        super.setText(ss);
        setMovementMethod(mLocalLinkMovementMethod);
        mLocalLinkSpannableString = ss;
    }

    public void setLinkClickListener(OnLinkClickListener listener) {
        mOnLinkClickListener = listener;
        if (mLocalLinkMovementMethod != null) {
            mLocalLinkMovementMethod.setOnLinkClickListener(listener);
        }
    }

    public int getCursorPosition(MotionEvent event) {
        if (event == null) {
            return 0;
        }
        Layout layout = this.getLayout();
        float x = event.getX() + this.getScrollX();
        float y = event.getY() + this.getScrollY() - getTotalPaddingTop();
        int line = layout.getLineForVertical((int) y);
        int offset = layout.getOffsetForHorizontal(line, x);
        return offset > 0 ? (x > layout.getLineMax(line) ? offset : offset - 1) : offset;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean superResult = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                tryRemoveLinkSpan();
                break;

            case MotionEvent.ACTION_MOVE:
                if (getMovementMethod() != null) {
                    Spannable buffer = (Spannable) getText();
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= this.getTotalPaddingLeft();
                    y -= this.getTotalPaddingTop();

                    x += this.getScrollX();
                    y += this.getScrollY();

                    Layout layout = this.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
                    if (link.length != 0) {

                        ClickableSpan cs = link[0];
                        int csStart = buffer.getSpanStart(cs);
                        int csEnd = buffer.getSpanEnd(cs);

                        int top = layout.getLineTop(line);
                        int bottom = layout.getLineBottom(line);
                        float eventY = event.getY() - this.getTotalPaddingTop();


                        if (off < csStart || off >= csEnd || eventY < top || eventY > bottom) {
//                            System.out.println("点击之前：" + line + " " + csStart + " " + csEnd + " " + off + " " + link.length + " " + top + " " + bottom + " " + eventY);
                            tryRemoveLinkSpan();
                        }
                    }
                }
                break;
        }

        return superResult;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        if (mLocalLinkMovementMethod != null) {
//            mLocalLinkMovementMethod.setLinkTextView(null);
//            mLocalLinkMovementMethod.setOnLinkClickListener(null);
//        }
    }

    private void tryRemoveLinkSpan() {
        if (getMovementMethod() != null) {
            Spannable buffer = (Spannable) getText();
            //移除按下的背景颜色
            BackgroundColorSpan[] backgroundColorSpans = buffer.getSpans(0, buffer.length(),
                    BackgroundColorSpan.class);
            for (BackgroundColorSpan span : backgroundColorSpans) {
                buffer.removeSpan(span);
                this.setText(buffer);
            }
        }
    }

    public interface OnLinkClickListener {
        boolean onLinkClick(LinkTextView textView, String link, MotionEvent event);

        boolean onNormalClick(LinkTextView textView, MotionEvent event);
    }

    public static class LocalLinkMovementMethod extends LinkMovementMethod {

        private static LocalLinkMovementMethod sInstance;

        private LinkTextView mLinkTextView;
        private OnLinkClickListener mOnLinkClickListener;

        public static LocalLinkMovementMethod getInstance() {
            if (sInstance == null) {
                sInstance = new LocalLinkMovementMethod();
            }
            return sInstance;
        }

        public void setLinkTextView(LinkTextView linkTextView) {
            mLinkTextView = linkTextView;
        }

        public void setOnLinkClickListener(OnLinkClickListener listener) {
            mOnLinkClickListener = listener;
        }

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();

            switch (action) {
                case MotionEvent.ACTION_CANCEL:
                    //移除按下的背景颜色
                    BackgroundColorSpan[] backgroundColorSpans = buffer.getSpans(0, buffer.length(),
                            BackgroundColorSpan.class);
                    for (BackgroundColorSpan span : backgroundColorSpans) {
                        buffer.removeSpan(span);
                        widget.setText(buffer);
                    }
                    return true;

                case MotionEvent.ACTION_MOVE:
//                    return super.onTouchEvent(widget, buffer, event);
                    return true;

                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_UP:

                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);


                    if (link.length != 0) {

                        ClickableSpan cs = link[0];
                        int csStart = buffer.getSpanStart(cs);
                        int csEnd = buffer.getSpanEnd(cs);

                        int top = layout.getLineTop(line);
                        int bottom = layout.getLineBottom(line);
                        float eventY = event.getY() - widget.getTotalPaddingTop();

//                        System.out.println("点击之前：" + line + " " + csStart + " " + csEnd + " " + off + " " + link.length + " " + top + " " + bottom + " " + eventY);
                        if (off >= csStart && off < csEnd && eventY >= top && eventY <= bottom) {
                            if (action == MotionEvent.ACTION_UP) {
                                //移除按下的背景颜色
                                BackgroundColorSpan[] spans = buffer.getSpans(0, buffer.length(),
                                        BackgroundColorSpan.class);
                                for (BackgroundColorSpan span : spans) {
                                    buffer.removeSpan(span);
                                    widget.setText(buffer);
                                }


                                String url = null;
                                if (link[0] instanceof LinkClickable) {
                                    url = ((LinkClickable) link[0]).getUrl();
                                }

                                if (mOnLinkClickListener == null || !mOnLinkClickListener.onLinkClick(mLinkTextView, url, event)) {
                                    link[0].onClick(widget);
                                }

//                                System.out.println("点击到链接：" + url + " " + csStart + " " + csEnd + " " + off + " " + link.length + " " + top + " " + bottom);
                            } else {
                                //增加按下的背景颜色
                                BackgroundColorSpan span = new BackgroundColorSpan(
                                        widget.getResources().getColor(R.color.color_selected));
                                buffer.setSpan(span, csStart, csEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                                widget.setText(buffer);
                                Selection.setSelection(buffer,
                                        buffer.getSpanStart(link[0]),
                                        buffer.getSpanEnd(link[0]));
                            }


                        } else {
                            if (action == MotionEvent.ACTION_UP) {
                                if (mOnLinkClickListener == null || !mOnLinkClickListener.onNormalClick(mLinkTextView, event)) {

                                }
                            }
                        }

                        return true;
                    } else {
                        Selection.removeSelection(buffer);
                        Touch.onTouchEvent(widget, buffer, event);
                        if (action == MotionEvent.ACTION_UP) {
                            if (mOnLinkClickListener == null || !mOnLinkClickListener.onNormalClick(mLinkTextView, event)) {

                            }
                        }
                        return false;
                    }

            }

            return Touch.onTouchEvent(widget, buffer, event);
        }
    }

    public static class LinkSpannableString extends SpannableString {

        public LinkSpannableString(CharSequence source) {
            super(source);
            String s = source.toString();
            if (s.contains("@")) {
                parseEmailLink(s);
            } else {
                gatherLink(source);
            }
        }

        private void parseEmailLink(String source) {
            String[] ss = source.split(" " + "|" + "\\n");
            Matcher matcher;
            for (String s1 : ss) {
                if (s1.contains("@")) {
                    Pattern emailPattern = Pattern.compile(_EMIAL);
                    matcher = emailPattern.matcher(s1);
                } else {
                    Pattern emailPattern = Pattern.compile(URL_REGEX);
                    matcher = emailPattern.matcher(s1);
                }
                boolean result = matcher.find();
                while (result) {
                    final String find = matcher.group();
                    int start = matcher.start();
                    int end = matcher.end();
                    int index = source.indexOf(s1);
                    final String url = find;
                    setSpan(new LinkClickable(url, new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (url.contains("@")) {
                                //TODO
//                            Utils.sendMail( , url , null , null);
                            } else {
//                            WebUtils.openUrlInCommon(, url);
                            }
                        }
                    }), start + index, end + index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    result = matcher.find();
                }
            }
        }

        private void gatherLink(CharSequence source) {
            Pattern defaultPattern = Pattern.compile(URL_REGEX);
            Matcher matcher = defaultPattern.matcher(source);

            boolean result = matcher.find();
            while (result) {
                final String find = matcher.group();

                int start = matcher.start();
                int end = matcher.end();

                int length = find.length();
                int index = -1;
                for (int i = 0; i < length; i++) {
                    if (find.charAt(i) >= 127) {
                        index = i;
                        break;
                    }
                }
                final String url;
                if (index > 0) {
                    url = find.substring(0, index);
                    end = start + url.length();
                } else {
                    url = find;
                }

                setSpan(new LinkClickable(url, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                  TODO
//     v -> WebUtils.openUrlInCommon(, url)
                    }
                }), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                result = matcher.find();
            }

        }

    }

    public static class LinkClickable extends ClickableSpan {

        private final View.OnClickListener mListener;

        private String mUrl;

        public LinkClickable(String url, View.OnClickListener l) {
            mUrl = url;
            mListener = l;
        }

        public String getUrl() {
            return mUrl;
        }

        @Override
        public void onClick(View widget) {
            mListener.onClick(widget);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(Color.parseColor("#FF00a8ff"));
        }
    }

}