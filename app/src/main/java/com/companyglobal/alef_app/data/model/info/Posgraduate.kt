package com.companyglobal.alef_app.data.model.info

import com.google.gson.annotations.SerializedName

abstract class Posgraduate {
    abstract var uid: String?
    abstract val state: Int?
    abstract var total: Int?
    abstract var user: UserPosgraduate?
    abstract var posgraduates: ArrayList<AllPosgraduates>?
}

data class UserPosgraduate(
    @SerializedName("_id")
    var id: String? = "",
    @SerializedName("email")
    var email: String? = ""
)

data class AllPosgraduates(
    @SerializedName("type")
    var type: String? = "",
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("startMonth")
    var startMonth: String? = "",
    @SerializedName("startYear")
    var startYear: Int? = -1,
    @SerializedName("endMonth")
    var endMonth: String? = "",
    @SerializedName("endYear")
    var endYear: Int? = -1,
    @SerializedName("status")
    var status: Boolean? = false
)