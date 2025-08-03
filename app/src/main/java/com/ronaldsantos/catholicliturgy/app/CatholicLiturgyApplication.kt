package com.ronaldsantos.catholicliturgy.app

import com.ronaldsantos.catholicliturgy.library.framework.app.AppInitializer
import com.ronaldsantos.catholicliturgy.library.framework.app.CoreApplication
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CatholicLiturgyApplication : CoreApplication() {
    @Inject
    lateinit var initializer: AppInitializer

    override fun onCreate() {
        super.onCreate()
        initializer.init(this)
    }
}
