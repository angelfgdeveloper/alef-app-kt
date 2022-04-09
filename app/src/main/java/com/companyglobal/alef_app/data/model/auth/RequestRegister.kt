package com.companyglobal.alef_app.data.model.auth

import com.google.gson.annotations.SerializedName

data class RequestRegister(
    @SerializedName("email")
    override var email: String = "",
    @SerializedName("password")
    override var password: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("repeatPassword") val repeatPassword: String = "",
) : UserCredentials()