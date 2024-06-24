package com.lr.androidfeature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lr.androidfeature.repository.AppRepository
import com.lr.androidfeature.repository.BaseRepository

class ViewModelFactory(private val repository: BaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> modelClass.cast(
                LoginViewModel(repository as AppRepository)
            ) as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}