package com.companyglobal.alef_app.data.model.info

import com.google.gson.annotations.SerializedName

abstract class Work {
    abstract var uid: String?
    abstract val state: Int? // state - NO(0), YES(1)
    abstract var user: UserWork?
    abstract var works: ArrayList<AllWorks>?
}

data class UserWork(
    @SerializedName("_id")
    var id: String? = "",
    @SerializedName("email")
    var email: String? = ""
)

data class AllWorks(
    @SerializedName("company")
    var company: String? = "",
    @SerializedName("job")
    var job: String? = "",
    @SerializedName("area")
    var area: String? = "",
    @SerializedName("startMonth")
    var startMonth: String? = "",
    @SerializedName("startYear")
    var startYear: Int? = -1,
    @SerializedName("endMonth")
    var endMonth: String? = "",
    @SerializedName("endYear")
    var endYear: Int? = -1,
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("status")
    var status: Boolean? = false
)