package net.angrycode.mvp;

/**
 * just show how to define contract
 * Created by huangyanglin on 2017/8/16.
 */

public interface SimpleContract {

    interface Presenter extends IPresenter {
        void doSomething();
    }

    interface View extends IView<Presenter> {

        void onRequestFinish(Object result);
    }
}
