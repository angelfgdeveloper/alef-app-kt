package com.alefglobalintegralproductivityconsulting.alef_app.data.model

import java.util.*

data class Notification(
    val id: Long = -1,
    val title: String = "",
    var timestamp: Date? = null,
    val description: String = "",
    val isView: Boolean = false,
)
