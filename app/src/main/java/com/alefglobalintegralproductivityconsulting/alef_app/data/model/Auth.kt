package com.alefglobalintegralproductivityconsulting.alef_app.data.model

import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("accessToken") val accessToken: String = "",
    @SerializedName("refreshToken") val refreshToken: String = ""
)