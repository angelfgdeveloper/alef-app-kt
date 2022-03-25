package com.companyglobal.alef_app.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id") val uid: String = "",
    @SerializedName("google") val google: String = "",
    @SerializedName("isVisibleCV") val isVisibleCV: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("role") val role: String = "",
    @SerializedName("active") val active: String = "",
    @SerializedName("date") val date: String = "",
)