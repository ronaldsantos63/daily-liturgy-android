package com.ronaldsantos.catholicliturgy.app.di

import com.ronaldsantos.catholicliturgy.data.remote.CatholicLiturgyApi
import com.ronaldsantos.catholicliturgy.domain.config.Configuration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private fun createSpecs() = listOf(
        ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
            ).build()
    )

    @Provides
    @Singleton
    fun provideOkHttpClient(configuration: Configuration): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (configuration.isDebug()) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logging)
            }
            writeTimeout(configuration.networkConfig().writeTimeout, TimeUnit.SECONDS)
            readTimeout(configuration.networkConfig().readTimeout, TimeUnit.SECONDS)
            connectTimeout(configuration.networkConfig().connectTimeout, TimeUnit.SECONDS)
            if (!configuration.isDebug()) {
                connectionSpecs(createSpecs())
            }
        }.build()
    }

    @Provides
    @Singleton
    fun providesCatholicLiturgyApi(
        client: OkHttpClient,
        configuration: Configuration,
    ): CatholicLiturgyApi = createRetrofit(client, configuration)
        .create(CatholicLiturgyApi::class.java)

    private fun createRetrofit(
        client: OkHttpClient,
        configuration: Configuration,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(configuration.networkConfig().baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
