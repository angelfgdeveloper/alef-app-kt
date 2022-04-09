package com.companyglobal.alef_app.services.info

import android.util.Log
import com.companyglobal.alef_app.core.AppConstants
import com.companyglobal.alef_app.core.utils.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SharedPreferencesManager.getStringValue(AppConstants.USER_TOKEN)

        val original = chain.request()
        val request = original.newBuilder()
            .header("authorization", token.toString())
            .method(original.method, original.body)
            .build()
        val response = chain.proceed(request)
        Log.d("INTERCEPTOR-01", "Code : " + response.code)
        if (response.code == 403) {
            Log.d("INTERCEPTOR-02", "Ya caduco el token, Code:" + response.code)
            SharedPreferencesManager.removeAllData(AppConstants.USER_TOKEN)
            return response
        }
        return response
    }
}
