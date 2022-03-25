package com.companyglobal.alef_app.data.model.auth

import com.companyglobal.alef_app.data.model.User
import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("user") val user: User? = null,
    @SerializedName("accessToken") val accessToken: String = "",
    @SerializedName("refreshToken") val refreshToken: String = ""
)