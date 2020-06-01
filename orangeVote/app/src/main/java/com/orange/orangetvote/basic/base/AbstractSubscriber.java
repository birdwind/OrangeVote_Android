package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.MainApplication;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.response.AbstractResponse;
import com.orange.orangetvote.basic.response.BaseResponse;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.rxHelper.RxException;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import android.content.Context;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;

public abstract class AbstractSubscriber<T extends ResponseBody, RS extends AbstractResponse, RD extends BaseResponse, FE extends FieldErrorResponse>
    extends DisposableSubscriber<T> implements BaseSubscriber<RD, FE> {

    protected BaseView view;

    private Context context = MainApplication.getAppContext();

    private boolean isShowDialog;

    private Class<RS> clazz;

    public AbstractSubscriber(BaseView view) {
        this.view = view;
        this.clazz = clazz;
    }

    public AbstractSubscriber(BaseView view, boolean isShowDialog) {
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
            String responseJson = t.string();
            ServerResponse serverResponse = GsonUtils.parseJsonToBean(responseJson, ServerResponse.class);
            RS response = GsonUtils.parseJsonToBean(responseJson, clazz);
            if (serverResponse != null && response != null) {
                switch (serverResponse.getErrorCode()) {
                    case 0:
                        // 成功
                        onSuccess(response.getResponse());
                        break;
                    case 1:
                        onError(context.getString(R.string.error_server_permission));
                        // 沒有權限
                        break;
                    case 2:
                        // 尚未登入
                    case 9:
                        // 登入紀錄過期
                        view.onLoginError();
                        break;
                    case 6:
                        onResponseFieldError(response.getResponseFieldError());
                        // 送出資料不正確
                        break;
                    case 8:
                        onError(context.getString(R.string.error_server_login));
                        // 登入失敗
                        break;

                    case 11:
                        onResponseError(response.getResponseError());
                    default:
                        // 3.沒找到資源 4.API找不到 5.伺服器錯誤 7.伺服器錯誤 10.少傳欄位
                        onError(context.getString(R.string.error_server_backend));
                        LogUtils.e(context.getString(R.string.error_server_error) + serverResponse.getErrorCode());
                        break;

                }
            } else {
                onError(context.getString(R.string.error_undefined));
                LogUtils.e(context.getString(R.string.error_undefined) + " ServerResponse is Null");
            }
        } catch (Exception e) {
            LogUtils.exception(e);
//            e.printStackTrace();
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

                // 回调到view层 处理 或者根据项目情况处理
                if (view != null) {
                    view.onErrorCode(RxException.handleException(e).getCode(),
                        RxException.handleException(e).getMessage());
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

    @Override
    public void onError(String msg) {
        view.showError(msg);
    }
}
