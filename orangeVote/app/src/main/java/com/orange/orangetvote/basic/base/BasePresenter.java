package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.basic.network.RetrofitManager;
import com.orange.orangetvote.basic.network.ApiServer;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BasePresenter<V extends BaseView> {

    public CompositeDisposable compositeDisposable;

    protected Map<String, Object> paramsMap = new HashMap<>();

    protected Map<String, Object> headerMap = new HashMap<>();

    protected Map<String, RequestBody> requestBodyMap = new HashMap<>();

    public V baseView;

    protected ApiServer apiServer = RetrofitManager.getInstance().getApiService();

    public BasePresenter(V baseView) {
        this.baseView = baseView;
    }

    /**
     * 解除绑定
     */
    public void detachView() {
        baseView = null;
        removeDisposable();
    }

    public void addDisposable(Observable<?> flowable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer));

    }

    public void addDisposable(Flowable<?> flowable, BaseSubscriber subscriber) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(subscriber));

    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    protected void removeCookie(){
        RetrofitManager.getInstance().removeCookies();
    }

    protected void initParamAndHeader() {
        initParamMap();
        initHeaderMap();
        initRequestBodyMap();
    }

    protected void initParamMap() {
        paramsMap.clear();
    }

    protected void initHeaderMap() {
        headerMap.clear();
    }

    protected void initRequestBodyMap(){
        requestBodyMap.clear();
    }

    protected void packageToRequestBody() {
        initRequestBodyMap();
        for (String key : paramsMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    paramsMap.get(key) == null ? "" : paramsMap.get(key).toString());
            requestBodyMap.put(key, requestBody);
        }
    }
}