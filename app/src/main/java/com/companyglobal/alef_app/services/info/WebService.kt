package com.companyglobal.alef_app.services.info

import com.companyglobal.alef_app.core.AppConstants
import com.companyglobal.alef_app.data.model.info.*
import com.companyglobal.alef_app.services.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface WebService {
    @Headers("Content-Type: application/json")
    @POST("user/personal")
    suspend fun setPersonal(@Body requestPersonal: RequestPersonal): Response<ResponsePersonal>

    @POST("user/academic")
    suspend fun setAcademic(@Body requestAcademic: RequestAcademic): Response<ResponseAcademic>

    @POST("user/posgraduate")
    suspend fun setPosgraduate(@Body requestPosgraduate: RequestPosgraduate): Response<ResponsePosgraduate>

    @POST("user/work")
    suspend fun setWork(@Body requestWork: RequestWork): Response<ResponsetWork>
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