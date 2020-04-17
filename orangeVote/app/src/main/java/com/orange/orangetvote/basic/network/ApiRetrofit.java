package com.orange.orangetvote.basic.network;

import android.content.Context;
import android.text.TextUtils;

import com.orange.orangetvote.MainApplication;
import com.orange.orangetvote.basic.config.Config;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiRetrofit {

    private String TAG = "ApiRetrofit";
    private static final int DEFAULT_TIMEOUT = 10;
    private static ApiRetrofit sNewInstance;
    private String baseUrl = Config.BASE_URL;
    private ApiServer apiServer;
    private Context context = MainApplication.getAppContext();
    private static CookieManger cookieManger;

    private static class SingletonHolder {
        private static ApiRetrofit INSTANCE = new ApiRetrofit();
    }

    public static ApiRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static ApiRetrofit getInstance(String url) {
        sNewInstance = new ApiRetrofit(url);
        return sNewInstance;
    }

    private ApiRetrofit() {
        this(null);
    }

    private ApiRetrofit(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = this.baseUrl;
        }
        if(cookieManger == null) {
            cookieManger = new CookieManger(context);
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .cookieJar(new CookieManger(context))
                .addInterceptor(interceptor)
                //.addInterceptor(new HeaderInterceptor(Map<String, String> headers)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        apiServer = retrofit.create(ApiServer.class);
    }

    public ApiServer getApiService() {
        return apiServer;
    }

    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            String responseBody = response.body().string();
            String responseHeader = response.headers().toString();
            String cookies = cookieManger.getCookies().toString();

            LogUtils.d(TAG, "----------Request Start----------------");
            LogUtils.d(TAG, "| Cookies: " + cookies);
            LogUtils.d(TAG, "| Request: " + request);
            LogUtils.d(TAG, "| RequestUrl: " + request.url());
            LogUtils.d(TAG, "| RequestMethod: " + request.method());
            LogUtils.d(TAG, "| RequestHeader: " + request.headers().toString());
            LogUtils.d(TAG, "| ResponseHeader: " + responseHeader);
            LogUtils.d(TAG, "| ResponseBody: " + responseBody);
            LogUtils.d(TAG, "----------Request End:" + duration + "毫秒----------");

            SharedPreferencesUtils.put("JSESSIONID", cookies);

            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, responseBody))
                    .build();
        }
    };

    public void removeCookies(){
        cookieManger.removeCookies();
    }
}
