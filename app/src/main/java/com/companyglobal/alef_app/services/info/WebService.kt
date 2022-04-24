package com.companyglobal.alef_app.services.info

import com.companyglobal.alef_app.core.AppConstants
import com.companyglobal.alef_app.data.model.info.*
import com.companyglobal.alef_app.services.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WebService {
    @Headers("Content-Type: application/json")
    @POST("personal")
    suspend fun setPersonal(@Body requestPersonal: RequestPersonal): Response<ResponsePersonal>

    @GET("personal")
    suspend fun getPersonal(): ResponsePersonal

    @POST("academic")
    suspend fun setAcademic(@Body requestAcademic: RequestAcademic): Response<ResponseAcademic>

    @GET("academic")
    suspend fun getAcademic(): ResponseAcademic

    @POST("posgraduate")
    suspend fun setPosgraduate(@Body requestPosgraduate: RequestPosgraduate): Response<ResponsePosgraduate>

    @GET("posgraduate")
    suspend fun getPosgraduate(): ResponsePosgraduate

    @POST("work")
    suspend fun setWork(@Body requestWork: RequestWork): Response<ResponseWork>

    @GET("work")
    suspend fun getWork(): ResponseWork

    // TODO: en construccion - subida de imagen
    @Multipart
    @PUT("upload-avatar/{id}")
    suspend fun setUploadAvatar(@Path("id") idUser: String): Response<ResponseWork>

}

object RetrofitClient {
    private val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(Interceptor())
    private val client = okHttpClientBuilder.build()

    val webService: WebService by lazy {
        Retrofit.Builder().baseUrl(AppConstants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WebService::class.java)
    }
}