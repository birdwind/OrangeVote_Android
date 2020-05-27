package com.orange.orangetvote.basic.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.orangetvote.basic.network.ApiServer;
import com.orange.orangetvote.basic.network.RetrofitManager;
import com.orange.orangetvote.basic.utils.LogUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class AbstractPresenter<V extends BaseView> {

    public CompositeDisposable compositeDisposable;

    protected HashMap<String, Object> headerMap = new HashMap<>();

    protected HashMap<String, Object> fieldMap = new HashMap<>();

    protected HashMap<String, Object> paramsMap = new HashMap<>();

    public V baseView;

    protected RetrofitManager retrofitManager = RetrofitManager.getInstance(getClass().getSimpleName());

    protected ApiServer apiServer = retrofitManager.getApiService();

    public AbstractPresenter(V baseView) {
        this.baseView = baseView;
    }

    /**
     * 解除绑定
     */
    public void detachView() {
        baseView = null;
        removeDisposable();
    }

    public void addDisposable(Observable<ResponseBody> flowable, AbstractObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(
            flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer));

    }

    public void addDisposable(Flowable<Object> flowable, AbstractSubscriber subscriber) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(
            flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(subscriber));

    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    protected void removeCookie() {
        retrofitManager.removeCookies();
    }

    protected void initMap() {
        initParamMap();
        initHeaderMap();
        initFieldMap();
    }

    private void initParamMap() {
        paramsMap.clear();
    }

    private void initHeaderMap() {
        headerMap.clear();
    }

    private void initFieldMap() {
        fieldMap.clear();
    }

    protected RequestBody packageToRequestBody(Object obj) {
        ObjectMapper oMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = "";
        try {
            json = oMapper.writeValueAsString(obj);
        } catch (IOException e) {
            LogUtils.exception(e);
//            e.printStackTrace();
        }
        return RequestBody.create(json, MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"));
    }

    protected HashMap<String, Object> parseObjectToHashMap(Object object) {
        HashMap tempMap = new HashMap<>();
        ObjectMapper oMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        tempMap = oMapper.convertValue(object, HashMap.class);
        try {
            for (Object key : tempMap.keySet()) {
                Object obj = tempMap.get(key);
                if (obj instanceof List) {
                    LogUtils.e("這是List");
                    String temp = oMapper.writeValueAsString(obj).replace("{", "").replace("}", "").replace("[", "")
                            .replace("]", "").replace("\"", "");
                    tempMap.put(key, temp);
                }
            }
        } catch (Exception e) {
            LogUtils.exception(e);
//            e.printStackTrace();
        }

        return tempMap;
    }
}
