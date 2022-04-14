package com.companyglobal.alef_app.data.model

import java.util.*
import kotlin.collections.ArrayList

data class Vacant(
    val id: String = "",
    val location: ArrayList<Location> = arrayListOf(),
    val workDay: ArrayList<WorkDay> = arrayListOf(),
    val availability: ArrayList<Availability> = arrayListOf(),
    val title: String = "",
    val company: Company? = null,
    val description: String = "",
    val firstSalary: Int = -1,
    val secondSalary: Int = -1,
    val paymentMethod: String = "",
    val typeCurrency: String = "",
    val mode: String = "",
    val companyPaid: Boolean = false,
    val outstading: Int = -1,
    val created: Date? = null,

    val isFavorite: Boolean = false
)

data class Location(
    val state: String = "",
    val town: String = ""
)

data class WorkDay(
    val day: String = "",
    val hour: String = ""
)

data class Availability(
    val key: String = "",
    val value: Boolean = false
)

data class Company(
    val name: String = ""
)

enum class DAYS(val num: Int, val day: String) {
    MONDAY(0, "Lunes"),
    TUESDAY(1, "Martes"),
    WEDNESDAY(2, "Miércoles"),
    THURSDAY(3, "Jueves"),
    FRIDAY(4, "Viernes"),
    SATURDAY(5, "Sabado"),
    SUNDAY(6, "Domingo")
}