package com.companyglobal.alef_app.core.utils

import android.app.AlertDialog
import android.content.Context

class MessageFactory {

    companion object {
        const val TYPE_ERROR = "typeError"
        const val TYPE_SUCCESS = "typeSuccess"
        const val TYPE_LOADING = "typeLoading"
        const val TYPE_INFO = "typeInfo"

        fun getDialog(context: Context, layout: Int? = -1, type: String): AlertDialog.Builder? {
            when (type) {
                TYPE_ERROR -> {
                    return AlertDialog.Builder(context)
                        .setMessage("Hay un error al momento de traer el contenido")
                }
                TYPE_LOADING -> {
                    if (layout != -1) {
                        return layout?.let {
                            AlertDialog.Builder(context).setView(it).setCancelable(false)
                        }
                    }
                }
                TYPE_SUCCESS -> {
                    return AlertDialog.Builder(context)
                        .setMessage("El contenido fue cargado exitosamente")
                }
                TYPE_INFO -> {
                    return AlertDialog.Builder(context)
                        .setMessage("Info")
                }
            }

            return AlertDialog.Builder(context)
                .setMessage("Necesitas añadir el nuevo tipo")
        }
    }
}