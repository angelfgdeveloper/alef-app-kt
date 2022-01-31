package com.alefglobalintegralproductivityconsulting.alef_app.data.model

data class Notification(
    val id: Long = -1,
    val title: String = "",
    val timestamp: String = "",
    val description: String = "",
    val isView: Boolean = false,
)
