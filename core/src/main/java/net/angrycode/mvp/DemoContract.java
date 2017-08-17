package net.angrycode.mvp;

/**
 * Created by huangyanglin on 2017/8/17.
 */

public interface DemoContract {

    interface View extends IView {
        void onGetDataFinished(String data);
        //other callbacks
    }

    interface Presenter extends IPresenter {
        void getData();
        //other mehtods
    }
}
