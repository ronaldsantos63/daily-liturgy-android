package com.ronaldsantos.catholicliturgy.library.framework.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.ronaldsantos.catholicliturgy.library.framework.extension.getDefaultSharedPrefName
import com.ronaldsantos.catholicliturgy.library.framework.extension.getPrefs
import com.ronaldsantos.catholicliturgy.library.framework.extension.toJson
import timber.log.Timber

class CacheManagerImpl(
    private val context: Context,
    private val prefFileName: String? = null,
): CacheManager {
    private val prefs: SharedPreferences = context.getPrefs(
        prefFileName ?: context.getDefaultSharedPrefName()
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T: Any> read(key: String, defaultValue: T): T {
        Timber.tag(TAG).d("Reading value for key: $key with default value: $defaultValue")
        return when (defaultValue) {
            is String -> prefs.getString(key, defaultValue) as? T ?: defaultValue
            is Int -> prefs.getInt(key, defaultValue) as T
            is Boolean -> prefs.getBoolean(key, defaultValue) as T
            is Long -> prefs.getLong(key, defaultValue) as T
            else -> {
                Timber.tag(TAG).e("Unknown type: ${defaultValue::class.java}")
                throw IllegalArgumentException("Unsupported type ${defaultValue::class.java} for key $key")
            }
        }
    }

    override fun <T: Any> write(key: String, value: T) {
        Timber.tag(TAG).d("Writing value for key: $key with value: $value")
        when (value) {
            is String -> prefs.edit { putString(key, value) }
            is Int -> prefs.edit { putInt(key, value) }
            is Boolean -> prefs.edit { putBoolean(key, value) }
            is Long -> prefs.edit { putLong(key, value) }
            else -> {
                Timber.tag(TAG).e("Unknown type: ${value::class.java}")
                throw IllegalArgumentException("Unsupported type ${value::class.java} for key $key")
            }
        }
    }

    override fun clear(key: String): Unit = prefs.edit {
        Timber.tag(TAG).d("Clearing value for key: $key")
        remove(key)
    }

    override fun clearEverything(callBack: () -> Unit) {
        Timber.tag(TAG).d("Clearing all preferences")
        prefs.edit {
            clear().apply()
        }
        callBack.invoke()
    }

    override fun writeObject(key: String, value: Any) {
        Timber.tag(TAG).d("Writing object to cache with key: $key")
        val json = value.toJson()
        if (json.isEmpty()) {
            Timber.tag(TAG).e("Failed to convert object to JSON for key: $key")
            throw IllegalArgumentException("Object cannot be converted to JSON for key $key")
        }
        write(key, json)
    }

    companion object {
        private val TAG = CacheManagerImpl::class.java.simpleName
    }
}
