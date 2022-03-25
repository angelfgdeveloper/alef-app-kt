package com.companyglobal.alef_app.data.model

import java.util.*

data class Vacant(
    val id: Long = -1,
    val location: String = "",
    val timestamp: Date? = null,
    val title: String = "",
    val isFavorite: Boolean = false,
    val company: String = "",
    val description: String = "",
    val firstSalary: Int = -1,
    val secondSalary: Int = -1,
    val paymentMethod: String = "",
    val vacantInfoExtra: VacantInfoExtra? = null
)

enum class DAYS(val num: Int, val day: String) {
    MONDAY(0, "Lunes"),
    TUESDAY (1, "Martes"),
    WEDNESDAY (2, "Miércoles"),
    THURSDAY(3, "Jueves"),
    FRIDAY(4, "Viernes"),
    SATURDAY(5, "Sabado"),
    SUNDAY(6, "Domingo")
}

data class VacantInfoExtra(
    val companyPaid: Boolean = false,
    val workDay: Map<Int, String>? = null,
    val mode: String = "",
    val availability: Map<String, Boolean> = mapOf(),
)