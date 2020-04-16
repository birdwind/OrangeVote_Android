package com.orange.orangetvote.basic.utils.rxHelper;

import com.orange.orangetvote.basic.base.BaseBean;
import com.orange.orangetvote.basic.base.MvpCallback;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class RxObservable<T extends BaseBean> implements Observer<T>, MvpCallback {

    public RxObservable() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        LogUtils.print("RxObservable", d.toString());
    }

    @Override
    public void onError(Throwable e) {
        String error = RxException.handleException(e).getMessage();
        ToastUtils.show(error);
        this.onFail(error);
    }

    @Override
    public void onNext(T value) {
        //再根據需求對code統一處理
        this.onSuccess(value);
    }

    @Override
    public void onComplete() {

    }
}
