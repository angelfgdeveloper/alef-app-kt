package com.companyglobal.alef_app.data.remote.home

import com.companyglobal.alef_app.data.model.Vacant
import com.companyglobal.alef_app.services.auth.WebServiceAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RemoteHomeDataSource(private val webService: WebServiceAuth) {

    suspend fun getVacancies(): ArrayList<Vacant> {
        var vacantList: ArrayList<Vacant>
        withContext(Dispatchers.IO) {
            vacantList = webService.getVacancies()
        }
        return vacantList
    }

}