package com.companyglobal.alef_app.data.model.info

import com.companyglobal.alef_app.ui.fragments.information_user.viewmodel.STATE
import com.google.gson.annotations.SerializedName

data class ResponsePosgraduate(
    @SerializedName("_id")
    override var uid: String? = "",
    @SerializedName("state")
    override var state: Int? = -1,
    @SerializedName("total")
    override var total: Int? = -1,
    @SerializedName("user")
    override var user: UserPosgraduate? = null,
    @SerializedName("posgraduates")
    override var posgraduates: ArrayList<AllPosgraduates>? = arrayListOf()
) : Posgraduate()