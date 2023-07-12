package com.hk.listeddashboard.api

import com.hk.listeddashboard.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface NetworkService {

    @GET("v1/dashboardNew")
    suspend fun getData(): Response<UserResponse?>

}