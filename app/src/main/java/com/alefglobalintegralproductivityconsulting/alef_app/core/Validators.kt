package com.alefglobalintegralproductivityconsulting.alef_app.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import android.util.Patterns
import androidx.annotation.RequiresApi

class Validators {

    companion object {
        fun isValidEmail(email: String?): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            return password.length >= 5
        }

        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun isNetworkAvailable(context: Context) =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
                getNetworkCapabilities(activeNetwork)?.run {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                } ?: false
            }
    }
}