package com.alefglobalintegralproductivityconsulting.alef_app.services.auth

import com.alefglobalintegralproductivityconsulting.alef_app.core.AppConstants
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Auth
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.RequestAuth
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface WebServiceAuth {

    @Headers("Content-Type: application/json")
    @POST("sign-in")
    suspend fun signIn(@Body requestAuth: RequestAuth): Response<Auth>
}

object RetrofitClientAuth {
    val webServiceAuth: WebServiceAuth by lazy {
        Retrofit.Builder().baseUrl(AppConstants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebServiceAuth::class.java)
    }
}