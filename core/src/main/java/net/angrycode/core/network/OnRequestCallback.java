package net.angrycode.core.network;

public interface OnRequestCallback<T> {
    void callback(T t);

    void onError(Exception e);
}