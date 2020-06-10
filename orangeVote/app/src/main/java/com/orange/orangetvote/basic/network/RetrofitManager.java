package com.orange.orangetvote.basic.network;

import com.orange.orangetvote.MainApplication;
import com.orange.orangetvote.basic.config.Config;
import com.orange.orangetvote.basic.utils.LogUtils;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import android.content.Context;
import android.text.TextUtils;
import okhttp3.Cookie;
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

public class RetrofitManager {

    private static String Title = "ApiRetrofit";

    private static final int DEFAULT_TIMEOUT = 10;

    private static RetrofitManager sNewInstance;

    private String baseUrl = Config.BASE_URL;

    private ApiServer apiServer;

    private Context context = MainApplication.getAppContext();

    private static CookieManger cookieManger;

    private static class SingletonHolder {
        private static RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance(String tag) {
        Title = tag;
        return SingletonHolder.INSTANCE;
    }

    public static RetrofitManager getInstanceWithUrl(String tag, String url) {
        Title = tag;
        return new RetrofitManager(url);
    }

    private RetrofitManager() {
        this(null);
    }

    private RetrofitManager(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = this.baseUrl;
        }
        if (cookieManger == null) {
            cookieManger = new CookieManger(context);
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .cookieJar(new CookieManger(context)).addInterceptor(interceptor)
            // .addInterceptor(new HeaderInterceptor(Map<String, String> headers)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(baseUrl).build();
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

            LogUtils.d(Title, "----------Request Start----------------");
            LogUtils.d(Title, "| Cookies: " + cookies);
            LogUtils.d(Title, "| Request: " + request);
            LogUtils.d(Title, "| RequestUrl: " + request.url());
            LogUtils.d(Title, "| RequestMethod: " + request.method());
            LogUtils.d(Title, "| RequestHeader: " + request.headers().toString());
            LogUtils.d(Title, "| ResponseHeader: " + responseHeader);
            LogUtils.d(Title, "| ResponseBody: " + responseBody);
            LogUtils.d(Title, "----------Request End:" + duration + "毫秒----------");
            return response.newBuilder().body(ResponseBody.create(mediaType, responseBody)).build();
        }
    };

    public void removeCookies() {
        cookieManger.removeCookies();
    }

    public List<Cookie> getCookies() {
        return cookieManger.getCookies();
    }
}
