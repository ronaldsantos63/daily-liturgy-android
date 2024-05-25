package com.ronaldsantos.catholicliturgy.library.framework.app

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.ronaldsantos.catholicliturgy.library.framework.extension.classTag

class ActivityLifecycleCallback : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d(activity.classTag, "onCreate()")
        activity.allowDebugRotation()
        activity.registerFragmentLifecycleCallbacks()
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d(activity.classTag, "onStart()")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d(activity.classTag, "onResume()")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.d(activity.classTag, "onPause()")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.d(activity.classTag, "onStop()")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.d(activity.classTag, "onSaveInstanceState()")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d(activity.classTag, "onDestroy()")
    }
}

private fun Activity.allowDebugRotation() {
    val isDebug = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    requestedOrientation = if (isDebug) {
        ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    } else {
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}

fun Activity.registerFragmentLifecycleCallbacks() {
    if (this is FragmentActivity) {
        supportFragmentManager
            .registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentViewCreated(
                        fm: FragmentManager,
                        f: Fragment,
                        v: View,
                        savedInstanceState: Bundle?
                    ) {
                        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                        Log.d(f.classTag, "onCreateView()")
                    }

                    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                        super.onFragmentResumed(fm, f)
                        Log.d(f.classTag, "onResume()")
                    }

                    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
                        super.onFragmentPaused(fm, f)
                        Log.d(f.classTag, "onPause()")
                    }

                    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                        super.onFragmentViewDestroyed(fm, f)
                        Log.d(f.classTag, "onDestroyView()")
                    }
                },
                true
            )
    }
}
