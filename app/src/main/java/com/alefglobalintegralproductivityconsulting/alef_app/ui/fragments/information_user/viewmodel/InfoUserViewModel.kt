package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfoUserViewModel : ViewModel() {

    // Datos Generales
    private val mInfoUser = MutableLiveData<InfoUser>()
    private val mGender = MutableLiveData<List<String>>()
    private val mState = MutableLiveData<List<String>>()
    private val mTown = MutableLiveData<List<String>>()

    // Datos Academicos
    private val mAcademicUser = MutableLiveData<AcademicUser>()
    private val mAcademicLevel = MutableLiveData<List<String>>()
    private val mAcademicAdvance = MutableLiveData<List<String>>()
    private val mMonth = MutableLiveData<List<String>>()

    // Posgrado
    private val mPosgraduateUser = MutableLiveData<PosgraduateUser>()
    private val mPosgraduateNumber = MutableLiveData<List<Int>>()

    fun setInfoUser(infoUser: InfoUser) {
        mInfoUser.value = infoUser
    }

    fun setAcademicUser(academicUser: AcademicUser) {
        mAcademicUser.value = academicUser
    }

    fun setPosgraduateUser(posgraduateUser: PosgraduateUser) {
        mPosgraduateUser.value = posgraduateUser
    }

    fun getInfoUser(): LiveData<InfoUser> = mInfoUser

    fun getAcademicUser(): LiveData<AcademicUser> = mAcademicUser

    fun getGenderList(): LiveData<List<String>> {
        val genderList = arrayListOf("Masculino", "Femenino", "Otro")
        mGender.value = genderList
        return mGender
    }

    fun getStateList(): LiveData<List<String>> {
        val stateList = arrayListOf("Durango")
        mState.value = stateList
        return mState
    }

    fun getTownList(): MutableLiveData<List<String>> {
        val townList =
            arrayListOf(
                "Canatlán",
                "Canelas",
                "Coneto de Comonfort",
                "Cuencamé",
                "Durango",
                "General Simón Bolívar",
                "Gómez Palacio"
            )

        mTown.value = townList
        return mTown
    }

    fun getAcademicLevelList(): MutableLiveData<List<String>> {
        val academicLevel = arrayListOf("Primaria", "Secundaria", "Bachillerato", "Universidad")
        mAcademicLevel.value = academicLevel
        return mAcademicLevel
    }

    fun getAcademicAdvanceList(): MutableLiveData<List<String>> {
        val academicAdvance = arrayListOf("En curso", "Trunca", "Terminado", "Grado técnico")
        mAcademicAdvance.value = academicAdvance
        return mAcademicAdvance
    }

    fun getMonthList(): MutableLiveData<List<String>> {
        val months = arrayListOf(
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre",
        )

        mMonth.value = months
        return mMonth
    }

    fun getNumberPosgraduate(): MutableLiveData<List<Int>> {
        val number = mutableListOf<Int>()
        for (i in 1..8) {
            number.add(i)
        }

        mPosgraduateNumber.value = number
        return mPosgraduateNumber
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
    val suburb: String = "",
    val street: String = "",
    val numberHome: Int = 0,
    val postalCode: Int = 0,
    val telephone: String = "",
    val cellphone: String = "",
    val curp: String = "",
    val rfc: String = "",
    val nss: String = "",
    val academicUser: AcademicUser? = null
)

data class AcademicUser(
    val levelAcademic: String = "",
    val school: String = "",
    val academicAdvance: String = "",
    val startMonth: String = "",
    val startYear: Int = 0,
    val endMonth: String = "",
    val endYear: Int = 0,
    val certificate: Boolean = false,
    val titleAchieved: Boolean = false,
    val identificationCard: Boolean = false
)

enum class STATE(val state: Int) {
    NO(0),
    YES(1),
    IN_PROCESS(2),
}

data class PosgraduateUser(
    val state: STATE? = null,
    val total: Int = 0,
    val totalPosgraduate: ArrayList<Posgraduate> = ArrayList()
)

data class Posgraduate(
    val id: Int = -1,
    val type: String = "",
    val title: String = "",
    val startMonth: String = "",
    val startYear: Int = 0,
    val endMonth: String = "",
    val endYear: Int = 0
)