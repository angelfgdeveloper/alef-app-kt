package com.companyglobal.alef_app.data.model.auth

import com.google.gson.annotations.SerializedName

data class RequestAuth(
    @SerializedName("email")
    override var email: String = "",
    @SerializedName("password")
    override var password: String = ""
): UserCredentials()