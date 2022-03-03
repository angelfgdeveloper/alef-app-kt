package com.alefglobalintegralproductivityconsulting.alef_app.data.model

import com.google.gson.annotations.SerializedName

data class RequestAuth(
    @SerializedName("email") val email: String = "",
    @SerializedName("password") val password: String = "",
)