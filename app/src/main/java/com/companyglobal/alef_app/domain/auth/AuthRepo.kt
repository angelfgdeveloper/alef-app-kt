package com.companyglobal.alef_app.domain.auth

import com.companyglobal.alef_app.data.model.auth.Auth
import com.companyglobal.alef_app.data.model.auth.RequestAuth
import com.companyglobal.alef_app.data.model.auth.RequestGoogle
import retrofit2.Response

interface AuthRepo {
    suspend fun signIn(requestAuth: RequestAuth): Response<Auth>
    suspend fun authGoogle(requestGoogle: RequestGoogle): Response<Auth>
}