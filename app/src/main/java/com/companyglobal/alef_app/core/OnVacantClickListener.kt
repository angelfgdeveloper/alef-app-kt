package com.companyglobal.alef_app.core

import com.companyglobal.alef_app.R

interface OnVacantClickListener {
    fun onVacantDetails(
        jsonVacant: String,
        isActivity: Boolean,
        idFragment: Int? = R.id.nav_home
    )
}