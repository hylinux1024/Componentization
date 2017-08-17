package net.angrycode.toolkit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import net.angrycode.core.R;

public class UrlUtils {
    /**
     * 默认的scheme跳转逻辑
     */
    public void defaultSchemeJump(Context context, String scheme) {
        if (!TextUtils.isEmpty(scheme)) {
            Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme));
            if (in.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(in);
                return;
            }
        }
        Toast.makeText(context, R.string.error_app_not_install, Toast.LENGTH_SHORT).show();
    }
}