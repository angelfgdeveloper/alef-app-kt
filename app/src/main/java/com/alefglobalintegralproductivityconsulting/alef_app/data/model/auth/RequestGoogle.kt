package com.alefglobalintegralproductivityconsulting.alef_app.data.model.auth

import com.google.gson.annotations.SerializedName

data class RequestGoogle(
    @SerializedName("tokenId") val tokenId: String = "",
)