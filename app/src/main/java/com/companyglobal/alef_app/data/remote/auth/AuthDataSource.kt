package com.companyglobal.alef_app.data.remote.auth

import com.companyglobal.alef_app.data.model.User
import com.companyglobal.alef_app.data.model.auth.Auth
import com.companyglobal.alef_app.data.model.auth.RequestAuth
import com.companyglobal.alef_app.data.model.auth.RequestGoogle
import com.companyglobal.alef_app.data.model.auth.RequestRegister
import com.companyglobal.alef_app.services.auth.WebServiceAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AuthDataSource(private val webService: WebServiceAuth) {

    suspend fun register(requestRegister: RequestRegister): Response<User> {
        val authUser: Response<User>
        withContext(Dispatchers.IO) {
            authUser = webService.register(requestRegister)
        }
        return authUser
    }

    suspend fun signIn(requestAuth: RequestAuth): Response<Auth> {
        val authUser: Response<Auth>
        withContext(Dispatchers.IO) {
            authUser = webService.signIn(requestAuth)
        }
        return authUser
    }

    suspend fun authGoogle(requestGoogle: RequestGoogle): Response<Auth> {
        val authGoogle: Response<Auth>
        withContext(Dispatchers.IO) {
            authGoogle = webService.authGoogle(requestGoogle)
        }
        return authGoogle
    }

}