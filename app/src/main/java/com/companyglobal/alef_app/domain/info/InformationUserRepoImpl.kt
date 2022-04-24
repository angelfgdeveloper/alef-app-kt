package com.companyglobal.alef_app.domain.info

import com.companyglobal.alef_app.data.model.info.*
import com.companyglobal.alef_app.data.remote.info.RemoteInformationUser
import retrofit2.Response

class InformationUserRepoImpl(private val dataSource: RemoteInformationUser) : InformationUserRepo {
    override suspend fun setPersonal(requestPersonal: RequestPersonal): Response<ResponsePersonal> {
        return dataSource.setPersonal(requestPersonal)
    }

    override suspend fun getPersonal(): ResponsePersonal = dataSource.getPersonal()

    override suspend fun setAcademic(requestAcademic: RequestAcademic): Response<ResponseAcademic> {
        return dataSource.setAcademic(requestAcademic)
    }

    override suspend fun getAcademic(): ResponseAcademic = dataSource.getAcademic()

    override suspend fun setPosgraduate(requestPosgraduate: RequestPosgraduate): Response<ResponsePosgraduate> {
        return dataSource.setPosgraduate(requestPosgraduate)
    }

    override suspend fun getPosgraduate(): ResponsePosgraduate = dataSource.getPosgraduate()

    override suspend fun setWork(requestWork: RequestWork): Response<ResponseWork> {
        return dataSource.setWork(requestWork)
    }

    override suspend fun getWork(): ResponseWork = dataSource.getWork()
}