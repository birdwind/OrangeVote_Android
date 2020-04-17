package com.orange.orangetvote.basic.base2;

import com.orange.orangetvote.basic.utils.rxHelper.RxException;

import io.reactivex.subscribers.DisposableSubscriber;

public abstract class BaseSubscriber<T> extends DisposableSubscriber<T> {


    protected BaseView view;

    private boolean isShowDialog;

    public BaseSubscriber(BaseView view) {
        this.view = view;
    }

    public BaseSubscriber(BaseView view, boolean isShowDialog) {
        this.view = view;
        this.isShowDialog = isShowDialog;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (view != null && isShowDialog) {
            view.showLoading();
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (view != null && isShowDialog) {
            view.hideLoading();
        }
        RxException rxException = null;
        String errorMessage = "";

        if (e != null) {
            if (e instanceof RxException) {
                rxException = (RxException) e;

                //回调到view层 处理 或者根据项目情况处理
                if (view != null) {
                    view.onErrorCode(RxException.handleException(e).getCode(), RxException.handleException(e).getMessage());
                } else {
                    onError(RxException.handleException(e).getMessage());
                }

            } else {
                errorMessage = RxException.handleException(e).getMessage();
            }
        } else {
            errorMessage = "未知錯誤";
        }

        onError(errorMessage);
    }

    @Override
    public void onComplete() {
        if (view != null && isShowDialog) {
            view.hideLoading();
        }
    }

    public abstract void onSuccess(T o);

    public abstract void onError(String msg);
}