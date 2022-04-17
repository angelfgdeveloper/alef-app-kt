package com.companyglobal.alef_app.data.model.postulation

import com.google.gson.annotations.SerializedName

data class ResponsePostulation(
    @SerializedName("postulation")
    val postulation: UserPostulation? = null,
    @SerializedName("message")
    val message: String? = "",
)

data class UserPostulation(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("user")
    val user: UserInfoPostulation? = null,
    @SerializedName("vacant")
    val vacant: String? = "",
    @SerializedName("company")
    val company: String? = "",
)

data class UserInfoPostulation(
    @SerializedName("_id")
    var id: String? = "",
    @SerializedName("email")
    var email: String? = ""
)

data class ResponsePostulationStatus(
    @SerializedName("status")
    val status: Boolean = false
)