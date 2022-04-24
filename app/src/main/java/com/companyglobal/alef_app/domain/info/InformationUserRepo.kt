package com.companyglobal.alef_app.domain.info

import com.companyglobal.alef_app.data.model.info.*
import retrofit2.Response

interface InformationUserRepo {
    suspend fun setPersonal(requestPersonal: RequestPersonal): Response<ResponsePersonal>
    suspend fun getPersonal(): ResponsePersonal

    suspend fun setAcademic(requestAcademic: RequestAcademic): Response<ResponseAcademic>
    suspend fun getAcademic(): ResponseAcademic

    suspend fun setPosgraduate(requestPosgraduate: RequestPosgraduate): Response<ResponsePosgraduate>
    suspend fun getPosgraduate(): ResponsePosgraduate

    suspend fun setWork(requestWork: RequestWork): Response<ResponseWork>
    suspend fun getWork(): ResponseWork
}