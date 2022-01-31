package com.alefglobalintegralproductivityconsulting.alef_app.data.model

data class Postulation(
    val id: Long = -1,
    val title: String = "",
    val description: String = "",
    val stepAdvance: Int = -1,
    val isCancel: Boolean = false
)