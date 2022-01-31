package com.alefglobalintegralproductivityconsulting.alef_app.domain.notification

import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Notification

interface NotificationRepo {
    suspend fun getNotifications(): List<Notification>
}