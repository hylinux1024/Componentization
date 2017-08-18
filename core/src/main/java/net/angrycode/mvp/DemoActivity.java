package net.angrycode.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wecodexyz on 2017/8/18.
 */

public class DemoActivity extends AppCompatActivity implements DemoContract.View {

    DemoPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DemoPresenter(this);
        mPresenter.getData();//请求数据
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onGetDataFinished(String data) {
        // 这里获取到数据
    }

    @Override
    public void onBegin() {
        //请求开始，可以显示loading等操作
    }

    @Override
    public void onFinished() {
        //请求结束，取消loading等操作
    }

    @Override
    public void onError(int errorCode, String message) {
        //处理出错
    }
}
