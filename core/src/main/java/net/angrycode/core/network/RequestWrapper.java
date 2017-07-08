package net.angrycode.core.network;

import android.util.Log;
import android.util.Pair;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttpClient Wrapper
 * Created by lancelot on 2017/7/7.
 */

public abstract class RequestWrapper extends Request {

    static final int ERROR_NETWORK = 404;

    OkHttpClient mClient;
    Proxy mProxy;

    public RequestWrapper() {
        mClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .proxy(mProxy)
                .build();
    }

    @Override
    public Pair<Integer, String> request() {
        Pair<Integer, String> result = new Pair<>(ERROR_NETWORK, "");
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


}
