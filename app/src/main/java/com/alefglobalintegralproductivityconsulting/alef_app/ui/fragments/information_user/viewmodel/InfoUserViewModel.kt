package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfoUserViewModel: ViewModel() {

    private val mInfoUser = MutableLiveData<InfoUser>()
    private val mGender = MutableLiveData<List<String>>()

    fun setInfoUser(infoUser: InfoUser) {
        mInfoUser.value = infoUser
    }
    
    fun getInfoUser(): LiveData<InfoUser> = mInfoUser

    fun getGenderList(): LiveData<List<String>> {
        val genderList = arrayListOf("Masculino", "Femenino", "Otro")
        mGender.value = genderList
        return mGender
    }

}

data class InfoUser(
    val lastName: String = "",
    val motherLastName: String = "",
    val name: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val state: String = "",
    val town: String = "",
    val telephone: String = "",
    val cellphone: String = "",
    val address: String = "",
    val curp: String = "",
    val rfc: String = "",
    val nss: String = ""
)