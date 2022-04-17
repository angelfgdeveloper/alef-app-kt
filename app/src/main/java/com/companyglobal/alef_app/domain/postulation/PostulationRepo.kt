package com.companyglobal.alef_app.domain.postulation

import com.companyglobal.alef_app.data.model.Postulation
import com.companyglobal.alef_app.data.model.postulation.RequestPostulation
import com.companyglobal.alef_app.data.model.postulation.ResponsePostulation
import com.companyglobal.alef_app.data.model.postulation.ResponsePostulationStatus
import retrofit2.Response

interface PostulationRepo {
    suspend fun createPostulation(
        idVacant: String,
        requestPostulation: RequestPostulation
    ): Response<ResponsePostulation>

    suspend fun getPostulation(idVacant: String): Response<ResponsePostulationStatus>

    suspend fun getPostulations(): List<Postulation>
}