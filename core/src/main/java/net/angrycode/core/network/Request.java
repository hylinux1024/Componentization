package net.angrycode.core.network;

import android.support.annotation.NonNull;
import android.util.Log;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Network request architecture
 * Created by lancelot on 2017/7/7.
 */

public abstract class Request implements IRequest {

    public static final String TAG = Request.class.getSimpleName();

    Map<String, String> mHeaders = new HashMap<>();
    Map<String, String> mParams = new HashMap<>();
    int mMaxRetries = 0;
    String mCookie;
    String mContentType;
    String mUserAgent;

    public Request() {
    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params != null) {
            mParams.clear();
            mParams.putAll(params);
        }
    }

    @Override
    public void addParams(Map<String, String> params) {
        if (params != null)
            mParams.putAll(params);
    }

    @Override
    public void putParam(String key, String value) {
        mParams.put(key, value);
    }

    @Override
    public void removeCookie() {
        mCookie = null;
    }

    @Override
    public boolean isSupportCache() {
        return false;
    }

    @Override
    public void setMaxRetries(int retry) {
        mMaxRetries = retry;
    }

    @Override
    public void setCookie(String cookie) {
        mCookie = cookie;
    }

    @Override
    public void addHeader(@NonNull String key, @NonNull String value) {
        mHeaders.put(key, value);
    }

    @Override
    public void removeHeader(String key) {
        mHeaders.remove(key);
    }

    @Override
    public void setContentType(String contentType) {
        mContentType = contentType;
    }

    @Override
    public void setUserAgent(String userAgent) {
        mUserAgent = userAgent;
    }

    @Override
    public String getUserAgent() {
        return mUserAgent;
    }


    public String getUrlWithParams() {
        StringBuilder builder = new StringBuilder(getUrl());
        try {
            if (mParams.size() > 0) {
                builder.append("?");
            }
            int pos = 0;
            for (String key : mParams.keySet()) {
                if (pos > 0) {
                    builder.append("&");
                }
                builder.append(String.format("%s=%s", key, URLEncoder.encode(mParams.get(key), "utf-8")));
                pos++;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return builder.toString();
    }

    public class Builder {
        String url;
        Map<String, String> params = new HashMap<>();
        int maxRetries = 0;
        String cookie;
    }
}