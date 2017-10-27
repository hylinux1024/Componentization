package net.angrycode.toolkit.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * <h2>Utility methods that make working with views easier, less error prone, and more concise.</h2>
 * <p/>
 * <h3>Common uses:</h3>
 * <code>TextView textView = ViewUtils.{@link #findViewById findViewById}(this, R.id.my_text_view);</code> //<b>no more casting!</b><br />
 * <code>TextView textView = ViewUtils.{@link #findViewById findViewById}(parentView, R.id.my_text_view);</code> //<b>no more casting!</b><br />
 * <code>String text = ViewUtils.{@link #getText getText}(this, R.id.my_text_view);</code><br />
 * <code>ViewUtils.{@link #setText setText}(this, R.id.my_text_vew, "new text");</code><br />
 * <code>ViewUtils.{@link #setText setText}(textView, "new text");</code><br />
 * <code>ViewUtils.{@link #appendText appendText}(textView, "appended");</code><br />
 * <code>ViewUtils.{@link #hideView hideView}(this, R.id.my_text_view);</code><br />
 * <code>ViewUtils.{@link #showView showView}(this, R.id.my_text_view);</code><br />
 * <code>Bitmap bitmap = ViewUtils.{@link #viewToImage viewToImage}(this, R.id.my_layout);</code><br />
 * <code>ViewUtils.{@link #closeKeyboard showKeyboard}(this, R.id.my_text_view);</code><br />
 * <code>ViewUtils.{@link #closeKeyboard closeKeyboard}(this, R.id.my_text_view);</code><br />
 * <br />
 */
public class ViewUtils {

    /**
     * Utility method to make getting a View via findViewById() more safe & simple.
     * <p/>
     * - Casts view to appropriate type based on expected return value
     * - Handles & logs invalid casts
     *
     * @param context The current Context or Activity that this method is called from
     * @param id      R.id value for view
     * @return View object, cast to appropriate type based on expected return value.
     * @throws ClassCastException if cast to the expected type breaks.
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(Activity context, int id) {
        T view = null;
        View genericView = context.findViewById(id);
        try {
            view = (T) (genericView);
        } catch (Exception ex) {
            String message = "Can't cast view (" + id + ") to a " + view.getClass() + ".  Is actually a " + genericView.getClass() + ".";
            Log.e("PercolateAndroidUtils", message);
            throw new ClassCastException(message);
        }

        return view;
    }

    /**
     * Utility method to make getting a View via findViewById() more safe & simple.
     * <p/>
     * - Casts view to appropriate type based on expected return value
     * - Handles & logs invalid casts
     *
     * @param parentView Parent View containing the view we are trying to get
     * @param id         R.id value for view
     * @return View object, cast to appropriate type based on expected return value.
     * @throws ClassCastException if cast to the expected type breaks.
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(View parentView, int id) {
        T view = null;
        View genericView = parentView.findViewById(id);
        try {
            view = (T) (genericView);
        } catch (Exception ex) {
            String message = "Can't cast view (" + id + ") to a " + view.getClass() + ".  Is actually a " + genericView.getClass() + ".";
            Log.e("PercolateAndroidUtils", message);
            throw new ClassCastException(message);
        }

        return view;
    }

    /**
     * Get text as String from EditView.
     * <b>Note:</b> returns "" for null EditText, not a NullPointerException
     *
     * @param view EditView to get text from
     * @return the text
     */
    public static String getText(TextView view) {
        String text = "";
        if (view != null) {
            text = view.getText().toString();
        } else {
            Log.e("PercolateAndroidUtils", "Null view given to getText().  \"\" will be returned.");
        }
        return text;
    }

    /**
     * Get text as String from EditView.
     * <b>Note:</b> returns "" for null EditText, not a NullPointerException
     *
     * @param context The current Context or Activity that this method is called from
     * @param id      Id for the TextView/EditView to get text from
     * @return the text
     */
    public static String getText(Activity context, int id) {
        TextView view = findViewById(context, id);

        String text = "";
        if (view != null) {
            text = view.getText().toString();
        } else {
            Log.e("PercolateAndroidUtils", "Null view given to getText().  \"\" will be returned.");
        }
        return text;
    }

    /**
     * Append given text String to the provided view (one of TextView or EditText).
     *
     * @param view     View to update
     * @param toAppend String text
     */
    public static void appendText(TextView view, String toAppend) {
        String currentText = getText(view);
        view.setText(currentText + toAppend);
    }

    /**
     * Go away keyboard, nobody likes you.
     *
     * @param context The current Context or Activity that this method is called from
     * @param field   field that holds the keyboard focus
     */
    public static void closeKeyboard(Context context, View field) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(field.getWindowToken(), 0);
        } catch (Exception ex) {
            Log.e("PercolateAndroidUtils", "Error occurred trying to hide the keyboard.  Exception=" + ex);
        }
    }

    /**
     * Convert view to an image.  Can be used to make animations smoother.
     *
     * @param context           The current Context or Activity that this method is called from
     * @param viewToBeConverted View to convert to a Bitmap
     * @return Bitmap object that can be put in an ImageView.  Will look like the converted viewToBeConverted.
     */
    public static Bitmap viewToImage(Context context, WebView viewToBeConverted) {
        int extraSpace = 2000; //because getContentHeight doesn't always return the full screen height.
        int height = viewToBeConverted.getContentHeight() + extraSpace;

        Bitmap viewBitmap = Bitmap.createBitmap(viewToBeConverted.getWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(viewBitmap);
        viewToBeConverted.draw(canvas);

        //If the view is scrolled, cut off the top part that is off the screen.
        try {
            int scrollY = viewToBeConverted.getScrollY();
            if (scrollY > 0) {
                viewBitmap = Bitmap.createBitmap(viewBitmap, 0, scrollY, viewToBeConverted.getWidth(), height - scrollY);
            }
        } catch (Exception ex) {
            Log.e("PercolateAndroidUtils", "Could not remove top part of the webview image.  ex=" + ex);
        }

        return viewBitmap;
    }

    /**
     * Method used to set text for a TextView
     *
     * @param context The current Context or Activity that this method is called from
     * @param field   R.id.xxxx value for the text field.
     * @param text    Text to place in the text field.
     */
    public static void setText(Activity context, int field, String text) {
        View view = context.findViewById(field);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        } else {
            Log.e("PercolateAndroidUtils", "ViewUtils.setText() given a field that is not a TextView");
        }
    }

    /**
     * Method used to set text for a TextView
     *
     * @param parentView The View used to call findViewId() on
     * @param field      R.id.xxxx value for the text field.
     * @param text       Text to place in the text field.
     */
    public static void setText(View parentView, int field, String text) {
        View view = parentView.findViewById(field);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        } else {
            Log.e("PercolateAndroidUtils", "ViewUtils.setText() given a field that is not a TextView");
        }
    }

    /**
     * Sets visibility of the given view to <code>View.GONE</code>.
     *
     * @param context The current Context or Activity that this method is called from
     * @param id      R.id.xxxx value for the view to hide"expected textView to throw a ClassCastException" + textView
     */
    public static void hideView(Activity context, int id) {
        if (context != null) {
            View view = context.findViewById(id);
            if (view != null) {
                view.setVisibility(View.GONE);
            } else {
                Log.e("PercolateAndroidUtils", "View does not exist.  Could not hide it.");
            }
        }
    }

    /**
     * Sets visibility of the given view to <code>View.VISIBLE</code>.
     *
     * @param context The current Context or Activity that this method is called from
     * @param id      R.id.xxxx value for the view to show
     */
    public static void showView(Activity context, int id) {
        if (context != null) {
            View view = context.findViewById(id);
            if (view != null) {
                view.setVisibility(View.VISIBLE);
            } else {
                Log.e("PercolateAndroidUtils", "View does not exist.  Could not hide it.");
            }
        }
    }

    /**
     * Set visibility of given view to be gone or visible
     * <p>
     * This method has no effect if the view visibility is currently invisible
     *
     * @param view
     * @param gone
     * @return view
     */
    public static <V extends View> V setGone(final V view, final boolean gone) {
        if (view != null)
            if (gone) {
                if (GONE != view.getVisibility())
                    view.setVisibility(GONE);
            } else {
                if (VISIBLE != view.getVisibility())
                    view.setVisibility(VISIBLE);
            }
        return view;
    }

    /**
     * Set visibility of given view to be invisible or visible
     * <p>
     * This method has no effect if the view visibility is currently gone
     *
     * @param view
     * @param invisible
     * @return view
     */
    public static <V extends View> V setInvisible(final V view,
                                                  final boolean invisible) {
        if (view != null)
            if (invisible) {
                if (INVISIBLE != view.getVisibility())
                    view.setVisibility(INVISIBLE);
            } else {
                if (VISIBLE != view.getVisibility())
                    view.setVisibility(VISIBLE);
            }
        return view;
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Generate a value suitable for use in {@link #setId(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        }
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
    public static final int OPGL_MAX_TEXTURE = 4096;
    private static long lastLauchTime;

    /**
     *  防止快速点击动态缩略图后显示空白
     */
    public static boolean isFastClickWhenTransition(){
        long now = System.currentTimeMillis();
        boolean isFast = now - lastLauchTime < 1000;
        lastLauchTime = now;
        return isFast;
    }
}
