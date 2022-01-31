package com.alefglobalintegralproductivityconsulting.alef_app.domain.home

import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant

interface HomeRepo {
    suspend fun getVacancies(): List<Vacant>
}