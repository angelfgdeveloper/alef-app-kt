package com.alefglobalintegralproductivityconsulting.alef_app.domain

import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant

interface VacantRepo {
    suspend fun getVacancies(): List<Vacant>
}