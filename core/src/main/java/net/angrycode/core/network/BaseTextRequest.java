package net.angrycode.core.network;

import android.content.Context;
import android.util.Pair;

import org.reactivestreams.Publisher;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Network request using rxjava2
 * Created by lancelot on 2017/7/7.
 */

public abstract class BaseTextRequest<T> extends RequestWrapper {

    public BaseTextRequest(Context context) {
        super(context);
    }

    public Flowable<T> request() {
        return Flowable.fromCallable(new Callable<Pair<Integer, String>>() {
            @Override
            public Pair<Integer, String> call() throws Exception {
                Pair<Integer, String> result = doRequest();
                return result;
            }
        }).flatMap(new Function<Pair<Integer, String>, Publisher<T>>() {
            @Override
            public Publisher<T> apply(@NonNull Pair<Integer, String> pair) throws Exception {
                if (isSuccessful(pair.first)) {
                    return Flowable.just(onRequestFinish(pair.second));
                }
                return Flowable.just(onRequestError(pair.first, pair.second));
            }
        });

    }

    public void request(final OnRequestCallback<T> callback) {
        request().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(@NonNull T t) throws Exception {
                        callback.callback(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        callback.onError(new Exception(throwable));
                    }
                });
    }


    @Override
    public boolean isSupportCache() {
        return true;
    }

    protected abstract T onRequestFinish(String result);

    protected abstract T onRequestError(int code, String message);

    public interface OnRequestCallback<T> {
        void callback(T t);

        void onError(Exception e);
    }

}