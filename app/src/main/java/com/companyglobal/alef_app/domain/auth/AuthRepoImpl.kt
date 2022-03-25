package com.companyglobal.alef_app.domain.auth

import com.companyglobal.alef_app.data.model.auth.Auth
import com.companyglobal.alef_app.data.model.auth.RequestAuth
import com.companyglobal.alef_app.data.model.auth.RequestGoogle
import com.companyglobal.alef_app.data.remote.auth.AuthDataSource
import retrofit2.Response

class AuthRepoImpl(private val dataSource: AuthDataSource): AuthRepo {
    override suspend fun signIn(requestAuth: RequestAuth): Response<Auth> {
        return dataSource.signIn(requestAuth)
    }

    override suspend fun authGoogle(requestGoogle: RequestGoogle): Response<Auth> {
        return dataSource.authGoogle(requestGoogle)
    }
}