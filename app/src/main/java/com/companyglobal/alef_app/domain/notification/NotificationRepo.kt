package com.companyglobal.alef_app.domain.notification

import com.companyglobal.alef_app.data.model.Notification

interface NotificationRepo {
    suspend fun getNotifications(): List<Notification>
    suspend fun setMarkViewAllNotifications(markAll: Boolean): List<Notification>
}