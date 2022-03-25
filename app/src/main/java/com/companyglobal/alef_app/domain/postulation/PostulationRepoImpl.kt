package com.companyglobal.alef_app.domain.postulation

import com.companyglobal.alef_app.data.model.Postulation
import com.companyglobal.alef_app.data.remote.postulation.RemotePostulationDataSource

class PostulationRepoImpl(
    private val dataSource: RemotePostulationDataSource
) : PostulationRepo {
    override suspend fun getPostulations(): List<Postulation> = dataSource.getPostulations()
}