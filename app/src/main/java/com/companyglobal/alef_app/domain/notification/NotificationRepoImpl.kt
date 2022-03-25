package com.companyglobal.alef_app.domain.notification

import com.companyglobal.alef_app.data.model.Notification
import com.companyglobal.alef_app.data.remote.notification.RemoteNotificationDataSource

class NotificationRepoImpl(
    private val dataSource: RemoteNotificationDataSource
) : NotificationRepo {
    override suspend fun getNotifications(): List<Notification> = dataSource.getNotifications()
    override suspend fun setMarkViewAllNotifications(markAll: Boolean): List<Notification> =
        dataSource.setMarkViewAllNotifications(markAll)
}