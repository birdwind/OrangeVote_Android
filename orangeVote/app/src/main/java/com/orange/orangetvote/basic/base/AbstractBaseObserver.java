package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.MainApplication;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.response.AbstractResponse;
import com.orange.orangetvote.basic.response.BaseResponse;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.SharedPreferencesUtils;
import com.orange.orangetvote.basic.utils.rxHelper.RxException;
import android.content.Context;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public abstract class AbstractBaseObserver<T extends ResponseBody, RS extends AbstractResponse, RD extends BaseResponse>
    extends DisposableObserver<T> implements BaseObserver<RD> {

    protected BaseView view;

    protected Context context = MainApplication.getAppContext();

    private Class<RS> clazz;

    public AbstractBaseObserver(BaseView view, Class<RS> clazz) {
        this.view = view;
        this.clazz = clazz;
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
            String responseJson = o.string();
            ServerResponse serverResponse = GsonUtils.parseJsonToBean(responseJson, ServerResponse.class);
            String localVersion = SharedPreferencesUtils.get("version", "0");
            String version = serverResponse.getApiVersion();
            if (localVersion.equals("0")) {
                SharedPreferencesUtils.put("version", version);
            } else if (!version.equals(localVersion)) {
                onError(context.getString(R.string.error_version_not_match));
            }
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
                        break;
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
            onError(context.getString(R.string.error_undefined));
            // LogUtils.exception(e);
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.exception(e);
        // e.printStackTrace();
        if (view != null) {
            view.hideLoading();
        }
        onError(RxException.handleException(e).getMessage());
    }

    @Override
    public void onComplete() {
        if (view != null) {
            view.hideLoading();
        }
    }

    @Override
    public void onError(String msg) {
        view.showError(msg);
        LogUtils.e(msg);
    }
}
