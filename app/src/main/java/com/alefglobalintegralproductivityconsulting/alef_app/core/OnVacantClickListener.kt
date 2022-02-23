package com.alefglobalintegralproductivityconsulting.alef_app.core

import com.alefglobalintegralproductivityconsulting.alef_app.R

interface OnVacantClickListener {
    fun onVacantDetails(
        jsonVacant: String,
        jsonVacantInfoExtra: String?,
        isActivity: Boolean,
        idFragment: Int? = R.id.nav_home
    )
}