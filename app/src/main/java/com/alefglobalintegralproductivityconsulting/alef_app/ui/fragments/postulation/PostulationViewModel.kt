package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.postulation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostulationViewModel : ViewModel() {

    private val _titlePostulations = MutableLiveData<String>().apply {
        value = "Postulaciones"
    }

    val titlePostulation: LiveData<String> = _titlePostulations
}