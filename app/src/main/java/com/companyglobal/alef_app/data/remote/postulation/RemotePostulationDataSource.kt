package com.companyglobal.alef_app.data.remote.postulation

import com.companyglobal.alef_app.data.model.Postulation
import com.companyglobal.alef_app.data.model.postulation.RequestPostulation
import com.companyglobal.alef_app.data.model.postulation.ResponsePostulation
import com.companyglobal.alef_app.data.model.postulation.ResponsePostulationStatus
import com.companyglobal.alef_app.services.routes.WebServiceAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class RemotePostulationDataSource(private val webService: WebServiceAPI) {

    suspend fun getPostulation(
        idVacant: String
    ): Response<ResponsePostulationStatus> {
        val hasPostulation: Response<ResponsePostulationStatus>
        withContext(Dispatchers.IO) {
            hasPostulation = webService.getPostulation(idVacant)
        }
        return hasPostulation
    }

    suspend fun createPostulation(
        idVacant: String,
        requestPostulation: RequestPostulation
    ): Response<ResponsePostulation> {
        val postulation: Response<ResponsePostulation>
        withContext(Dispatchers.IO) {
            postulation = webService.createPostulation(idVacant, requestPostulation)
        }
        return postulation
    }

    suspend fun getPostulations(): ArrayList<Postulation> {
        val postulationList: ArrayList<Postulation> = ArrayList()

        withContext(Dispatchers.IO) {
            launch {
                delay(1000)
                postulationList.addAll(postulationList())
            }
        }

        return postulationList
    }

    private fun postulationList(): ArrayList<Postulation> {
        val postulationList: ArrayList<Postulation> = ArrayList()

        postulationList.add(
            Postulation(
                id = 1,
                title = "Desarrollador Android",
                description = "Te sugerimos que estes atento al proceso de tú postulación.",
                stepAdvance = 0,
                isCancel = false
            )
        )

        postulationList.add(
            Postulation(
                id = 2,
                title = "Desarrollador Web",
                description = "Te sugerimos que estes atento al proceso de tú postulación.",
                stepAdvance = 1,
                isCancel = false
            )
        )

        postulationList.add(
            Postulation(
                id = 3,
                title = "Desarrollador Full Stack Jr",
                description = "Te sugerimos que estes atento al proceso de tú postulación.",
                stepAdvance = 3,
                isCancel = false
            )
        )

        return postulationList
    }
}