package net.angrycode.mvp;

/**
 * Created by wecodexyz on 2017/8/17.
 */

public class DemoPresenter extends BasePresenter<DemoContract.View> implements DemoContract.Presenter {
    public DemoPresenter(DemoContract.View view) {
        super(view);
    }

    @Override
    public void getData() {
        view.onBegin();
        view.onGetDataFinished("");
        view.onFinished();
    }
}
