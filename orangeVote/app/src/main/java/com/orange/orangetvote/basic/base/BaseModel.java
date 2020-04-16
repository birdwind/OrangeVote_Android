package com.orange.orangetvote.basic.base;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseModel<S extends BaseService> {

    public CompositeDisposable mDisposable = new CompositeDisposable();

    /**
     * 初始化網路請求
     */
    protected abstract S apiService();

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
