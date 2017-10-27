package net.angrycode.core.network;

import android.content.Context;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by wecodexyz@gmail.com on 2017/10/26 上午11:07.
 * GitHub - https://github.com/wecodexyz
 * Description:
 */

public abstract class BaseMultipartRequest<T> extends RequestWrapper {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public BaseMultipartRequest(Context context) {
        super(context);
    }

    protected RequestBody requestBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(super.requestBody())
                .addFormDataPart(getName(), getFileName(),
                        RequestBody.create(MEDIA_TYPE_PNG, new File(getFilePath())));
        return builder.build();
    }

    protected abstract String getFilePath();

    protected abstract String getName();

    protected abstract String getFileName();

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }
}
