package com.companyglobal.alef_app.domain.postulation

import com.companyglobal.alef_app.data.model.Postulation
import com.companyglobal.alef_app.data.model.postulation.RequestPostulation
import com.companyglobal.alef_app.data.model.postulation.ResponsePostulation
import com.companyglobal.alef_app.data.model.postulation.ResponsePostulationStatus
import com.companyglobal.alef_app.data.remote.postulation.RemotePostulationDataSource
import retrofit2.Response

class PostulationRepoImpl(
    private val dataSource: RemotePostulationDataSource
) : PostulationRepo {

    override suspend fun createPostulation(
        idVacant: String,
        requestPostulation: RequestPostulation
    ): Response<ResponsePostulation> = dataSource.createPostulation(idVacant, requestPostulation)

    override suspend fun getPostulation(idVacant: String): Response<ResponsePostulationStatus> = dataSource.getPostulation(idVacant)

    override suspend fun getPostulations(): List<Postulation> = dataSource.getPostulations()
}