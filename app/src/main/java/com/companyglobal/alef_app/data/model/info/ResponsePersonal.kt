package com.companyglobal.alef_app.data.model.info

import com.google.gson.annotations.SerializedName

data class ResponsePersonal(
    @SerializedName("personal")
    val informationUser: InformationUser? = null
)

data class InformationUser(
    @SerializedName("id")
    val uid: String = "",
    @SerializedName("name")
    override var name: String = "",
    @SerializedName("motherLastName")
    override var motherLastName: String = "",
    @SerializedName("lastName")
    override var lastName: String = "",
    @SerializedName("dateOfBirth")
    override var birth: String = "",
    @SerializedName("gender")
    override var gender: String = "",
    @SerializedName("state")
    override var state: String = "",
    @SerializedName("town")
    override var town: String = "",
    @SerializedName("suburb")
    override var suburb: String = "",
    @SerializedName("street")
    override var street: String = "",
    @SerializedName("numberHome")
    override var numberHome: Int = -1,
    @SerializedName("postalCode")
    override var postalCode: Int = -1,
    @SerializedName("telephone")
    override var telephone: String = "",
    @SerializedName("cellphone")
    override var cellphone: String = "",

    @SerializedName("user")
    val user: UserData? = null,

    // Opcionales
    @SerializedName("curp")
    override var curp: String = "",
    @SerializedName("rfc")
    override var rfc: String = "",
    @SerializedName("nss")
    override var nss: String = ""
): Personal()

data class UserData(
    @SerializedName("_id")
    val uid: String = "",
    @SerializedName("email")
    val email: String = "",
)