package com.companyglobal.alef_app.services.routes

import com.companyglobal.alef_app.core.AppConstants
import com.companyglobal.alef_app.data.model.postulation.RequestPostulation
import com.companyglobal.alef_app.data.model.postulation.ResponsePostulation
import com.companyglobal.alef_app.data.model.postulation.ResponsePostulationStatus
import com.companyglobal.alef_app.services.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WebServiceAPI {

    @Headers("Content-Type: application/json")
    @POST("postulation/{idVacant}")
    suspend fun createPostulation(
        @Path("idVacant") idVacant: String,
        @Body requestPostulation: RequestPostulation
    ): Response<ResponsePostulation>

    @GET("postulation/{idVacant}")
    suspend fun getPostulation(@Path("idVacant") idVacant: String): Response<ResponsePostulationStatus>
}

object RetrofitClientAPI {
    private val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(Interceptor())
    private val client = okHttpClientBuilder.build()

    val webServiceAPI: WebServiceAPI by lazy {
        Retrofit.Builder().baseUrl(AppConstants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WebServiceAPI::class.java)
    }
}