package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.basic.utils.rxHelper.RxException;

import io.reactivex.observers.DisposableObserver;

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
//        try {
//            BaseModel model = (BaseModel) o;
//            if (model.getErrorCode() == 0) {
//                onSuccess(o);
//            } else {
//                if (view != null) {
//                    view.onErrorCode(model.getErrorCode(), model.getErrorMsg());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            onError(e.toString());
//        }
        onSuccess(o);
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

    public abstract void onSuccess(T o);

    public abstract void onError(String msg);
}
