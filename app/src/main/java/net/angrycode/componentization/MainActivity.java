package net.angrycode.componentization;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.angrycode.core.network.SimpleTextRequest;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    TextView textView;
    SimpleTextRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        textView = (TextView) findViewById(R.id.tv_content);
//        request = new SimpleTextRequest(this, null);
//        request.request()
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        textView.setText(s);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        textView.setText(throwable.getMessage());
//                    }
//                });
//        Flowable.fromCallable(new Callable<List<Data>>() {
//            @Override
//            public List<Data> call() throws Exception {
//                List<Data> dataList = RepositoryFactory.get().queryAll();
//                return dataList;
//            }
//        }).observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Consumer<List<Data>>() {
//                    @Override
//                    public void accept(@NonNull List<Data> datas) throws Exception {
//                        textView.setText("data size:" + datas.size());
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        textView.setText(throwable.getMessage());
//                    }
//                });
//        Flowable.just("one", "two", "three", "four", "five", "six")
//                .subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.single())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        Log.e(TAG, s);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        Log.e(TAG, throwable.getMessage());
//                    }
//                });
//
//        Flowable.fromCallable(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                Thread.sleep(3000);
//                return "from callable";
//            }
//        }).flatMap(new Function<String, Publisher<String>>() {
//            @Override
//            public Publisher<String> apply(@NonNull String s) throws Exception {
//                return Flowable.just(s + " flat map");
//            }
//        }).subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        Log.e(TAG, s);
//                        textView.setText(s);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        Log.e(TAG, throwable.getMessage());
//                    }
//                });
    }
}
