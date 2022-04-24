package com.companyglobal.alef_app.presentation.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.companyglobal.alef_app.core.Result
import com.companyglobal.alef_app.data.model.info.RequestAcademic
import com.companyglobal.alef_app.data.model.info.RequestPersonal
import com.companyglobal.alef_app.data.model.info.RequestPosgraduate
import com.companyglobal.alef_app.data.model.info.RequestWork
import com.companyglobal.alef_app.domain.info.InformationUserRepo
import kotlinx.coroutines.Dispatchers

class InformationUserViewModel(private val repo: InformationUserRepo) : ViewModel() {

    fun setPersonal(requestPersonal: RequestPersonal) = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repo.setPersonal(requestPersonal)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getPersonal() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repo.getPersonal()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun setAcademic(requestAcademic: RequestAcademic) = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repo.setAcademic(requestAcademic)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getAcademic() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repo.getAcademic()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun setPosgraduate(requestPosgraduate: RequestPosgraduate) = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repo.setPosgraduate(requestPosgraduate)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getPosgraduate() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repo.getPosgraduate()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun setWork(requestWork: RequestWork) = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repo.setWork(requestWork)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getWork() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repo.getWork()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class InformationUserViewModelFactory(private val repo: InformationUserRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(InformationUserRepo::class.java).newInstance(repo)
    }
}