package com.ronaldsantos.catholicliturgy.data.config

import android.content.Context
import android.content.pm.ApplicationInfo
import com.ronaldsantos.catholicliturgy.R
import com.ronaldsantos.catholicliturgy.data.model.config.NetworkConfig
import com.ronaldsantos.catholicliturgy.domain.config.Configuration
import com.ronaldsantos.catholicliturgy.library.framework.extension.isEmulator

class ConfigurationImpl(private val context: Context) : Configuration {
    private fun provideNetworkConfigDebug() = NetworkConfig(
        baseUrl = if (isEmulator()) "http://10.0.2.2:5000" else "http://192.168.18.178:5000",
        readTimeout = 30L,
        writeTimeout = 30L,
        connectTimeout = 30L
    )

    private fun provideNetworkConfigRelease() = NetworkConfig(
        baseUrl = "https://catholicapi.ronaldsantos.com",
        readTimeout = 10L,
        writeTimeout = 10L,
        connectTimeout = 10L
    )

    override fun isDebug(): Boolean {
        val flags = context.applicationInfo.flags
        return flags and ApplicationInfo.FLAG_DEBUGGABLE != 0 && isDebugVariant()
    }

    override fun networkConfig(): NetworkConfig = if (isDebug()) {
        provideNetworkConfigDebug()
    } else {
        provideNetworkConfigRelease()
    }

    override fun databaseName(): String {
        return "dailyLiturgyDb"
    }

    override fun shouldShowLog(): Boolean {
        return isDebug() || isReleaseLogVariant()
    }

    private fun isDebugVariant(): Boolean {
        return context.getString(R.string.build_variant) == BUILD_VARIANT_DEBUG
    }

    private fun isReleaseLogVariant(): Boolean {
        return context.getString(R.string.build_variant) == BUILD_VARIANT_RELEASE_LOG
    }

    private companion object {
        const val BUILD_VARIANT_DEBUG = "debug"
        const val BUILD_VARIANT_RELEASE_LOG = "releaseLog"
    }
}
