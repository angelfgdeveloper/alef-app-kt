package com.companyglobal.alef_app.core.utils

import com.companyglobal.alef_app.R

interface OnCloseBackPress {
    fun onCloseActivity(
        isActivityClose: Boolean,
        idFragment: Int? = R.id.nav_home
    )
}