package com.ronaldsantos.catholicliturgy.domain.config

import com.ronaldsantos.catholicliturgy.data.model.config.NetworkConfig

interface Configuration {
    fun isDebug(): Boolean
    fun networkConfig(): NetworkConfig
    fun databaseName(): String
}
