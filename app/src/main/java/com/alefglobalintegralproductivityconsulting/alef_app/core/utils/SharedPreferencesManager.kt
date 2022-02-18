package com.alefglobalintegralproductivityconsulting.alef_app.core.utils

import android.content.Context
import android.content.SharedPreferences

private const val APP_SETTINGS_FILE = "APP_SETTINGS"

object SharedPreferencesManager {

    private fun SharedPreferencesManager() {}

    private fun getSharedPreferences(): SharedPreferences {
        return MyApp.getContext().getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE)
    }

    fun setStringValue(dataLabel: String?, dataValue: String?) {
        val edit = getSharedPreferences().edit()
        edit.putString(dataLabel, dataValue)
        edit.apply()
    }

    fun getStringValue(dataLabel: String?): String? {
        return getSharedPreferences().getString(dataLabel, "")
    }

    fun setBooleanValue(dataLabel: String?, dataValue: Boolean) {
        val edit = getSharedPreferences().edit()
        edit.putBoolean(dataLabel, dataValue)
        edit.apply()
    }

    fun getBooleanValue(dataLabel: String?): Boolean {
        return getSharedPreferences().getBoolean(dataLabel, false)
    }

    fun setIntValue(dataLabel: String?, dataValue: Int) {
        val edit = getSharedPreferences().edit()
        edit.putInt(dataLabel, dataValue)
        edit.apply()
    }

    fun getIntValue(dataLabel: String?): Int {
        return getSharedPreferences().getInt(dataLabel, -1)
    }

    fun removeAllData(dataLabel: String?) {
        val edit = getSharedPreferences().edit()
        edit.remove(dataLabel)
        edit.apply()
    }
}