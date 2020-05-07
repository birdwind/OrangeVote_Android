package com.orange.orangetvote.basic.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.orangetvote.basic.network.ApiServer;
import com.orange.orangetvote.basic.network.RetrofitManager;
import com.orange.orangetvote.basic.utils.LogUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AbstractPresenter<V extends BaseView> {

    public CompositeDisposable compositeDisposable;

    protected Map<String, Object> paramsMap = new HashMap<>();

    protected Map<String, Object> headerMap = new HashMap<>();

    protected Map<String, RequestBody> requestBodyMap = new HashMap<>();

    protected RequestBody requestBody;

    public V baseView;

    protected ApiServer apiServer = RetrofitManager.getInstance().getApiService();

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

    public void addDisposable(Observable<?> flowable, AbstractObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(
            flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer));

    }

    public void addDisposable(Flowable<?> flowable, AbstractSubscriber subscriber) {
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

    protected void initRequestBodyMap() {
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

    protected void packageToParamsMap(Object obj) {
        ObjectMapper oMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        HashMap<String, Object> tempMap = oMapper.convertValue(obj, HashMap.class);

//        try {
//
//            for (String key : tempMap.keySet()) {
//                Object object = tempMap.get(key);
//                if (object instanceof List) {
//                    LogUtils.e("這是List");
//                    String temp = oMapper.writeValueAsString(object).replace("{", "").replace("}", "").replace("[", "")
//                        .replace("]", "").replace("\"", "");
//                    tempMap.put(key, temp);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        paramsMap.putAll(tempMap);

        requestBody = getRequestBody(tempMap);
    }

    public RequestBody getRequestBody(HashMap<String, Object> hashMap) {
        StringBuffer data = new StringBuffer();
        if (hashMap != null && hashMap.size() > 0) {
            Iterator iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                data.append(key).append("=").append(val).append("&");
            }
        }
        String jso = data.substring(0, data.length() - 1);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),jso);

        return requestBody;
    }
}
