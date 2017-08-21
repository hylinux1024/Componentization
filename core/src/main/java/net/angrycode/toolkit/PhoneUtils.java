package net.angrycode.toolkit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Html;

import java.io.File;

/**
 * Created by wecodexyz on 2017/8/21.
 */

public class PhoneUtils {
    /**
     * <uses-permission android:name="android.permission.CALL_PHONE" />
     *
     * @param context
     * @param phoneNumber
     */
    public static void call(Context context, String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (callIntent.resolveActivity(context.getPackageManager()) != null) {
            if (context instanceof Activity) {

            } else {
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(callIntent);
        }
    }

    /**
     * open phone app，dont need permission
     *
     * @param context
     * @param phoneNumber
     */
    public static void dial(Context context, String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (callIntent.resolveActivity(context.getPackageManager()) != null) {
            if (context instanceof Activity) {

            } else {
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(callIntent);
        }
    }

    /**
     * open email client
     *
     * @param context
     * @param email
     * @param subject
     * @param body
     */
    public static void sendMail(Context context, String email, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            if (context instanceof Activity) {

            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(Intent.createChooser(intent, ""));
        }
    }

    /**
     * open email client
     *
     * @param context
     * @param email
     * @param subject
     * @param body
     */
    public static void mailto(Context context, String email, String subject, String body) {
        String uriText =
                "mailto:" + email +
                        "?subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(body);

        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        if (sendIntent.resolveActivity(context.getPackageManager()) != null) {
            if (context instanceof Activity) {

            } else {
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(Intent.createChooser(sendIntent, "Send email"));
        }
    }

    /**
     * Launch a website in the phone browser
     *
     * @param context
     * @param url
     */
    public static void launchWebsite(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (browserIntent.resolveActivity(context.getPackageManager()) != null) {
            if (context instanceof Activity) {

            } else {
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(browserIntent);
        }
    }

    /**
     * @param context
     */
    public static void openOnGooglePlay(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + context.getPackageName()));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            if (context instanceof Activity) {

            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }

    /**
     * @param context
     * @param latitude
     * @param longitude
     */
    public static void showLocationInMaps(Context context, String latitude, String longitude) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String data = String.format("geo:%s,%s", latitude, longitude);
//        if (zoomLevel != null) {
//            data = String.format("%s?z=%s", data, zoomLevel);
//        }
        intent.setData(Uri.parse(data));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            if (context instanceof Activity) {

            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }

    /**
     * @param context
     * @param file
     */
    public static void capturePhoto(Context context, String file) {
        Uri uri = Uri.fromFile(new File(file));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            if (context instanceof Activity) {

            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }

    /**
     * String text = "Look at my awesome picture";
     * Uri pictureUri = Uri.parse("file://my_picture");
     * Intent shareIntent = new Intent();
     * shareIntent.setAction(Intent.ACTION_SEND);
     * shareIntent.putExtra(Intent.EXTRA_TEXT, text);
     * shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
     * shareIntent.setType("image/*");
     * shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
     * startActivity(Intent.createChooser(shareIntent, "Share images..."));
     */
    public static void shareImage(Context context, String filePath, String fileName) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/jpg");
        Uri uri = Uri.fromFile(new File(filePath, fileName));
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri.toString());
        if (sharingIntent.resolveActivity(context.getPackageManager()) != null) {
            if (context instanceof Activity) {

            } else {
                sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));
        }

    }

    public static void shareText(Context context, String text) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>" + text + "</p>"));
        if (sharingIntent.resolveActivity(context.getPackageManager()) != null) {
            if (context instanceof Activity) {

            } else {
                sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
        }
    }
}
