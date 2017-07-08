package net.angrycode.core.network;

/**
 * Created by lancelot on 2017/7/8.
 */

public class SimpleTextRequest extends BaseTextRequest<String> {
    public SimpleTextRequest() {
    }

    @Override
    public String getUrl() {
        return null;
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
