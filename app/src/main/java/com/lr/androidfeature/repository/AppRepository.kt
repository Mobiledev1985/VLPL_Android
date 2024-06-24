package com.lr.androidfeature.repository

import com.lr.androidfeature.api.ApiService

class AppRepository(private val apiService: ApiService) : BaseRepository() {

    suspend fun login(params: HashMap<String, Any>) = safeApiCall { apiService.login(params) }

}
