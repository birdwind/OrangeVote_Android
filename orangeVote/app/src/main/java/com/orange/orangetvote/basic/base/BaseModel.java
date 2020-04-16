package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.basic.utils.rxHelper.RxObservable;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseModel<S extends BaseService> {

    public CompositeDisposable mDisposable = new CompositeDisposable();

    /**
     * 初始化網路請求
     */
    protected abstract S apiService();

    protected abstract void mVote(RxObservable rxObservable);

    /**
     * 取消網路請求
     */
    public void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable.clear();
        }
    }

}
