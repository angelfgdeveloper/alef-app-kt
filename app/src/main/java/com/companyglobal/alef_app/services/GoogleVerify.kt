package com.companyglobal.alef_app.services

import android.app.Activity
import com.companyglobal.alef_app.core.AppConstants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object GoogleVerify {
    fun signInGoogle(activity: Activity): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(AppConstants.ID_GOOGLE_CLIENT)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(activity, gso)
    }
}