package com.companyglobal.alef_app.core.utils

import android.app.Application
import android.content.Context

class MyApp : Application() {
    companion object {
        private var instance: MyApp? = null

        private fun getInstance(): MyApp {
            if (instance == null) {
                instance = MyApp()
            }
            return instance as MyApp
        }

        fun getContext(): Context {
            return getInstance()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
