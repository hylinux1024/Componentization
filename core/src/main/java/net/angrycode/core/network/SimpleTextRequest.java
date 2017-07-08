package net.angrycode.core.network;

import java.util.Map;

/**
 * Created by lancelot on 2017/7/8.
 */

public class SimpleTextRequest extends BaseTextRequest<String> {

    public SimpleTextRequest(Map<String, String> params) {
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
    String onRequestFinish(String result) {
        return result;
    }

    @Override
    String onRequestError(int code, String message) {
        return message;
    }
}
