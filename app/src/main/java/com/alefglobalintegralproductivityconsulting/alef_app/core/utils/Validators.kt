package com.alefglobalintegralproductivityconsulting.alef_app.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

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

        fun validateFields(
            vararg textFields: TextInputLayout,
            fab: FloatingActionButton,
            context: Context
        ): Boolean {
            var isValid = true

            for (textField in textFields) {
                if (textField.editText?.text.toString().trim().isEmpty()) {
                    textField.error = context.getString(R.string.helper_required)
                    textField.editText?.requestFocus()
                    isValid = false
                } else textField.error = null
            }

            fab.isEnabled = isValid
            return isValid
        }

        fun onBackPress(
            activity: FragmentActivity
        ) {
            val callback: OnBackPressedCallback =
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        activity.finish()
                    }
                }

            activity.onBackPressedDispatcher.addCallback(activity, callback)
        }
    }
}