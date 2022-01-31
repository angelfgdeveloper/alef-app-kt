package com.alefglobalintegralproductivityconsulting.alef_app.domain.notification

import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Notification
import com.alefglobalintegralproductivityconsulting.alef_app.data.remote.notification.RemoteNotificationDataSource

class NotificationRepoImpl(
    private val dataSource: RemoteNotificationDataSource
) : NotificationRepo {
    override suspend fun getNotifications(): List<Notification> = dataSource.getNotifications()
}