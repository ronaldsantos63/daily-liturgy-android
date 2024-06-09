package com.ronaldsantos.catholicliturgy.data.remote

import com.ronaldsantos.catholicliturgy.data.model.remote.response.DailyLiturgyResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface CatholicLiturgyApi {
    @GET("/liturgy")
    suspend fun fetchDalyLiturgy(@Header("period") period: String): DailyLiturgyResponse?
}
