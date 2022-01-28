package com.alefglobalintegralproductivityconsulting.alef_app.domain

import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant
import com.alefglobalintegralproductivityconsulting.alef_app.data.remote.RemoteVacantDataSource

class VacantRepoImpl(private val dataSource: RemoteVacantDataSource) : VacantRepo {
    override suspend fun getVacancies(): List<Vacant> = dataSource.getVacancies()
}