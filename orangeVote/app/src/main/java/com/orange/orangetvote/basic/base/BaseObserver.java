package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.basic.utils.rxHelper.RxException;

import java.io.IOException;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public abstract class BaseObserver<T> extends DisposableObserver<T> {

    protected BaseView view;

    public BaseObserver(BaseView view) {
        this.view = view;
    }

    @Override
    protected void onStart() {
        if (view != null) {
            view.showLoading();
        }
    }

    @Override
    public void onNext(T o) {
        try {
            onSuccess((ResponseBody) o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if(view != null){
            view.hideLoading();
        }
        if(e != null){
            onError(RxException.handleException(e).getMessage());
        } else {
            onError("未知錯誤");
        }
    }

    @Override
    public void onComplete() {
        if(view != null){
            view.hideLoading();
        }
    }

    public abstract void onSuccess(ResponseBody responseBody) throws IOException;

    public abstract void onError(String msg);
}
