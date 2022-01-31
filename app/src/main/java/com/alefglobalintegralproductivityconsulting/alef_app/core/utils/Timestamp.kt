package com.alefglobalintegralproductivityconsulting.alef_app.core.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val SECOND_MILLIS = 1
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS  // 60 * 1
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS // 60 * 60
private const val DAY_MILLIS = 24 * HOUR_MILLIS // 24 * 60

object Timestamp {

    fun getTimeAgo(time: Int): String {

        // Obtenemos la fecha de este momento
        val now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())

        if (time > now || time <= 0) {
            return "en el futuro"
        }

        // Diferencia entre el tiempo de ahora y el tiempo que mandamos time
        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> "Justo ahora"
            diff < 2 * MINUTE_MILLIS -> "Hace un minuto"
            diff < 60 * MINUTE_MILLIS -> "Hace ${diff / MINUTE_MILLIS} minutos"
            diff < 2 * HOUR_MILLIS -> "Hace una hora"
            diff < 24 * HOUR_MILLIS -> "Hace ${diff / HOUR_MILLIS} horas"
            diff < 48 * HOUR_MILLIS -> "Ayer"
            else -> "Hace ${diff / DAY_MILLIS} días"
        }

    }

    fun getDateTime(dateTime: String): Date {
//        val dateTime = "Mon Jan 31 14:54:40 CST 2022"
        val format = SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy")
        return format.parse(dateTime)
    }
}