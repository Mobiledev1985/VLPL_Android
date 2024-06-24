package com.lr.androidfeature.api

import com.lr.androidfeature.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /**
     * With Headers
     */
    @Headers("Accept:application/json")
    @POST("register")
    suspend fun createUser(
        @Header("Content-Type") contentType: String,
        @Body multipartBody: MultipartBody
    ): Response<BaseModel<String>>

    @POST("login")
    suspend fun login(@Body params: HashMap<String, Any>): Response<BaseModel<String>>

}
