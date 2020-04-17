package com.orange.orangetvote.service;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiServer {

    @GET("{url}")
    Observable<ResponseBody> executeGet(@Path("url") String url, @QueryMap Map<String, String> maps , @HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @POST("{url}")
    Observable<ResponseBody> executeFormPost(@Path("url") String url, @FieldMap Map<String, String> maps);

    @POST("{url}")
    Observable<ResponseBody> executePost(@Path("url") String url, @QueryMap Map<String, String> maps);

    @Multipart
    @POST("{url}")
    Observable<ResponseBody> executePost(@Path("url") String url, List<MultipartBody.Part> parts);

    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upLoadFile(@Path("url") String url, @Part("image\"; filename=\"image.jpg") RequestBody avatar);

    @POST("{url}")
    Observable<ResponseBody> uploadFiles(@Path("url") String url,
            @Part("filename") String description,
            @PartMap()  Map<String, RequestBody> maps);
}
