package net.angrycode.core.network;

import android.content.Context;

import java.util.Map;

/**
 * Created by lancelot on 2017/7/8.
 */

public class SimpleTextRequest extends BaseTextRequest<String> {

    public SimpleTextRequest(Context context, Map<String, String> params) {
        super(context);
        addParams(params);
    }

    @Override
    public String getUrl() {
        return "https://raw.githubusercontent.com/wecodexyz/Componentization/master/README.md";
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    protected String onRequestFinish(String result) {
        return result;
    }

    @Override
    protected String onRequestError(int code, String message) {
        return message;
    }
}
