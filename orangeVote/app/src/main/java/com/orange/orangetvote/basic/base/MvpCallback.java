package com.orange.orangetvote.basic.base;

public interface MvpCallback<T extends BaseBean> {

    void onSuccess(T t);

    void onFail(String reason);
}
