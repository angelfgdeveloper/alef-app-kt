package com.alefglobalintegralproductivityconsulting.alef_app.data.model

data class Vacant(
    val id: Long = -1,
    val location: String = "",
    val timestamp: String = "",
    val title: String = "",
    val isFavorite: Boolean = false,
    val company: String = "",
    val description: String = "",
    val firstSalary: Int = -1,
    val secondSalary: Int = -1,
    val paymentMethod: String = ""
)
