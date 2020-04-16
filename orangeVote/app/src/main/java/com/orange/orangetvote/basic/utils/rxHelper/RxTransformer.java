package com.orange.orangetvote.basic.utils.rxHelper;

import com.orange.orangetvote.basic.base.BaseModel;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>
 * 控制操作現成的輔助類別
 */
public class RxTransformer {

    /**
     * @param <T>泛型
     * @return 返回Observable
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers(BaseModel baseModel) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> baseModel.mDisposable.add(disposable))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
