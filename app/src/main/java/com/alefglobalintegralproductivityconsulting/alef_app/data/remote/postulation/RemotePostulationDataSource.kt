package com.alefglobalintegralproductivityconsulting.alef_app.data.remote.postulation

import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Postulation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RemotePostulationDataSource {

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