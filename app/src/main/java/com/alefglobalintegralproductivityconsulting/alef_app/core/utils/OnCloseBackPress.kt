package com.alefglobalintegralproductivityconsulting.alef_app.core.utils

import com.alefglobalintegralproductivityconsulting.alef_app.R

interface OnCloseBackPress {
    fun onCloseActivity(
        isActivityClose: Boolean,
        idFragment: Int? = R.id.nav_home
    )
}