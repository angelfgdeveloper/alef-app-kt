package com.companyglobal.alef_app.data.remote.info

import com.companyglobal.alef_app.data.model.info.*
import com.companyglobal.alef_app.services.info.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RemoteInformationUser(private val webService: WebService) {
    suspend fun setPersonal(requestPersonal: RequestPersonal): Response<ResponsePersonal> {
        val personal: Response<ResponsePersonal>
        withContext(Dispatchers.IO) {
            personal = webService.setPersonal(requestPersonal)
        }
        return personal
    }

    suspend fun getPersonal(): ResponsePersonal {
        val personal: ResponsePersonal
        withContext(Dispatchers.IO) {
            personal = webService.getPersonal()
        }
        return personal
    }

    suspend fun setAcademic(requestAcademic: RequestAcademic): Response<ResponseAcademic> {
        val academic: Response<ResponseAcademic>
        withContext(Dispatchers.IO) {
            academic = webService.setAcademic(requestAcademic)
        }
        return academic
    }

    suspend fun getAcademic(): ResponseAcademic {
        val academic: ResponseAcademic
        withContext(Dispatchers.IO) {
            academic = webService.getAcademic()
        }
        return academic
    }

    suspend fun setPosgraduate(requestPosgraduate: RequestPosgraduate): Response<ResponsePosgraduate> {
        val posgraduate: Response<ResponsePosgraduate>
        withContext(Dispatchers.IO) {
            posgraduate = webService.setPosgraduate(requestPosgraduate)
        }
        return posgraduate
    }

    suspend fun getPosgraduate(): ResponsePosgraduate {
        val posgraduate: ResponsePosgraduate
        withContext(Dispatchers.IO) {
            posgraduate = webService.getPosgraduate()
        }
        return posgraduate
    }

    suspend fun setWork(requestWork: RequestWork): Response<ResponseWork> {
        val work: Response<ResponseWork>
        withContext(Dispatchers.IO) {
            work = webService.setWork(requestWork)
        }
        return work
    }

    suspend fun getWork(): ResponseWork {
        val work: ResponseWork
        withContext(Dispatchers.IO) {
            work = webService.getWork()
        }
        return work
    }

}