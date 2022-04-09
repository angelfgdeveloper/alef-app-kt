package com.companyglobal.alef_app.domain.info

import com.companyglobal.alef_app.data.model.info.*
import retrofit2.Response

interface InformationUserRepo {
    suspend fun setPersonal(requestPersonal: RequestPersonal): Response<ResponsePersonal>
    suspend fun setAcademic(requestAcademic: RequestAcademic): Response<ResponseAcademic>
    suspend fun setPosgraduate(requestPosgraduate: RequestPosgraduate): Response<ResponsePosgraduate>
}