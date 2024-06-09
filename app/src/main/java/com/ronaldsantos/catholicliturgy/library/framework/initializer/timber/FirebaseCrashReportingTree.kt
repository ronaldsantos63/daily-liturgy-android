package com.ronaldsantos.catholicliturgy.library.framework.initializer.timber

import android.content.Context
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.ronaldsantos.catholicliturgy.library.framework.extension.deviceId
import timber.log.Timber

class FirebaseCrashReportingTree(private val context: Context) : Timber.Tree() {
    init {
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR) {
            with(Firebase.crashlytics) {
                 setCustomKey("DEVICE_ID", context.deviceId())
                if (t != null) {
                    log("$tag - $message")
                    recordException(t)
                }
            }
        }
    }
}
