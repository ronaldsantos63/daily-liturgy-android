package com.ronaldsantos.catholicliturgy.data.model.config

data class NetworkConfig(
    val baseUrl: String,
    val readTimeout: Long,
    val writeTimeout: Long,
    val connectTimeout: Long,
)
