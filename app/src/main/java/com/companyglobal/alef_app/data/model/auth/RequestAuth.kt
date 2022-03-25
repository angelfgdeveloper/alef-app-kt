package com.companyglobal.alef_app.data.model.auth

import com.google.gson.annotations.SerializedName

data class RequestAuth(
    @SerializedName("email") val email: String = "",
    @SerializedName("password") val password: String = "",
)