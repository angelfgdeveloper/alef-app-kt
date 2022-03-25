package com.companyglobal.alef_app.domain.home

import com.companyglobal.alef_app.data.model.Vacant

interface HomeRepo {
    suspend fun getVacancies(): List<Vacant>
}