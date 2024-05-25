package com.ronaldsantos.catholicliturgy.library.framework.extension

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ronaldsantos.catholicliturgy.library.framework.pref.CacheManager

fun Context.getPrefs(fileName: String? = null): SharedPreferences {
    val masterKey = MasterKey.Builder(this)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    val name = if (fileName.isNullOrEmpty()) {
        getDefaultSharedPrefName()
    } else {
        fileName.toString()
    }

    return EncryptedSharedPreferences.create(
        this,
        name,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun Context.getDefaultSharedPrefName(): String {
    return this.packageName + "_pref"
}

inline fun <reified T> CacheManager.readObject(key: String): T? {
    val readValue = read(key, "")
    return if (readValue.isEmpty()) {
        null
    } else {
        readValue.fromJson()
    }
}

inline fun <reified T> CacheManager.readListObject(key: String): List<T>? {
    return try {
        val value = read(key, "")
        if (value.isEmpty()) {
            null
        } else {
            value.fromJsonList<T>()
        }
    } catch (ex: Exception) {
        null
    }
}
