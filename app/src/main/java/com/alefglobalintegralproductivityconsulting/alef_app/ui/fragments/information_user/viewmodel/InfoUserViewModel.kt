package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfoUserViewModel: ViewModel() {

    private val mInfoUser = MutableLiveData<InfoUser>()
    
    fun setInfoUser(infoUser: InfoUser) {
        mInfoUser.value = infoUser
    }
    
    fun getInfoUser(): LiveData<InfoUser> = mInfoUser

}

data class InfoUser(
    val lastName: String = "",
    val motherLastName: String = "",
    val name: String = "",
    val dateOfBirth: String = ""
)