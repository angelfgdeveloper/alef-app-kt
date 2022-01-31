package com.alefglobalintegralproductivityconsulting.alef_app.data.remote.home

import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RemoteHomeDataSource {

    suspend fun getVacancies(): ArrayList<Vacant> {
        val vacantList: ArrayList<Vacant> = ArrayList()

        withContext(Dispatchers.IO) {
            launch {
                delay(1000)

                vacantList.add(
                    Vacant(
                        id = 1,
                        location = "Iztacalco, CDMX, Granjas México",
                        timestamp = "Hace 2 días",
                        title = "Recomendado Asesor telefónico ATC Turno VESPERTINO - Apoyo de TRANSPORTE",
                        company = "Atento Mexicana, S.A. de C.V.",
                        description = "Prestaciones de ley (IMSS, vacaciones, aguinaldo) Plan de crecimiento personal y laboral Ayuda de transporte",
                        firstSalary = 15000,
                        secondSalary = 20000,
                        paymentMethod = "Mensuales"
                    )
                )

                vacantList.add(
                    Vacant(
                        id = 2,
                        location = "Iztacalco, CDMX",
                        timestamp = "Hace una semana",
                        title = "Ejecutivo Atención a cliente Call Center",
                        company = "Atento Mexicana, S.A. de C.V.",
                        description = "¿Quieres comenzar tu carrera dentro de una empresa con prestigio " +
                                "internacional? Esta es tu oportunidada AGENTE DE ATENCIÓN A CLIENTES"
                    )
                )
            }
        }

        return vacantList
    }
}