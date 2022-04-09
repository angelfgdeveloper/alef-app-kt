package com.companyglobal.alef_app.data.model.info

import com.google.gson.annotations.SerializedName

data class ResponseAcademic(
    @SerializedName("uid")
    override var uid: String? = "",
    @SerializedName("levelAcademic")
    override var levelAcademic: String = "",
    @SerializedName("institute")
    override var institute: String = "",
    @SerializedName("academicAdvance")
    override var academicAdvance: String = "",
    @SerializedName("startMonth")
    override var startMonth: String = "",
    @SerializedName("startYear")
    override var startYear: Int = -1,
    @SerializedName("endMonth")
    override var endMonth: String = "",
    @SerializedName("endYear")
    override var endYear: Int = -1,
    @SerializedName("certificate")
    override var certificate: Boolean = false,
    @SerializedName("titleAchieved")
    override var titleAchieved: Boolean = false,
    @SerializedName("identificationCard")
    override var identificationCard: Boolean = false,
    @SerializedName("user")
    override var user: UserAcademic? = null,
) : Academic()

