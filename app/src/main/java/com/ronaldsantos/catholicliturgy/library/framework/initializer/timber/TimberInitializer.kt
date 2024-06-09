package com.ronaldsantos.catholicliturgy.library.framework.initializer.timber

import android.content.Context
import com.ronaldsantos.catholicliturgy.domain.config.Configuration
import com.ronaldsantos.catholicliturgy.library.framework.app.AppInitializer
import com.ronaldsantos.catholicliturgy.library.framework.app.CoreApplication
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val configuration: Configuration
) : AppInitializer {
    override fun init(application: CoreApplication) {
        if (configuration.shouldShowLog()) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(FirebaseCrashReportingTree(context))
        }
    }
}
