package com.alefglobalintegralproductivityconsulting.alef_app.data.remote.auth

import android.util.Log
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Auth
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.RequestAuth
import com.alefglobalintegralproductivityconsulting.alef_app.services.auth.WebServiceAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AuthDataSource(private val webService: WebServiceAuth) {

    suspend fun signIn(requestAuth: RequestAuth): Response<Auth> {
        val authUser: Response<Auth>
        withContext(Dispatchers.IO) {
            authUser = webService.signIn(requestAuth)
        }
        return authUser
    }

}