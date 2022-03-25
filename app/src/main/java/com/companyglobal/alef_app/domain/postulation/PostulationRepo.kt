package com.companyglobal.alef_app.domain.postulation

import com.companyglobal.alef_app.data.model.Postulation

interface PostulationRepo {
    suspend fun getPostulations(): List<Postulation>
}