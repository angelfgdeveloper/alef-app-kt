package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _titleHome = MutableLiveData<String>().apply {
        value = "Inicio"
    }
    val titleHome: LiveData<String> = _titleHome
}