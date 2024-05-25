package com.ronaldsantos.catholicliturgy.data.remote

import com.ronaldsantos.catholicliturgy.data.model.remote.request.DailyLiturgyRequest
import com.ronaldsantos.catholicliturgy.data.model.remote.response.DailyLiturgyResponse
import retrofit2.http.GET

interface CatholicLiturgyApi {
    @GET("/liturgy")
    suspend fun fetchDalyLiturgy(dailyLiturgyRequest: DailyLiturgyRequest): DailyLiturgyResponse
}
