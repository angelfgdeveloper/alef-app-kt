package com.alefglobalintegralproductivityconsulting.alef_app.domain.home

import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant
import com.alefglobalintegralproductivityconsulting.alef_app.data.remote.home.RemoteHomeDataSource

class HomeRepoImpl(private val dataSource: RemoteHomeDataSource) : HomeRepo {
    override suspend fun getVacancies(): List<Vacant> = dataSource.getVacancies()
}