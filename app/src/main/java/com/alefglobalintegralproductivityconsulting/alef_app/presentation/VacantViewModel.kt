package com.alefglobalintegralproductivityconsulting.alef_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.alefglobalintegralproductivityconsulting.alef_app.core.Result
import com.alefglobalintegralproductivityconsulting.alef_app.domain.VacantRepo
import kotlinx.coroutines.Dispatchers

class VacantViewModel(private val repo: VacantRepo) : ViewModel() {

    fun fetchVacancies() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repo.getVacancies()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }

    }

}

class VacantViewModelFactory(private val repo: VacantRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(VacantRepo::class.java).newInstance(repo)
    }

}