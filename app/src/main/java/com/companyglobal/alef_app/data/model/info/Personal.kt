package com.companyglobal.alef_app.data.model.info

abstract class Personal {
    abstract var name: String
    abstract var motherLastName: String
    abstract var lastName: String
    abstract var birth: String
    abstract var gender: String
    abstract var state: String
    abstract var town: String
    abstract var suburb: String
    abstract var street: String
    abstract var numberHome: Int
    abstract var postalCode: Int
    abstract var telephone: String
    abstract var cellphone: String

    // Opcionales
    abstract var curp: String
    abstract var rfc: String
    abstract var nss: String
}
