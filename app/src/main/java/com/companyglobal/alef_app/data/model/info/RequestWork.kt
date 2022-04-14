package com.companyglobal.alef_app.data.model.info

import com.google.gson.annotations.SerializedName

data class RequestWork(
    @SerializedName("_id")
    override var uid: String? = "",
    @SerializedName("state")
    override val state: Int? = -1,
    @SerializedName("user")
    override var user: UserWork? = null,
    @SerializedName("works")
    override var works: ArrayList<AllWorks>? = arrayListOf()
) : Work()
