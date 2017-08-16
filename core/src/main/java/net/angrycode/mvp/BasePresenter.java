package net.angrycode.mvp;

import android.app.Activity;

import net.angrycode.core.R;

/**
 * Created by wecodexyz on 2017/8/16.
 */

public class BasePresenter<T extends IView> implements IPresenter {

    public static final int ERROR_RX = 2000;

    public T view;

//    public DisposableContainer subscription = new CompositeDisposable();

    public BasePresenter(T view) {
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    Activity getActivity() {
        return view.getActivity();
    }

    /**
     * 用于判断当前view是否已经退出
     *
     * @return
     */
    public boolean isViewDetached() {
        if (view == null) {
            return true;
        }
        if (view.getActivity() == null) {
            return true;
        }

        if (view.getActivity().isFinishing()) {
            return true;
        }
//        if (subscription == null) {
//            return true;
//        }
//        if (subscription.) {
//            return true;
//        }
        return false;
    }

    public String getRxErrorText() {
        return view.getActivity().getString(R.string.error_network);
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
