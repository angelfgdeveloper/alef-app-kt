package com.companyglobal.alef_app.domain.home

import com.companyglobal.alef_app.data.model.Vacant
import com.companyglobal.alef_app.data.remote.home.RemoteHomeDataSource

class HomeRepoImpl(private val dataSource: RemoteHomeDataSource) : HomeRepo {
    override suspend fun getVacancies(): ArrayList<Vacant> = dataSource.getVacancies()
}