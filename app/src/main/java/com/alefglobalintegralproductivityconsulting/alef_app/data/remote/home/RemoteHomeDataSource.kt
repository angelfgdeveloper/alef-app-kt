package com.alefglobalintegralproductivityconsulting.alef_app.data.remote.home

import com.alefglobalintegralproductivityconsulting.alef_app.core.utils.Timestamp
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.DAYS
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.VacantInfoExtra
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RemoteHomeDataSource {

    suspend fun getVacancies(): ArrayList<Vacant> {
        val vacantList: ArrayList<Vacant> = ArrayList()

        withContext(Dispatchers.IO) {
            launch {
                delay(300)

                val workDay = HashMap<Int, String>()
                workDay.put(DAYS.MONDAY.num, "10:00 a 15:00")
                workDay.put(DAYS.TUESDAY.num, "10:00 a 15:00")
                workDay.put(DAYS.WEDNESDAY.num, "10:00 a 15:00")
                workDay.put(DAYS.THURSDAY.num, "10:00 a 15:00")
                workDay.put(DAYS.FRIDAY.num, "10:00 a 15:00")
                workDay.put(DAYS.SATURDAY.num, "No se trabaja")
                workDay.put(DAYS.SUNDAY.num, "No se trabaja")

                val availability = HashMap<String, Boolean>()
                availability.put("Cambiar de turno", false)
                availability.put("Viajar", true)
                availability.put("Prima Vacacional", false)
                availability.put("Seguro social", true)
                availability.put("Infonavit", true)

                vacantList.add(
                    Vacant(
                        id = 1,
                        location = "Iztacalco, CDMX, Granjas México",
                        timestamp = Timestamp.getDateTime("Mon Jan 31 15:19:40 CST 2022"),
                        title = "Recomendado Asesor telefónico ATC Turno VESPERTINO - Apoyo de TRANSPORTE",
                        company = "Atento Mexicana, S.A. de C.V.",
                        description = "Prestaciones de ley (IMSS, vacaciones, aguinaldo) Plan de crecimiento personal y laboral Ayuda de transporte",
                        firstSalary = 15000,
                        secondSalary = 20000,
                        paymentMethod = "Mensuales",
                        isFavorite = false,
                        vacantInfoExtra = VacantInfoExtra(
                            companyPaid = true,
                            workDay = workDay,
                            mode = "Remoto",
                            availability = availability
                        )
                    )
                )

                vacantList.add(
                    Vacant(
                        id = 2,
                        location = "Ciudad de México",
                        timestamp = Timestamp.getDateTime("Mon Jan 31 15:19:40 CST 2022"),
                        title = "Recomendado Asesor telefónico",
                        company = "ABsc Mexicana, S.A. de C.V.",
                        description = "Prestaciones de ley (IMSS, vacaciones, aguinaldo) Plan de crecimiento personal y laboral Ayuda de transporte",
                        firstSalary = 1500,
                        secondSalary = 2000,
                        paymentMethod = "Semanales",
                        isFavorite = true,
                        vacantInfoExtra = VacantInfoExtra(
                            companyPaid = false,
                            workDay = workDay,
                            mode = "Home office & presencial"
                        )
                    )
                )

                vacantList.add(
                    Vacant(
                        id = 3,
                        location = "Iztacalco, CDMX",
                        timestamp = Timestamp.getDateTime("Mon Jan 10 10:00:00 CST 2022"),
                        title = "Ejecutivo Atención a cliente Call Center",
                        company = "Atento Mexicana, S.A. de C.V.",
                        description = "¿Quieres comenzar tu carrera dentro de una empresa con prestigio " +
                                "internacional? Esta es tu oportunidada AGENTE DE ATENCIÓN A CLIENTES",
                        isFavorite = true,
                        vacantInfoExtra = VacantInfoExtra(
                            companyPaid = false,
                            workDay = workDay,
                            mode = "Presencial"
                        )
                    )
                )
            }
        }

        return vacantList
    }
}