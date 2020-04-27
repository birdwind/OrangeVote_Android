package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.MainApplication;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.rxHelper.RxException;

import java.io.IOException;
import android.content.Context;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;

public abstract class BaseSubscriber<T> extends DisposableSubscriber<T> {


    protected BaseView view;

    private Context context = MainApplication.getAppContext();

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
        try {
            String responseJson = ((ResponseBody) t).string();
            ServerResponse serverResponse = GsonUtils.parseJsonToBean(responseJson, ServerResponse.class);
            switch (serverResponse.getErrorCode()) {
                case 0:
                    // 成功
                    onSuccess(responseJson);
                    break;
                case 1:
                    onError(context.getString(R.string.error_server_permission));
                    // 沒有權限
                    break;
                case 2:
                    view.onLoginError();
                    // 尚未登入
                    break;
                case 6:
                    onFieldsError(responseJson);
                    // 送出資料不正確
                    break;
                case 8:
                    onError(context.getString(R.string.error_server_login));
                    // 登入失敗
                    break;
                case 9:
                    // 登入紀錄過期
                    view.onLoginError();
                    break;
                default:
                    //3.沒找到資源 4.API找不到 5.伺服器錯誤 7.伺服器錯誤 10.少傳欄位
                    onError(context.getString(R.string.error_server_backend));
                    LogUtils.e("伺服器錯誤 : " + String.valueOf(serverResponse.getErrorCode()));
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    protected abstract void onSuccess(String o);

    protected abstract void onError(String msg);

    protected abstract void onFieldsError(String responseJson);
}