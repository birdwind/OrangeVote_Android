package com.orange.orangetvote.basic.network;

import java.util.Map;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiServer {

    @GET()
    Observable<ResponseBody> executeGet(@Url String url, @QueryMap Map<String, Object> paramMap,
        @HeaderMap Map<String, Object> headerMap);

    @FormUrlEncoded
    @PUT()
    Observable<ResponseBody> executePutFormUrlEncode(@Url String url, @FieldMap Map<String, Object> fieldMap,
        @HeaderMap Map<String, Object> headerMap);

    @PUT()
    Observable<ResponseBody> executePut(@Url String url, @Body RequestBody requestBody, @HeaderMap Map<String, Object> headerMap);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> executePostFormUrlEncode(@Url String url, @FieldMap Map<String, Object> fieldMap,
        @HeaderMap Map<String, Object> headerMap);

    @POST()
    Observable<ResponseBody> executePostWithGet(@Url String url, @QueryMap Map<String, Object> paramMap,
        @HeaderMap Map<String, Object> headerMap);

    // @Multipart
    // @POST()
    // Observable<ResponseBody> executePost(@Url String url,
    // @Part() List<MultipartBody.Part> parts,
    // @HeaderMap Map<String, Object> headerMap);
    @Multipart
    @POST()
    Observable<ResponseBody> executePostMultipart(@Url String url, @PartMap Map<String, RequestBody> partMap,
        @HeaderMap Map<String, Object> headerMap);

    @Multipart
    @POST()
    Observable<ResponseBody> upLoadFile(@Url String url, @Part("image\"; filename=\"image.jpg") RequestBody avatar,
        @HeaderMap Map<String, Object> headerMap);

    @POST()
    Observable<ResponseBody> uploadFiles(@Url String url, @Part("filename") String description,
        @PartMap() Map<String, RequestBody> maps, @HeaderMap Map<String, Object> headerMap);
}
