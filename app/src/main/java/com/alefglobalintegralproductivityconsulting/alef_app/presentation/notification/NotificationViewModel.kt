package com.alefglobalintegralproductivityconsulting.alef_app.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.alefglobalintegralproductivityconsulting.alef_app.core.Result
import com.alefglobalintegralproductivityconsulting.alef_app.domain.notification.NotificationRepo
import kotlinx.coroutines.Dispatchers

class NotificationViewModel(private val repo: NotificationRepo) : ViewModel() {

    fun fetchNotifications() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repo.getNotifications()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }

    }

    fun setMarkViewAllNotifications(markAll: Boolean) = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repo.setMarkViewAllNotifications(markAll)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class NotificationViewModelFactory(private val repo: NotificationRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NotificationRepo::class.java).newInstance(repo)
    }
}