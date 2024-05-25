package com.ronaldsantos.catholicliturgy.library.framework.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.ronaldsantos.catholicliturgy.library.framework.extension.getDefaultSharedPrefName
import com.ronaldsantos.catholicliturgy.library.framework.extension.getPrefs
import com.ronaldsantos.catholicliturgy.library.framework.extension.toJson

class CacheManagerImpl(
    private val context: Context,
    private val prefFileName: String? = null,
): CacheManager {
    private val prefs: SharedPreferences = context.getPrefs(
        prefFileName ?: context.getDefaultSharedPrefName()
    )

    override fun <T> read(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> prefs.getString(key, defaultValue as String) as T ?: defaultValue
            is Int -> prefs.getInt(key, defaultValue as Int) as T ?: defaultValue
            is Boolean -> prefs.getBoolean(key, defaultValue as Boolean) as T ?: defaultValue
            is Long -> prefs.getLong(key, defaultValue as Long) as T ?: defaultValue
            else -> defaultValue
        }
    }

    override fun <T> write(key: String, value: T) {
        when (value) {
            is String -> prefs.edit { putString(key, value).apply() }
            is Int -> prefs.edit { putInt(key, value).apply() }
            is Boolean -> prefs.edit { putBoolean(key, value).apply() }
            is Long -> prefs.edit { putLong(key, value).apply() }
            else -> Unit
        }
    }

    override fun clear(key: String): Unit = prefs.edit {
        remove(key)
    }

    override fun clearEverything(callBack: () -> Unit) {
        prefs.edit {
            clear().commit()
            callBack.invoke()
        }
    }

    override fun writeObject(key: String, value: Any) {
        write(key, value.toJson())
    }
}
