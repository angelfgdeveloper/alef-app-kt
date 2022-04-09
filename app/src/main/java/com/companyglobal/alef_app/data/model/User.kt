package com.companyglobal.alef_app.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userStore") val userStore: UserStore? = null,
    @SerializedName("accessToken") val accessToken: String = "",
):UserStore()

abstract class UserStore(
    @SerializedName("_id") val uid: String = "",
    @SerializedName("google") val google: String = "",
    @SerializedName("isVisibleCV") val isVisibleCV: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("role") val role: Boolean = false,
    @SerializedName("active") val active: Boolean = false,
    @SerializedName("date") val date: String = ""
)