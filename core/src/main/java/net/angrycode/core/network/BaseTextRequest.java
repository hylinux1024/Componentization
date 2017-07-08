package net.angrycode.core.network;

import android.content.Context;
import android.util.Pair;

import org.reactivestreams.Publisher;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

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

    @Override
    public boolean isSupportCache() {
        return true;
    }

    protected abstract T onRequestFinish(String result);

    protected abstract T onRequestError(int code, String message);
}