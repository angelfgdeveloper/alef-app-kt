package com.alefglobalintegralproductivityconsulting.alef_app.data.remote.notification

import com.alefglobalintegralproductivityconsulting.alef_app.core.utils.Timestamp
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Notification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RemoteNotificationDataSource {

    suspend fun getNotifications(): ArrayList<Notification> {
        val notificationList: ArrayList<Notification> = ArrayList()

        withContext(Dispatchers.IO) {
            launch {
                delay(1000)
                notificationList.addAll(notificationsList(false))
            }
        }

        return notificationList
    }

    suspend fun setMarkViewAllNotifications(markAll: Boolean): ArrayList<Notification> {
        val newNotifyList: ArrayList<Notification> = ArrayList()

        withContext(Dispatchers.IO) {
            launch {
                delay(1000)
                newNotifyList.addAll(notificationsList(markAll))
            }
        }

        return newNotifyList
    }

    private fun notificationsList(markAll: Boolean): ArrayList<Notification> {
        val notificationList: ArrayList<Notification> = ArrayList()

        notificationList.add(
            Notification(
                id = 1,
                title = "Completa tu perfil",
                timestamp = Timestamp.getDateTime("Sat Jan 29 10:00:00 CST 2022"),
                description = "Por favor, si quieres ser un candidato potencial para\n" +
                        "las empresas, usa un ordenador y completa tu \n" +
                        "informaciòn.\n" +
                        "Lo datos que registraste aquì solo es informaciòn\n" +
                        "bàsica. Gracias :D",
                isView = false
            )
        )

        notificationList.add(
            Notification(
                id = 2,
                title = "Completa tu perfil",
                timestamp = Timestamp.getDateTime("Sat Jan 29 10:00:00 CST 2022"),
                description = "Te sugerimos llenar todos la informaciòn para\n" +
                        "tener màs probabilidad de contrataciòn",
                isView = false
            )
        )

        notificationList.add(
            Notification(
                id = 3,
                title = "Alerta de configuraciòn",
                timestamp = Timestamp.getDateTime("Mon Jan 31 13:54:40 CST 2022"),
                description = "Por favor, si quieres ser un candidato potencial para\n" +
                        "las empresas, usa un ordenador y completa tu \n" +
                        "informaciòn.\n" +
                        "Lo datos que registraste aquì solo es informaciòn\n" +
                        "bàsica. Gracias :D",
                isView = true
            )
        )

        notificationList.add(
            Notification(
                id = 4,
                title = "Alerta de configuraciòn",
                timestamp = Timestamp.getDateTime("Mon Jan 31 14:54:40 CST 2022"),
                description = "Por favor, si quieres ser un candidato potencial para\n" +
                        "las empresas, usa un ordenador y completa tu \n" +
                        "informaciòn.\n" +
                        "Lo datos que registraste aquì solo es informaciòn\n" +
                        "bàsica. Gracias :D",
                isView = true
            )
        )

        if (markAll) {
            for (notification in notificationList) {
                notification.isView = false
            }

            return notificationList
        } else {
            return notificationList
        }
    }

}