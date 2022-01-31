package com.alefglobalintegralproductivityconsulting.alef_app.domain.postulation

import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Postulation

interface PostulationRepo {
    suspend fun getPostulations(): List<Postulation>
}