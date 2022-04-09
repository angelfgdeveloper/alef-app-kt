package com.companyglobal.alef_app.data.model.info

import com.google.gson.annotations.SerializedName

abstract class Academic {
    abstract var uid: String?
    abstract var levelAcademic: String
    abstract var institute: String
    abstract var academicAdvance: String
    abstract var startMonth: String
    abstract var startYear: Int
    abstract var endMonth: String
    abstract var endYear: Int
    abstract var certificate: Boolean
    abstract var titleAchieved: Boolean
    abstract var identificationCard: Boolean
    abstract var user: UserAcademic?
}

data class UserAcademic(
    @SerializedName("_id")
    var id: String? = "",
    @SerializedName("email")
    var email: String? = ""
)


