package net.angrycode.core.network;

import android.util.Pair;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Network request using rxjava2
 * Created by lancelot on 2017/7/7.
 */

public abstract class BaseTextRequest<T> extends RequestWrapper {

    public BaseTextRequest() {

    }

    public Observable<T> doRequest() {
        Pair<Integer, String> result = super.request();
        return Observable.just(result)
                .flatMap(new Function<Pair<Integer, String>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull Pair<Integer, String> pair) throws Exception {
                        if (isSuccessful(pair.first)) {
                            return Observable.just(onRequestFinish(pair.second));
                        }
                        return Observable.just(onRequestError(pair.first, pair.second));
                    }
                });

    }

    abstract T onRequestFinish(String result);

    abstract T onRequestError(int code, String message);
}