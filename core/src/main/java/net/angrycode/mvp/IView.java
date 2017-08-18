package net.angrycode.mvp;

import android.app.Activity;

/**
 * Created by wecodexyz on 2017/8/16.
 */

public interface IView<T> {
    Activity getActivity();

    /**
     * 请求开始
     */
    void onBegin();

    /**
     * 请求结束
     */
    void onFinished();

    /**
     * 请求出错
     * @param errorCode
     * @param message
     */
    void onError(int errorCode, String message);
}
