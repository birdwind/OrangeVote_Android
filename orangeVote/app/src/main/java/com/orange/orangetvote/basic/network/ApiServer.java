package com.orange.orangetvote.basic.network;

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
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiServer {

    @GET()
    Observable<ResponseBody> executeGet(@Url String url,
                                        @QueryMap Map<String, String> paramMap,
                                        @HeaderMap Map<String, String> headerMap);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> executeFormPost(@Url String url,
                                             @FieldMap Map<String, String> fieldMap,
                                             @HeaderMap Map<String, String> headerMap);

    @POST()
    Observable<ResponseBody> executePost(@Url String url,
                                         @QueryMap Map<String, String> paramMap,
                                         @HeaderMap Map<String, String> headerMap);

    @Multipart
    @POST()
    Observable<ResponseBody> executePost(@Url String url,
                                         List<MultipartBody.Part> parts,
                                         @HeaderMap Map<String, String> headerMap);

    @Multipart
    @POST()
    Observable<ResponseBody> upLoadFile(@Url String url,
                                        @Part("image\"; filename=\"image.jpg") RequestBody avatar,
                                        @HeaderMap Map<String, String> headerMap);

    @POST()
    Observable<ResponseBody> uploadFiles(@Url String url,
                                         @Part("filename") String description,
                                         @PartMap() Map<String, RequestBody> maps,
                                         @HeaderMap Map<String, String> headerMap);
}
