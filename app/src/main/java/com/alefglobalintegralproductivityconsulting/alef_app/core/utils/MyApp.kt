package com.alefglobalintegralproductivityconsulting.alef_app.core.utils

import android.app.Application
import android.content.Context

class MyApp : Application() {
    companion object {
        lateinit var instance: MyApp
            private set

        fun getContext(): Context {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
