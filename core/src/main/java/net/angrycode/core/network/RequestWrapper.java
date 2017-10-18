package net.angrycode.core.network;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttpClient Wrapper
 * Created by lancelot on 2017/7/7.
 */

public abstract class RequestWrapper extends Request {

    protected static final int ERROR_NETWORK = 404;

    protected static final int CACHE_SIZE = 10 * 1024 * 1024; // 10 MiB

    private WeakReference<Context> mContextRef;

    OkHttpClient mClient;
    Proxy mProxy;

    Cache mCache;

    public RequestWrapper(Context context) {
        mContextRef = new WeakReference<>(context);
        if (isSupportCache()) {
            mCache = new Cache(context.getCacheDir(), CACHE_SIZE);
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .proxy(mProxy)
                .cache(mCache);
        mClient = builder.build();
    }

    @Override
    public Pair<Integer, String> doRequest() {
        Pair<Integer, String> result = new Pair<>(ERROR_NETWORK, "network is not connect!");
        okhttp3.Request request = null;

        if (getHttpMethod() == HttpMethod.POST) {
            request = requestBuilder().url(getUrl()).post(requestBody()).build();
        } else {
            request = requestBuilder().url(getUrlWithParams()).build();
        }
        try {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
                result = new Pair<>(response.code(), response.body().string());
            } else {
                result = new Pair<>(response.code(), response.message());
            }
            Log.d("Network", "request url - " + getUrlWithParams());
            Log.d("Network", "response json - " + result.second);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }

    public boolean isSuccessful(int code) {
        return code >= 200 && code < 300;
    }

    private RequestBody requestBody() {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mParams.keySet()) {
            builder.add(key, mParams.get(key));
        }
        return builder.build();
    }

    private okhttp3.Request.Builder requestBuilder() {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        for (String key : mHeaders.keySet()) {
            builder.addHeader(key, mHeaders.get(key));
        }
        return builder;
    }

    @Override
    public void setProxy(String host, int port) {
        mProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
    }

    public
    @Nullable
    Context getContext() {
        return mContextRef.get();
    }

}
