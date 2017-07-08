package net.angrycode.core.network;

import android.util.Pair;

import java.util.Map;

/**
 * IRequest
 * Network request interface
 * Created by lancelot on 2017/7/7.
 */

public interface IRequest {
    enum HttpMethod {
        GET, POST, PUT, DELETE
    }

    Map<String, String> getParams();

    void setParams(Map<String, String> params);

    void addParams(Map<String, String> params);

    void putParam(String key, String value);

    String getUrl();

    Pair<Integer, String> doRequest();

    boolean isSupportCache();

    void setMaxRetries(int retry);

    void setCookie(String cookie);

    void removeCookie();

    void addHeader(String key, String value);

    void removeHeader(String key);

    void setContentType(String contentType);

    void setUserAgent(String userAgent);

    String getUserAgent();

    HttpMethod getHttpMethod();

    void setProxy(String host, int port);

}